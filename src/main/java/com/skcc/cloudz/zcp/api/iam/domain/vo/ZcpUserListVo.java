package com.skcc.cloudz.zcp.api.iam.domain.vo;

import java.util.List;

public class ZcpUserListVo {
    private String msg;
    private String code;
    private ZcpUser data;
    
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

    public ZcpUser getData() {
        return data;
    }

    public void setData(ZcpUser data) {
        this.data = data;
    }

    public static class ZcpUser {
        List<ZcpUserVo> items;

        public List<ZcpUserVo> getItems() {
            return items;
        }

        public void setItems(List<ZcpUserVo> items) {
            this.items = items;
        }
    }
}
