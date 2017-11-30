package com.mqunar.jonsnow.utils;

/**
 * Created by ironman.li on 2016/7/14.
 */
public class TextUtils {

    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String join(String delimiter, String[] elements) {
        if (elements == null || elements.length == 0) {
            return "";
        } else if (elements.length == 1) {
            return elements[0];
        } else {
            StringBuilder sb = new StringBuilder(elements[0]);
            for (int i = 1; i < elements.length; i++) {
                sb.append(delimiter).append(elements[i]);
            }
            return sb.toString();
        }
    }
}
