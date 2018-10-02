package com.skcc.cloudz.zcp.common.component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.skcc.cloudz.zcp.common.domain.vo.AddOnServiceMataVo;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AuthUserComponent implements Serializable {

    private static final long serialVersionUID = -3331519522204041061L;
    
    public AuthUserComponent() {}
    
    private String userId;
    private String firstName;
    private String namespace;
    private HashMap<String, Object> addOnServiceMetaData;
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getNamespace() {
        return namespace;
    }
    
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
    
    public HashMap<String, Object> getAddOnServiceMetaData() {
        return addOnServiceMetaData;
    }
    
    public void setAddOnServiceMetaData(HashMap<String, Object> addOnServiceMetaData) {
        this.addOnServiceMetaData = addOnServiceMetaData;
    }
    
    public void putAddOnServiceMetaData(String key, Object value) {
        if (null == this.addOnServiceMetaData) {
          this.addOnServiceMetaData = new HashMap<>();
        }

        this.addOnServiceMetaData.put(key, value);
    }
    
    @SuppressWarnings("unchecked")
    public List<AddOnServiceMataVo> getAddOnServiceMetaData(String key) {
        if (null == this.addOnServiceMetaData) return null;
        
        return (List<AddOnServiceMataVo>) this.addOnServiceMetaData.get(key);
    }
    
    @PreDestroy
    public void cleanUp() throws Exception {
        this.userId = null;
        this.firstName = null;
        this.namespace = null;
        this.addOnServiceMetaData = null;
    }

}
