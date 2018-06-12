package com.skcc.cloudz.zcp.common.constants;

public enum ApiResult {
    SUCCESS("Success", "200"),
    FAIL("Fail", "500");
    
    private String name;
    private String code;
    
    private ApiResult(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
