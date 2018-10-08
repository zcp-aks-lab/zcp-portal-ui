package com.skcc.cloudz.zcp.common.util;

import com.skcc.cloudz.zcp.common.exception.InvalidRequestException;

public class NumberUtil {

    private NumberUtil() {}

    private static class StringUtilHolder {
        static final NumberUtil single = new NumberUtil();
    }

    public static NumberUtil getInstance() {
        return StringUtilHolder.single;
    }

    public Integer isNumber(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new InvalidRequestException("잘못된 요청 입니다.");
        }
    }

    public Long isLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            throw new InvalidRequestException("잘못된 요청 입니다.");
        }
    }

    public Integer nvlInt(Integer objData, int nTrans) {
        return (objData == null) ? nTrans : objData;
    }

    public Long nvlLong(Long objData, int nTrans) {
        return (objData == null) ? nTrans : objData;
    }
    
    public static int compare(double a, double b) {
		if (a == b)
			return 0;
		if (a > b)
			return 1;
		else
			return -1;
	}
    
	public static int compare(int a, int b) {
		if (a == b)
			return 0;
		if (a > b)
			return 1;
		else
			return -1;
	}
}
