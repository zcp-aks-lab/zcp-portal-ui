package com.skcc.cloudz.zcp.portal.system.service;

import java.util.Map;

import com.skcc.cloudz.zcp.api.iam.domain.vo.ZcpUserVo;
import com.skcc.cloudz.zcp.portal.system.domain.dto.MyUserDto;

public interface MyService {
    
    ZcpUserVo getMyInfo() throws Exception;
    
    void updateUser(MyUserDto myUserDto) throws Exception;
    
    void updatePassword(MyUserDto myUserDto) throws Exception;
    
    Map<String, Object> getKubeConfig() throws Exception;;

}
