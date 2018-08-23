package com.skcc.cloudz.zcp.portal.my.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skcc.cloudz.zcp.api.iam.domain.vo.ApiResponseVo;
import com.skcc.cloudz.zcp.api.iam.domain.vo.ZcpUserResVo;
import com.skcc.cloudz.zcp.api.iam.domain.vo.ZcpUserVo;
import com.skcc.cloudz.zcp.api.iam.service.IamApiService;
import com.skcc.cloudz.zcp.common.constants.ApiResult;
import com.skcc.cloudz.zcp.common.exception.ZcpPortalException;
import com.skcc.cloudz.zcp.common.security.service.SecurityService;
import com.skcc.cloudz.zcp.portal.my.vo.MyUserVo;

@Service
public class MyService {
    
    @Autowired
    private IamApiService iamApiService;
    
    @Autowired
    private SecurityService securityService;
    
    public ZcpUserVo getMyUser() throws Exception {
        String id = securityService.getUserDetails().getUserId();
        
        ZcpUserResVo zcpUserResVo = iamApiService.getUser(id);
        if (!zcpUserResVo.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new ZcpPortalException(zcpUserResVo.getCode(), zcpUserResVo.getMsg());
        }
        
        return zcpUserResVo.getData();
    }
    
    public void updateUser(MyUserVo myUserVo) throws Exception {
        HashMap<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("defaultNamespace", myUserVo.getDefaultNamespace());
        reqMap.put("email", myUserVo.getEmail());
        reqMap.put("firstName", myUserVo.getFirstName());
        reqMap.put("username", myUserVo.getUsername());
        
        String userId = securityService.getUserDetails().getUserId();
        
        ApiResponseVo apiResponseVo = iamApiService.updateUser(userId, reqMap);
        if (!apiResponseVo.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new ZcpPortalException(apiResponseVo);
        }
    }
    
    public void updatePassword(MyUserVo myUserVo) throws Exception {
        HashMap<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("currentPassword", myUserVo.getCurrentPassword());
        reqMap.put("newPassword", myUserVo.getNewPassword());
        
        String userId = securityService.getUserDetails().getUserId();
        
        ApiResponseVo apiResponseVo = iamApiService.updatePassword(userId, reqMap);
        if (!apiResponseVo.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new ZcpPortalException(apiResponseVo);
        }
    }
    
    public void updateOtpPassword() throws Exception {
        String id = securityService.getUserDetails().getUserId();
        
        ApiResponseVo apiResponseVo = iamApiService.updateOtpPassword(id);
        if (!apiResponseVo.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new ZcpPortalException(apiResponseVo);
        }
    }
    
    public void deleteOtpPassword() throws Exception {
        String id = securityService.getUserDetails().getUserId();
        
        ApiResponseVo apiResponseVo = iamApiService.deleteOtpPassword(id);
        if (!apiResponseVo.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new ZcpPortalException(apiResponseVo);
        }
    }
    
    public Map<String, Object> getKubeConfig() throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String namespace = securityService.getUserDetails().getDefaultNamespace();
        String id = securityService.getUserDetails().getUserId();
        
        ApiResponseVo apiResponseVo = iamApiService.kubeconfig(id, namespace);
        if (!apiResponseVo.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new ZcpPortalException(apiResponseVo);
        }
        
        resultMap.putAll(apiResponseVo.getData());
        
        return resultMap;
    }

}
