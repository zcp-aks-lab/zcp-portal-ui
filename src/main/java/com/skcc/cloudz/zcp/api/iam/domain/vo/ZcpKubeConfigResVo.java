package com.skcc.cloudz.zcp.api.iam.domain.vo;

public class ZcpKubeConfigResVo {
    private String msg;
    private String code;
    private ZcpKubeConfig data;
    
    public ZcpKubeConfigResVo() {}

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ZcpKubeConfig getData() {
        return data;
    }

    public void setData(ZcpKubeConfig data) {
        this.data = data;
    }
    
}
