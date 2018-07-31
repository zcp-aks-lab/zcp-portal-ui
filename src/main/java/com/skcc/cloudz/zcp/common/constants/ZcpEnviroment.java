package com.skcc.cloudz.zcp.common.constants;

public enum ZcpEnviroment {
    LOCAL("local", "로컬"), DEV("dev", "개발"), STAGE("stage", "스테이징");
    
    private String name;
    private String description;
    
    private ZcpEnviroment(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
