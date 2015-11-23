package com.goeuro.devtest.util;

public class Util {

    public static final String ENCODING_UTF8 = "UTF-8";

    public static String formatString(String str) {
        return (str == null) ? "" : str;
    }

    public static String formatLong(Long l) {
        return (l == null) ? "" : String.valueOf(l);
    }

    public static String formatDouble(Double d) {
        return (d == null) ? "" : String.valueOf(d);
    }
}
