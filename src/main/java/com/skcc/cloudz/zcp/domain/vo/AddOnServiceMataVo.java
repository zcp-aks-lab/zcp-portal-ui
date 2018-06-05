package com.skcc.cloudz.zcp.domain.vo;

import java.util.List;

import com.skcc.cloudz.zcp.common.constants.AccessRole;

public class AddOnServiceMataVo {
    private String id;
    private String name;
    private int order;
    private String url;
    private String target;
    private List<AccessRole> accessRoles;
    private List<AddOnServiceMataSubVo> sub;
    
    public AddOnServiceMataVo() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public List<AccessRole> getAccessRoles() {
        return accessRoles;
    }

    public void setAccessRoles(List<AccessRole> accessRoles) {
        this.accessRoles = accessRoles;
    }

    public List<AddOnServiceMataSubVo> getSub() {
        return sub;
    }

    public void setSub(List<AddOnServiceMataSubVo> sub) {
        this.sub = sub;
    }
    
}
