package com.probasteReiniciando.TPTACS.domain;

public enum Language {
    ENGLISH,
    SPANISH;


    public static String getLanguage(Language code) {
        String language = "es";
        switch (code) {
            case ENGLISH:
                language = "en";
                break;
            case SPANISH:
                language = "es";
                break;
        }
        return language;
    }

    public static Language getValueOfCode(String code){
        Language language = SPANISH;
        switch (code){
            case "es":
                language = SPANISH;
                break;
            case "en":
                language = ENGLISH;
                break;
        }
        return language;
    }
}
