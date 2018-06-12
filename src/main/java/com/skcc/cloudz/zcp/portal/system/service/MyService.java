package com.skcc.cloudz.zcp.portal.system.service;

import java.util.Map;

import com.skcc.cloudz.zcp.portal.system.domain.dto.MyUserDto;

public interface MyService {
    
    Map<String, Object> getMyInfo() throws Exception;
    
    void updateUser(MyUserDto myUserDto) throws Exception;
    
    void updatePassword(MyUserDto myUserDto) throws Exception;

}
