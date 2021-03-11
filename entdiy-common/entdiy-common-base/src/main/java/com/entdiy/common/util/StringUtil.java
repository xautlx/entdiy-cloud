package com.entdiy.common.util;

import com.entdiy.common.exception.Validation;

import java.util.regex.Pattern;

public class StringUtil {

    static String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
            + "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";
    static Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);

    public static void assertSqlInjection(String sql) {
        Validation.isTrue(!sqlPattern.matcher(sql).find(), "SQL检测异常");
    }
}
