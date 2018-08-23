package com.skcc.cloudz.zcp.common.constants;

public enum Result {
    SUCCESS("0000", "SUCCESS"), ERROR("9999", "ERROR");
    
    private Result(String cd, String msg) {
        this.cd = cd;
        this.msg = msg;
    }

    private String cd;
    private String msg;

    public String getCd() {
        return cd;
    }

    public String getMsg() {
        return msg;
    }
}
