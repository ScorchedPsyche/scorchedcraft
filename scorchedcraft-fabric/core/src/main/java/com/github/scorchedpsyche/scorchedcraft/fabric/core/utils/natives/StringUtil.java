package com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.natives;

public class StringUtil {
    public static boolean isNullOrEmpty(String string)
    {
        return string == null || string.isEmpty();
    }
    public static boolean isStringBuilderNullOrEmpty(StringBuilder stringBuilder)
    {
        return stringBuilder == null || stringBuilder.equals("");
    }
    public static String removeEncasingDoubleQuotes(String str)
    {
        return str.replaceAll("^\"|\"$", "");
    }
}
