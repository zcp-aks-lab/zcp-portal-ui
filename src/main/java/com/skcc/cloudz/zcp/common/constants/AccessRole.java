package com.skcc.cloudz.zcp.common.constants;

public enum AccessRole {
    CLUSTER_ADMIN("cluster-admin"),
    ADMIN("admin"),
    EDITOR(""),
    VIEWER("viewer"),
    NONE("none"),
    ;
    
    private String name;

    private AccessRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
