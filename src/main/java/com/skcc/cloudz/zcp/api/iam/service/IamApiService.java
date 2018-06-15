package com.skcc.cloudz.zcp.api.iam.service;

import com.skcc.cloudz.zcp.api.iam.domain.vo.ApiResponseVo;
import com.skcc.cloudz.zcp.api.iam.domain.vo.ZcpUserResVo;
import com.skcc.cloudz.zcp.portal.system.domain.dto.MyUserDto;

public interface IamApiService {
    
    ZcpUserResVo getUser(String userId);
    
    ApiResponseVo setUser(MyUserDto myUserDto);
    
    ApiResponseVo updatePassword(MyUserDto myUserDto); 
    
    ApiResponseVo logout(String userId);
    
    ApiResponseVo kubeconfig(String userId, String namespace);
    
    ApiResponseVo serviceAccount(String userId);

}
