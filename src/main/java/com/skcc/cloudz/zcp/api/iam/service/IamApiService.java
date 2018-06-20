package com.skcc.cloudz.zcp.api.iam.service;

import java.util.HashMap;

import com.skcc.cloudz.zcp.api.iam.domain.vo.ApiResponseVo;
import com.skcc.cloudz.zcp.api.iam.domain.vo.ZcpUserListVo;
import com.skcc.cloudz.zcp.api.iam.domain.vo.ZcpUserResVo;
import com.skcc.cloudz.zcp.portal.system.domain.dto.MyUserDto;

public interface IamApiService {
    
    ZcpUserResVo getUser(String id);
    
    ApiResponseVo updateUser(String id, HashMap<String, Object> reqMap);
    
    ApiResponseVo updatePassword(MyUserDto myUserDto); 
    
    ApiResponseVo logout(String id);
    
    ApiResponseVo kubeconfig(String id, String namespace);
    
    ApiResponseVo serviceAccount(String id);
    
    ZcpUserListVo users();
    
    ApiResponseVo resetPassword(String id, HashMap<String, Object> reqMap);

}
