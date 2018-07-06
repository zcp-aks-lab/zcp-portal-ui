package com.skcc.cloudz.zcp.api.iam.domain.vo;

import java.util.List;

public class ZcpNodeListVo {
    private String msg;
    private String code;
    private ZcpNode data;
    
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

    public ZcpNode getData() {
        return data;
    }

    public void setData(ZcpNode data) {
        this.data = data;
    }

    public static class ZcpNode {
        List<ZcpNodeVo> items;

        public List<ZcpNodeVo> getItems() {
            return items;
        }

        public void setItems(List<ZcpNodeVo> items) {
            this.items = items;
        }
    }
}
