package com.skcc.cloudz.zcp.api.iam.domain.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ZcpUserVo {
    private String id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+9")
    private Date createdDate;
    private Boolean enabled;
    private Boolean emailVerified;
    private Boolean totp;
    private String clusterRole;
    private List<String> namespaces;
    private String defaultNamespace;
    private int usedNamespace;
    private List<String> requiredActions;
    private String namespacedRole;
    private Boolean zdbAdmin;
    
    public ZcpUserVo() {
        super();
    }

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
   
    public String getClusterRole() {
        return clusterRole;
    }

    public void setClusterRole(String clusterRole) {
        this.clusterRole = clusterRole;
    }

    public List<String> getNamespaces() {
        return namespaces;
    }

    public void setNamespaces(List<String> namespaces) {
        this.namespaces = namespaces;
    }

    public String getDefaultNamespace() {
        return defaultNamespace;
    }

    public void setDefaultNamespace(String defaultNamespace) {
        this.defaultNamespace = defaultNamespace;
    }

    public int getUsedNamespace() {
        return usedNamespace;
    }

    public void setUsedNamespace(int usedNamespace) {
        this.usedNamespace = usedNamespace;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public Boolean getTotp() {
        return totp;
    }

    public void setTotp(Boolean totp) {
        this.totp = totp;
    }

    public List<String> getRequiredActions() {
        return requiredActions;
    }

    public void setRequiredActions(List<String> requiredActions) {
        this.requiredActions = requiredActions;
    }

    public String getNamespacedRole() {
        return namespacedRole;
    }

    public void setNamespacedRole(String namespacedRole) {
        this.namespacedRole = namespacedRole;
    }

    public Boolean getZdbAdmin() {
        return zdbAdmin;
    }

    public void setZdbAdmin(Boolean zdbAdmin) {
        this.zdbAdmin = zdbAdmin;
    }
    
}
