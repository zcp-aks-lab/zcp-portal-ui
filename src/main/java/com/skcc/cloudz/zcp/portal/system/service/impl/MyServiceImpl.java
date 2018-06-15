package com.skcc.cloudz.zcp.portal.system.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skcc.cloudz.zcp.api.iam.domain.vo.ApiResponseVo;
import com.skcc.cloudz.zcp.api.iam.domain.vo.ZcpUserResVo;
import com.skcc.cloudz.zcp.api.iam.domain.vo.ZcpUserVo;
import com.skcc.cloudz.zcp.api.iam.service.IamApiService;
import com.skcc.cloudz.zcp.common.constants.ApiResult;
import com.skcc.cloudz.zcp.common.security.service.SecurityService;
import com.skcc.cloudz.zcp.portal.system.domain.dto.MyUserDto;
import com.skcc.cloudz.zcp.portal.system.service.MyService;

@Service
public class MyServiceImpl implements MyService {
    
    private static final Logger log = LoggerFactory.getLogger(MyServiceImpl.class);
    
    @Autowired
    private IamApiService iamApiService;
    
    @Autowired
    private SecurityService securityService;
    
    @Override
    public ZcpUserVo getMyInfo() throws Exception {
        String userId = securityService.getUserDetails().getUserId();
        log.info("userId : {}", userId);
        
        ZcpUserResVo zcpUserResVo = iamApiService.getUser(userId);
        if (!zcpUserResVo.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new Exception(zcpUserResVo.getMsg());
        }
        
        return zcpUserResVo.getData();
    }

    @Override
    public void updateUser(MyUserDto myUserDto) throws Exception {
        ApiResponseVo apiResponseVo = iamApiService.setUser(myUserDto);
        if (!apiResponseVo.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new Exception(apiResponseVo.getMsg());
        }
    }

    @Override
    public void updatePassword(MyUserDto myUserDto) throws Exception {
        myUserDto.setUserId(securityService.getUserDetails().getUserId());
        
        ApiResponseVo apiResponseVo = iamApiService.updatePassword(myUserDto);
        if (!apiResponseVo.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new Exception(apiResponseVo.getMsg());
        }
    }

    @Override
    public Map<String, Object> getKubeConfig() throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        //String namespace = securityService.getUserDetails().getDefaultNamespace();
        //String userId = securityService.getUserDetails().getUserId();
        
        String userId = "2578332d-0fcb-4e8e-bea0-206c715c0075";
        String namespace = "zcp-system";
        
        
        ApiResponseVo apiResponseVo = iamApiService.kubeconfig(userId, namespace);
        if (!apiResponseVo.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new Exception(apiResponseVo.getMsg());
        }
        
        resultMap.putAll(apiResponseVo.getData());
        
        return resultMap;
    }
    
}
