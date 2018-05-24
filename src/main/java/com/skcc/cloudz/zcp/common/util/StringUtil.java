package com.skcc.cloudz.zcp.common.util;

public class StringUtil {

    private StringUtil() {}

    private static class StringUtilHolder {
        static final StringUtil single = new StringUtil();
    }

    public static StringUtil getInstance() {
        return StringUtilHolder.single;
    }
}
