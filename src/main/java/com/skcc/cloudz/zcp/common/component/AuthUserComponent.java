package com.skcc.cloudz.zcp.common.component;

import java.io.Serializable;
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
    
    private String userId;
    private String firstName;
    private List<AddOnServiceMataVo> addOnServiceMetaVoList;
    
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
    public List<AddOnServiceMataVo> getAddOnServiceMetaVoList() {
        return addOnServiceMetaVoList;
    }
    public void setAddOnServiceMetaVoList(List<AddOnServiceMataVo> addOnServiceMetaVoList) {
        this.addOnServiceMetaVoList = addOnServiceMetaVoList;
    }
    
    @PreDestroy
    public void cleanUp() throws Exception {
        this.userId = null;
        this.firstName = null;
        this.addOnServiceMetaVoList = null;
    }

}
