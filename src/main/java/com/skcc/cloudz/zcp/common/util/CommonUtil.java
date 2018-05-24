package com.skcc.cloudz.zcp.common.util;

/**
 * @author Administrator
 * 공통 유틸
 */
public class CommonUtil {

    private CommonUtil() {}

    private static class CommonUtilHolder {
      static final CommonUtil single = new CommonUtil();
    }

    public static CommonUtil getInstance() {
      return CommonUtilHolder.single;
    }
}
