package cx.ath.jbzdak.pojava.zderzenia.translation;

import java.text.MessageFormat;
import java.util.*;


/**
 * Klasa przechowuje wybrane przez użytkownika Locale
 */
public class LocaleHolder {

    private static final List<Locale> SUPPORTED_LOCALES = Arrays.asList(
            new Locale("en"), new Locale("pl")
    );

    private static final List<String> LOCALE_NAMES;

    static {
        ArrayList<String> localeNames = new ArrayList<String>();

        for(Locale locale: SUPPORTED_LOCALES){
            localeNames.add(locale.getDisplayLanguage());
        }

        LOCALE_NAMES = Collections.unmodifiableList(localeNames);
    }

    private static final LocaleHolder holder = new LocaleHolder();

    ResourceBundle resourceBundle;

    public static List<Locale> getSupportedLocales() {
        return SUPPORTED_LOCALES;
    }

    public static List<String> getLocaleNames() {
        return LOCALE_NAMES;
    }

    public static LocaleHolder getHolder() {
        return holder;
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public static String getTrans(String key){
        try {
            return getHolder().getResourceBundle().getString(key);
        } catch (MissingResourceException e) {
            return key;
        }
    }

    public static String getTrans(String key, Object... args){
        String message = getTrans(key);
        return MessageFormat.format(message, args);
    }

    public void setLocale(Locale locale){
        if (!SUPPORTED_LOCALES.contains(locale)){
            throw new IllegalArgumentException("Unsupported locale");
        }
        resourceBundle = ResourceBundle.getBundle("cx.ath.jbzdak.pojava.zderzenia.translation.locale", locale);
    }
}
