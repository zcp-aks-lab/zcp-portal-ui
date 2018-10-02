package com.skcc.cloudz.zcp.common.constants;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ClusterRole {
    CLUSTER_ADMIN("cluster-admin"), MEMBER("member");
    
    private String name;
    
    private ClusterRole(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }
}
