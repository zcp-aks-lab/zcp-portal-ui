package com.skcc.cloudz.zcp.common.domain.vo;

import java.util.List;

public class AddOnServiceMataVo implements Comparable<AddOnServiceMataVo> {
    private String id;
    private String name;
    private int order;
    private String url;
    private String target;
    private List<AddOnServiceMataSubVo> sub;
    private Role role;
    private boolean enable;
    
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

    public List<AddOnServiceMataSubVo> getSub() {
        return sub;
    }

    public void setSub(List<AddOnServiceMataSubVo> sub) {
        this.sub = sub;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public int compareTo(AddOnServiceMataVo addOnServiceMataVo) {
        if (this.order < addOnServiceMataVo.getOrder()) {
            return -1;
        } else if (this.order > addOnServiceMataVo.getOrder()) {
            return 1;
        }
        
        return 0;
    }
    
    public static class Role {
        private List<String> clusterRoles;
        private List<String> namespaceRoles;
        
        public List<String> getClusterRoles() {
            return clusterRoles;
        }
        public void setClusterRoles(List<String> clusterRoles) {
            this.clusterRoles = clusterRoles;
        }
        public List<String> getNamespaceRoles() {
            return namespaceRoles;
        }
        public void setNamespaceRoles(List<String> namespaceRoles) {
            this.namespaceRoles = namespaceRoles;
        }
    }
    
}
