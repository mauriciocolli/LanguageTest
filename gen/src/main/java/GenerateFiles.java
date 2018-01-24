import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.io.File;
import java.io.FileOutputStream;

public class GenerateFiles {
    private static final String TAGS = "{" +
            "    'af': ['one', 'other']," +
            "    'ak': ['one', 'other']," +
            "    'am': ['one', 'other']," +
            "    'ar': ['zero', 'one', 'two', 'few', 'many', 'other']," +
            "    'ars': ['zero', 'one', 'two', 'few', 'many', 'other']," +
            "    'as': ['one', 'other']," +
            "    'asa': ['one', 'other']," +
            "    'ast': ['one', 'other']," +
            "    'az': ['one', 'other']," +
            "    'be': ['one', 'few', 'many']," +
            "    'bem': ['one', 'other']," +
            "    'bez': ['one', 'other']," +
            "    'bg': ['one', 'other']," +
            "    'bh': ['one', 'other']," +
            "    'bm': ['other']," +
            "    'bn': ['one', 'other']," +
            "    'bo': ['other']," +
            "    'br': ['one', 'two', 'few', 'many', 'other']," +
            "    'brx': ['one', 'other']," +
            "    'bs': ['one', 'few', 'other']," +
            "    'ca': ['one', 'other']," +
            "    'ce': ['one', 'other']," +
            "    'cgg': ['one', 'other']," +
            "    'chr': ['one', 'other']," +
            "    'ckb': ['one', 'other']," +
            "    'cs': ['one', 'few', 'other']," +
            "    'cy': ['zero', 'one', 'two', 'few', 'many', 'other']," +
            "    'da': ['one', 'other']," +
            "    'de': ['one', 'other']," +
            "    'dsb': ['one', 'two', 'few', 'other']," +
            "    'dv': ['one', 'other']," +
            "    'dz': ['other']," +
            "    'ee': ['one', 'other']," +
            "    'el': ['one', 'other']," +
            "    'en': ['one', 'other']," +
            "    'eo': ['one', 'other']," +
            "    'es': ['one', 'other']," +
            "    'et': ['one', 'other']," +
            "    'eu': ['one', 'other']," +
            "    'fa': ['one', 'other']," +
            "    'ff': ['one', 'other']," +
            "    'fi': ['one', 'other']," +
            "    'fil': ['one', 'other']," +
            "    'fo': ['one', 'other']," +
            "    'fr': ['one', 'other']," +
            "    'fur': ['one', 'other']," +
            "    'fy': ['one', 'other']," +
            "    'ga': ['one', 'two', 'few', 'many', 'other']," +
            "    'gd': ['one', 'two', 'few', 'other']," +
            "    'gl': ['one', 'other']," +
            "    'gsw': ['one', 'other']," +
            "    'gu': ['one', 'other']," +
            "    'guw': ['one', 'other']," +
            "    'gv': ['one', 'two', 'few', 'other']," +
            "    'ha': ['one', 'other']," +
            "    'haw': ['one', 'other']," +
            "    'he': ['one', 'two', 'many', 'other']," +
            "    'hi': ['one', 'other']," +
            "    'hr': ['one', 'few', 'other']," +
            "    'hsb': ['one', 'two', 'few', 'other']," +
            "    'hu': ['one', 'other']," +
            "    'hy': ['one', 'other']," +
            "    'id': ['other']," +
            "    'ig': ['other']," +
            "    'ii': ['other']," +
            "    'in': ['other']," +
            "    'io': ['one', 'other']," +
            "    'is': ['one', 'other']," +
            "    'it': ['one', 'other']," +
            "    'iu': ['one', 'two', 'other']," +
            "    'iw': ['one', 'two', 'many', 'other']," +
            "    'ja': ['other']," +
            "    'jbo': ['other']," +
            "    'jgo': ['one', 'other']," +
            "    'ji': ['one', 'other']," +
            "    'jmc': ['one', 'other']," +
            "    'jv': ['other']," +
            "    'jw': ['other']," +
            "    'ka': ['one', 'other']," +
            "    'kab': ['one', 'other']," +
            "    'kaj': ['one', 'other']," +
            "    'kcg': ['one', 'other']," +
            "    'kde': ['other']," +
            "    'kea': ['other']," +
            "    'kk': ['one', 'other']," +
            "    'kkj': ['one', 'other']," +
            "    'kl': ['one', 'other']," +
            "    'km': ['other']," +
            "    'kn': ['one', 'other']," +
            "    'ko': ['other']," +
            "    'ks': ['one', 'other']," +
            "    'ksb': ['one', 'other']," +
            "    'ksh': ['zero', 'one', 'other']," +
            "    'ku': ['one', 'other']," +
            "    'kw': ['one', 'two', 'other']," +
            "    'ky': ['one', 'other']," +
            "    'lag': ['zero', 'one', 'other']," +
            "    'lb': ['one', 'other']," +
            "    'lg': ['one', 'other']," +
            "    'lkt': ['other']," +
            "    'ln': ['one', 'other']," +
            "    'lo': ['other']," +
            "    'lt': ['one', 'few', 'other']," +
            "    'lv': ['zero', 'one', 'other']," +
            "    'mas': ['one', 'other']," +
            "    'mg': ['one', 'other']," +
            "    'mgo': ['one', 'other']," +
            "    'mk': ['one', 'other']," +
            "    'ml': ['one', 'other']," +
            "    'mn': ['one', 'other']," +
            "    'mo': ['one', 'few', 'other']," +
            "    'mr': ['one', 'other']," +
            "    'ms': ['other']," +
            "    'mt': ['one', 'few', 'many', 'other']," +
            "    'my': ['other']," +
            "    'nah': ['one', 'other']," +
            "    'naq': ['one', 'two', 'other']," +
            "    'nb': ['one', 'other']," +
            "    'nd': ['one', 'other']," +
            "    'ne': ['one', 'other']," +
            "    'nl': ['one', 'other']," +
            "    'nn': ['one', 'other']," +
            "    'nnh': ['one', 'other']," +
            "    'no': ['one', 'other']," +
            "    'nqo': ['other']," +
            "    'nr': ['one', 'other']," +
            "    'nso': ['one', 'other']," +
            "    'ny': ['one', 'other']," +
            "    'nyn': ['one', 'other']," +
            "    'om': ['one', 'other']," +
            "    'or': ['one', 'other']," +
            "    'os': ['one', 'other']," +
            "    'pa': ['one', 'other']," +
            "    'pap': ['one', 'other']," +
            "    'pl': ['one', 'few', 'many']," +
            "    'prg': ['zero', 'one', 'other']," +
            "    'ps': ['one', 'other']," +
            "    'pt': ['one', 'other']," +
            //"    'pt_PT': ['one', 'other']," +
            "    'rm': ['one', 'other']," +
            "    'ro': ['one', 'few', 'other']," +
            "    'rof': ['one', 'other']," +
            //"    'root': ['other']," +
            "    'ru': ['one', 'few', 'many']," +
            "    'rwk': ['one', 'other']," +
            "    'sah': ['other']," +
            "    'saq': ['one', 'other']," +
            "    'sd': ['one', 'other']," +
            "    'sdh': ['one', 'other']," +
            "    'se': ['one', 'two', 'other']," +
            "    'seh': ['one', 'other']," +
            "    'ses': ['other']," +
            "    'sg': ['other']," +
            "    'sh': ['one', 'few', 'other']," +
            "    'shi': ['one', 'few', 'other']," +
            "    'si': ['one', 'other']," +
            "    'sk': ['one', 'few', 'other']," +
            "    'sl': ['one', 'two', 'few', 'other']," +
            "    'sma': ['one', 'two', 'other']," +
            "    'smi': ['one', 'two', 'other']," +
            "    'smj': ['one', 'two', 'other']," +
            "    'smn': ['one', 'two', 'other']," +
            "    'sms': ['one', 'two', 'other']," +
            "    'sn': ['one', 'other']," +
            "    'so': ['one', 'other']," +
            "    'sq': ['one', 'other']," +
            "    'sr': ['one', 'few', 'other']," +
            "    'ss': ['one', 'other']," +
            "    'ssy': ['one', 'other']," +
            "    'st': ['one', 'other']," +
            "    'sv': ['one', 'other']," +
            "    'sw': ['one', 'other']," +
            "    'syr': ['one', 'other']," +
            "    'ta': ['one', 'other']," +
            "    'te': ['one', 'other']," +
            "    'teo': ['one', 'other']," +
            "    'th': ['other']," +
            "    'ti': ['one', 'other']," +
            "    'tig': ['one', 'other']," +
            "    'tk': ['one', 'other']," +
            "    'tl': ['one', 'other']," +
            "    'tn': ['one', 'other']," +
            "    'to': ['other']," +
            "    'tr': ['one', 'other']," +
            "    'ts': ['one', 'other']," +
            "    'tzm': ['one', 'other']," +
            "    'ug': ['one', 'other']," +
            "    'uk': ['one', 'few', 'many']," +
            "    'ur': ['one', 'other']," +
            "    'uz': ['one', 'other']," +
            "    've': ['one', 'other']," +
            "    'vi': ['other']," +
            "    'vo': ['one', 'other']," +
            "    'vun': ['one', 'other']," +
            "    'wa': ['one', 'other']," +
            "    'wae': ['one', 'other']," +
            "    'wo': ['other']," +
            "    'xh': ['one', 'other']," +
            "    'xog': ['one', 'other']," +
            "    'yi': ['one', 'other']," +
            "    'yo': ['other']," +
            "    'yue': ['other']," +
            "    'zh': ['other']," +
            "    'zu': ['one', 'other']" +
            "}";

