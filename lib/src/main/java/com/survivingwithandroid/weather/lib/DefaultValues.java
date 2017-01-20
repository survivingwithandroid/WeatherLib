package com.survivingwithandroid.weather.lib;

/**
 * Created by muellner-riederm on 19.01.2017.
 */

public final class DefaultValues {
    private DefaultValues() {}

    // These values are used if no value was assigned
    public static Integer DEFAULT_INTEGER = null;
    public static Long DEFAULT_LONG = null;
    public static Double DEFAULT_DOUBLE = null;
    public static String DEFAULT_STRING = "";

    // These values are used when parsing fails
    public static Integer ERROR_INTEGER = null;
    public static Long ERROR_LONG = null;
    public static Double ERROR_DOUBLE = null;
    public static String ERROR_STRING = "";
}
