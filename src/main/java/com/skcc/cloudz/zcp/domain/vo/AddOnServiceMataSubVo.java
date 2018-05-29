package com.skcc.cloudz.zcp.domain.vo;

public class AddOnServiceMataSubVo {
    private String name;
    private int order;
    private String url;
    private String target;
    
    public AddOnServiceMataSubVo() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
    
}
