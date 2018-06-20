package com.skcc.cloudz.zcp.portal.iam.user.domain.dto;

public class UserDto {
    private String id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private Boolean enabled;
    private Boolean temporary;
    
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
}
