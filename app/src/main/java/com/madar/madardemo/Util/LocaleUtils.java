package com.madar.madardemo.Util;

/**
 * Created by Ahmed on 8/15/2017.
 */

public class LocaleUtils {

    public final static int LANGUAGE_ARABIC = 0;
    public final static int LANGUAGE_ENGLISH = 1;

    public static int CURRENT_LANGUAGE = LANGUAGE_ARABIC;

    public static void changeDefaultLanguage(int lang){
        if(lang == LANGUAGE_ENGLISH){
            CURRENT_LANGUAGE = LANGUAGE_ENGLISH;
        }
        else{
            CURRENT_LANGUAGE = LANGUAGE_ARABIC;
        }
    }

    /*@SuppressWarnings("deprecation")
    private static void changeDefaultLanguageLegacy(int lang, Context context){
        String l = null;
        if(lang == LANGUAGE_ENGLISH){
            l = "en";
            CURRENT_LANGUAGE = LANGUAGE_ENGLISH;
        }
        else{
            l = "ar";
            CURRENT_LANGUAGE = LANGUAGE_ARABIC;
        }
        Locale locale = new Locale(l);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getApplicationContext().getResources().updateConfiguration(config, null);
    }

    private static void changeDefaultLanguageNewApi(int lang, Context context){

    }*/
}