    private static final boolean GENERATE_WITHOUT_OTHER = true;
    private static final boolean GENERATE_WITHOUT_OTHER_DELETE_IF_EMPTY = false;
    private static final String RES_DIR = System.getProperty("user.dir") + (GENERATE_WITHOUT_OTHER
            ? "\\..\\app\\src\\withoutOther\\res"
            : "\\..\\app\\src\\normal\\res");

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void main(String[] args) {
        final JsonObject object = Json.parse(TAGS.replaceAll("'", "\"")).asObject();
        System.out.println("RES_DIR = " + RES_DIR);

        for (String languageCode : object.names()) {
            final JsonValue value = object.get(languageCode);
            final JsonArray array = value.asArray();


            File folder = new File(RES_DIR, "values-" + languageCode);
            if (!folder.exists() && !folder.mkdir()) {
                System.err.println(languageCode + " could not be created");
                break;
            }


            if (GENERATE_WITHOUT_OTHER_DELETE_IF_EMPTY && GENERATE_WITHOUT_OTHER
                    && array.size() == 1 && array.get(0).asString().equals("other")) {
                for (File file : (folder.listFiles() != null ? folder.listFiles() : new File[]{})) file.delete();
                folder.delete();
                continue;
            }

            File file = new File(folder, "strings.xml");
            try {
                if (!file.exists() && !file.createNewFile()) {
                    System.err.println(languageCode + " FILE could not be created");
                }

                StringBuilder fileContent = new StringBuilder("<resources>\n");
                fileContent.append("    <plurals name=\"test\">\n");// +
                System.out.println(languageCode + " = " + value);
                for (JsonValue jsonValue : array) {
                    if (GENERATE_WITHOUT_OTHER && jsonValue.asString().equals("other")) {
                        continue;
                    }

                    fileContent.append("        ");
                    fileContent.append("<item quantity=\"").append(jsonValue.asString()).append("\">");
                    fileContent.append(languageCode).append(" \\> %1$d");
                    fileContent.append("</item>\n");
                }
                fileContent.append("    </plurals>\n</resources>");

                try (final FileOutputStream outputStream = new FileOutputStream(file)) {
                    outputStream.write(fileContent.toString().getBytes("UTF-8"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
