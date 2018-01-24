package com.lang.test;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity {
    private static final String[] GENERATED_LANGUAGES = {"af", "ak", "am", "ar", "ars", "as", "asa", "ast", "az", "be", "bem", "bez", "bg", "bh", "bm", "bn", "bo", "br", "brx", "bs", "ca", "ce", "cgg", "chr", "ckb", "cs", "cy", "da", "de", "dsb", "dv", "dz", "ee", "el", "en", "eo", "es", "et", "eu", "fa", "ff", "fi", "fil", "fo", "fr", "fur", "fy", "ga", "gd", "gl", "gsw", "gu", "guw", "gv", "ha", "haw", "he", "hi", "hr", "hsb", "hu", "hy", "id", "ig", "ii", "in", "io", "is", "it", "iu", "iw", "ja", "jbo", "jgo", "ji", "jmc", "jv", "jw", "ka", "kab", "kaj", "kcg", "kde", "kea", "kk", "kkj", "kl", "km", "kn", "ko", "ks", "ksb", "ksh", "ku", "kw", "ky", "lag", "lb", "lg", "lkt", "ln", "lo", "lt", "lv", "mas", "mg", "mgo", "mk", "ml", "mn", "mo", "mr", "ms", "mt", "my", "nah", "naq", "nb", "nd", "ne", "nl", "nn", "nnh", "no", "nqo", "nr", "nso", "ny", "nyn", "om", "or", "os", "pa", "pap", "pl", "prg", "ps", "pt", "rm", "ro", "rof", "ru", "rwk", "sah", "saq", "sd", "sdh", "se", "seh", "ses", "sg", "sh", "shi", "si", "sk", "sl", "sma", "smi", "smj", "smn", "sms", "sn", "so", "sq", "sr", "ss", "ssy", "st", "sv", "sw", "syr", "ta", "te", "teo", "th", "ti", "tig", "tk", "tl", "tn", "to", "tr", "ts", "tzm", "ug", "uk", "ur", "uz", "ve", "vi", "vo", "vun", "wa", "wae", "wo", "xh", "xog", "yi", "yo", "yue", "zh", "zu"};

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private TextView results;
    private ScrollView scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        results = findViewById(R.id.results);
        scroll = findViewById(R.id.vertical_scroll_view);
    }

    @Override
    protected void onStop() {
        super.onStop();
        executor.shutdownNow();
        clearResults();
    }

    public void onGeneratedClick(View view) {
        startTests(Arrays.asList(GENERATED_LANGUAGES));
    }

    public void onAllLangClick(View view) {
        final HashSet<String> languageSet = new LinkedHashSet<>();
        for (Locale locale : Locale.getAvailableLocales()) {
            final String country = locale.getLanguage();
            // Uncomment to test ALL languages with countries
            // + (TextUtils.isEmpty(locale.getCountry()) ? "" : "_" + locale.getCountry());
            languageSet.add(country);
        }

        startTests(languageSet);
    }

    private void startTests(final Collection<String> languagesToTest) {
        clearResults();
        appendLineResults("Starting tests\n");

        if (executor != null) executor.shutdownNow();
        executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            final List<String> passed = new ArrayList<>();
            final List<String> failed = new ArrayList<>();

            for (String language : languagesToTest) {
                Configuration configuration = new Configuration(getResources().getConfiguration());
                final Locale locale = new Locale(language);

                final String languageToCheck = (!language.equals(locale.getLanguage()) ? locale.getLanguage() : language);
                final String languageToPrint = (!language.equals(locale.getLanguage()) ? language + " (" + locale.getLanguage() + ")" : language);

                final Resources resources = getLocaleResources(this, locale, configuration);

                try {
                    String additionalMesg = "";
                    String quantityString;
                    for (int quantity = 0; quantity <= 220; quantity++) {
                        if (Thread.currentThread().isInterrupted()) return;

                        quantityString = resources.getQuantityString(R.plurals.test, quantity, quantity);
                        final boolean returnedCode = quantityString.contains(languageToCheck + " > ")
                                || quantityString.contains(languageToCheck.substring(0, 2) + " > ") || quantityString.contains("default strings > ");

                        if (!returnedCode || quantityString.contains("%") || quantityString.contains("$")) {
                            System.out.println("fail > " + languageToPrint + ": " + quantityString);
                            throw new Throwable("didn't returned correctly! (for quantity " + quantity + ": '" + quantityString + "')");
                        }

                        if (quantityString.contains("default strings > ")) {
                            if (TextUtils.isEmpty(additionalMesg))
                                additionalMesg = "Fallback to default strings: ";
                            additionalMesg += quantity + " ";
                        }
                    }
                    if (!TextUtils.isEmpty(additionalMesg)) {
                        additionalMesg = " (" + additionalMesg + ")";
                    }
                    appendLineResults("[✓] " + languageToPrint + " passed tests" + additionalMesg);
                    passed.add(languageToPrint);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    appendLineResults("[❌] " + languageToPrint + " didn't pass [" + throwable.getMessage() + "]");
                    failed.add(languageToPrint);
                }
            }

            appendLineResults("");
            appendLineResults("");
            appendLineResults("passed = " + passed);
            appendLineResults("");
            appendLineResults("failed = " + failed);

            System.out.println("passed = " + passed);
            System.out.println("failed = " + failed);
        });
    }

    private static Resources getLocaleResources(Activity activity, Locale locale, Configuration configuration) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
            return activity.createConfigurationContext(configuration).getResources();
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.locale = locale;
            DisplayMetrics metrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            return new Resources(activity.getAssets(), metrics, configuration);
        }
        return null;
    }

    private void appendLineResults(final String append) {
        runOnUiThread(() -> results.append(append + "\n"));
        runOnUiThread(() -> scroll.post(() -> scroll.fullScroll(View.FOCUS_DOWN)));
    }

    private void clearResults() {
        runOnUiThread(() -> results.setText(""));
    }
}
