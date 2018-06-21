package com.skcc.cloudz.zcp.portal.iam.user.domain.dto;

import java.util.List;

import com.skcc.cloudz.zcp.common.domain.dto.CommonDto;

public class UserDto extends CommonDto {
    private String id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private Boolean enabled;
    private Boolean temporary;
    private String period;
    private String type;
    private List<String> actions;
    private String clusterRole;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public Boolean getEnabled() {
        return enabled;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    public Boolean getTemporary() {
        return temporary;
    }
    public void setTemporary(Boolean temporary) {
        this.temporary = temporary;
    }
    public String getPeriod() {
        return period;
    }
    public void setPeriod(String period) {
        this.period = period;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public List<String> getActions() {
        return actions;
    }
    public void setActions(List<String> actions) {
        this.actions = actions;
    }
    public String getClusterRole() {
        return clusterRole;
    }
    public void setClusterRole(String clusterRole) {
        this.clusterRole = clusterRole;
    }
    
}
