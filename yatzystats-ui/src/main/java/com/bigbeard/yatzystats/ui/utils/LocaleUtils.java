package com.bigbeard.yatzystats.ui.utils;

import java.util.Locale;

public class LocaleUtils {

    public static Locale resolveLocale(String code) {
        if (code == null || code.isBlank()) {
            return Locale.getDefault();
        }

        return switch (code.toLowerCase()) {
            case String s when s.matches("[a-z]{2}_[A-Z]{2}") -> {
                String[] parts = s.split("_");
                yield Locale.of(parts[0], parts[1]); // New Java 19+ way
            }
            case "en" -> Locale.ENGLISH;
            case "fr" -> Locale.FRENCH;
            case "de" -> Locale.GERMAN;
            case "es" -> Locale.of("es", "ES");
            case "sv" -> Locale.of("sv", "SE");
            default -> Locale.getDefault();
        };
    }
}
