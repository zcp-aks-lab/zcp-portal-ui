package com.skcc.cloudz.zcp.portal.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skcc.cloudz.zcp.api.iam.domain.vo.ApiResponseVo;
import com.skcc.cloudz.zcp.api.iam.domain.vo.ZcpNodeListVo;
import com.skcc.cloudz.zcp.api.iam.domain.vo.ZcpNodeVo;
import com.skcc.cloudz.zcp.api.iam.service.IamApiService;
import com.skcc.cloudz.zcp.common.constants.ApiResult;
import com.skcc.cloudz.zcp.common.exception.ZcpPortalException;
import com.skcc.cloudz.zcp.common.util.CommonUtil;
import com.skcc.cloudz.zcp.portal.my.service.MyService;

@Service
public class MainService {
    
    private static final Logger log = LoggerFactory.getLogger(MainService.class);
    
    static final String TYPE_MEMORY = "memory";
    static final String TYPE_CPU = "cpu";
    
    @Autowired
    private IamApiService iamApiService;
    
    @Autowired
    private MyService myService;
    
    @Autowired
    private K8sSsoService k8sSsoService;
    
    public List<ZcpNodeVo> getNodes() throws Exception {
        ZcpNodeListVo zcpNodeListVo = iamApiService.getNodes();
        if (!zcpNodeListVo.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new ZcpPortalException(zcpNodeListVo.getMsg());
        }
        
        return zcpNodeListVo.getData().getItems();
    }
    
    public Map<String, Object> getDeploymentsStatus(String namespace) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        ApiResponseVo apiResponseVo = iamApiService.getDeploymentsStatus(namespace);
        if (!apiResponseVo.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new ZcpPortalException(apiResponseVo);
        }
        
        resultMap.putAll(apiResponseVo.getData());
        
        return resultMap;
    }
    
    public Map<String, Object> getNodesStatus() throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        ApiResponseVo apiResponseVo = iamApiService.getNodesStatus("");
        if (!apiResponseVo.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new ZcpPortalException(apiResponseVo);
        }
        
        resultMap.putAll(apiResponseVo.getData());
        
        return resultMap;
    }
    
    public Map<String, Object> getPodsStatus(String namespace) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        ApiResponseVo apiResponseVo = iamApiService.getPodsStatus(namespace);
        if (!apiResponseVo.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new ZcpPortalException(apiResponseVo);
        }
        
        resultMap.putAll(apiResponseVo.getData());
        
        return resultMap;
    }
    
    public Map<String, Object> getMemoryStatus() throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        ApiResponseVo apiResponseVo = iamApiService.getClusterStatus(TYPE_MEMORY);
        if (!apiResponseVo.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new ZcpPortalException(apiResponseVo);
        }
        
        resultMap.putAll(apiResponseVo.getData());
        
        return resultMap;
    }
    
    public Map<String, Object> getCpuStatus() throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        ApiResponseVo apiResponseVo = iamApiService.getClusterStatus(TYPE_CPU);
        if (!apiResponseVo.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new ZcpPortalException(apiResponseVo);
        }
        
        resultMap.putAll(apiResponseVo.getData());
        
        return resultMap;
    }
    
    public Map<String, Object> getUsersStatus(String namespace) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        ApiResponseVo apiResponseVo = iamApiService.getUsersStatus(namespace);
        if (!apiResponseVo.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new ZcpPortalException(apiResponseVo);
        }
        
        resultMap.putAll(apiResponseVo.getData());
        
        return resultMap;
    }
    
    @SuppressWarnings("unchecked")
    public Map<String, Object> getJweToken() throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        /* get csfr token */
        String csrfToken = k8sSsoService.getCsrfToken();
        log.info("csrfToken : {}", csrfToken);
        
        /* get login user token */
        Map<String, Object> kubeConfig = myService.getKubeConfig();
        log.info("users : {}", kubeConfig.get("users"));
        
        List<HashMap<String, Object>> users = (List<HashMap<String, Object>>) kubeConfig.get("users");
        log.info("user : {}", ((HashMap<String, Object>)users.get(0).get("user")).get("token").toString());
        String token = ((HashMap<String, Object>)users.get(0).get("user")).get("token").toString();
        
        /* login  */
        HashMap<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("kubeConfig", "");
        reqMap.put("password", "");
        //reqMap.put("token", "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJ6Y3Atc3lzdGVtIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6InpjcC1zeXN0ZW0tYWRtaW4tdG9rZW4tcmptN24iLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC5uYW1lIjoiemNwLXN5c3RlbS1hZG1pbiIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VydmljZS1hY2NvdW50LnVpZCI6ImJlOTI0MmM2LTYyOTctMTFlOC1iNzZhLTM2YjhlMjg5NmNjNCIsInN1YiI6InN5c3RlbTpzZXJ2aWNlYWNjb3VudDp6Y3Atc3lzdGVtOnpjcC1zeXN0ZW0tYWRtaW4ifQ.onxzYoJnX7f8UZ2RNMw_mExBv6Qavs-7KH92sLEJdeRdis24caiAhCJWG3_G73zmSJpij21fxsPKYkp9GQ67HySTZTYZRm-UcwPGIoWiRLO-ahiwaBftLuLykgo-92woPRdA-5NKmNX1NrWXLuhEA4LHIiNJYqXcN2snmUpmEDLztX4-jtJkUypCvsiitz9T-1ozyFSpSWXd_gjs_IabI2KCQM2l3-2dEehaM_-r0OZDI7S9OdmsBSQKQJ_sPkpGU4LyCsur9eHH5yx27FvPgsxX6sZJeG2bIVpNDcbhpn9jv27eaiiTCHfb8kBr8Y95-k2mD136Uy_A2fLZaq4G9WsgIewgZ0Fp0biMpd6Ma4PRxDvOUtIxsd7i8kdO2Ozo7NWQ7B_7HXX4px-w6FPtU6mgqYRV36nD3zz-IC7_YvHZGWfyE_U6xcre_HBbWoy6FCMi8R4z__-pb3EAq_ARKw-_XwQVF062WICjJy6TkoKlCB_w5vB9RfY9fPZR0HVu0t2toh5zQyCMu3x6x900M5tEyxDZNQN0d6dRDSH3TkvsDqJ5mV7xiHCWWIqvqKJvIXo_XwNMKpGhSItCMlGcwx-eUxFhR_ksXXUBz9jGRPkG2kBYzy7s7zMaUeDmsjzbE_6LLyaMwjoNl2CJd6uBsjqEAYG--KFQQ6GHL0CyOew");
        reqMap.put("token", token);
        reqMap.put("username", "");
        
        Map<String, Object> login = k8sSsoService.login(csrfToken, reqMap);
        
        /* login status */
        String jweToken = login.get("jweToken").toString();
        String encodedJweToken = CommonUtil.getInstance().encodeURIComponent(login.get("jweToken").toString());
        Map<String, Object> loginStatus = k8sSsoService.loginStatus(encodedJweToken);
        log.info("loginStatus : {}", loginStatus);
        
        /* get token */
        String refreshToken = k8sSsoService.getToken(encodedJweToken);
        log.info("refreshToken : {}", refreshToken);
        
        /* refresh token */
        reqMap = new HashMap<String, Object>();
        reqMap.put("jweToken", login.get("jweToken"));
        Map<String, Object> refresh = k8sSsoService.refresh(jweToken, encodedJweToken, refreshToken, reqMap);
        log.info("refresh : {}", refresh);
        
        resultMap.putAll(refresh);
        
        return resultMap;
    }

}
