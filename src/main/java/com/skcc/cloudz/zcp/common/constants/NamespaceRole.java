package com.skcc.cloudz.zcp.common.constants;

import com.fasterxml.jackson.annotation.JsonValue;

public enum NamespaceRole {
    ADMIN("admin"), CICD_MANAGER("cicd-manager"), DEVELOPER("developer");
    
    private String name;
    
    private NamespaceRole(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }
}
