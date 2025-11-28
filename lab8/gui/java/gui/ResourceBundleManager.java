package gui;

import java.util.*;

public class ResourceBundleManager {
    private static Locale currentLocale = Locale.ENGLISH;
    private static ResourceBundle bundle = ResourceBundle.getBundle("Messages", currentLocale);

    public static void setLocale(Locale locale) {
        currentLocale = locale;
        bundle = ResourceBundle.getBundle("Messages", currentLocale);
    }

    public static ResourceBundle getBundle() {
        return bundle;
    }

    public static Locale getLocale() {
        return currentLocale;
    }

    public static List<Locale> getSupportedLocales() {
        return List.of(
                new Locale("ru"),
                new Locale("en", "GB"),
                new Locale("fi"),
                new Locale("lt")
        );
    }

    public static String get(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return "!" + key + "!";
        }
    }
}