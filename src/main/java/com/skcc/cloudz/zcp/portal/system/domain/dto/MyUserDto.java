package com.skcc.cloudz.zcp.portal.system.domain.dto;

import com.skcc.cloudz.zcp.common.domain.dto.CommonDto;

public class MyUserDto extends CommonDto {
    private String email;
    private String firstName;
    
    public MyUserDto() {}
    
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

}
