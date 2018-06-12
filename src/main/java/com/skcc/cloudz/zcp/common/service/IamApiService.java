package com.skcc.cloudz.zcp.common.service;

import com.skcc.cloudz.zcp.common.domain.vo.ApiResponseVo;
import com.skcc.cloudz.zcp.portal.system.domain.dto.MyUserDto;

public interface IamApiService {
    
    ApiResponseVo getUser(String userId);
    
    ApiResponseVo setUser(MyUserDto myUserDto);
    
    ApiResponseVo updatePassword(MyUserDto myUserDto); 

}
