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

@Service
public class MainService {
    
    private static final Logger log = LoggerFactory.getLogger(MainService.class);
    
    @Autowired
    private IamApiService iamApiService;
    
    public List<ZcpNodeVo> getNodes() throws Exception {
        ZcpNodeListVo zcpNodeListVo = iamApiService.getNodes();
        if (!zcpNodeListVo.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new ZcpPortalException(zcpNodeListVo.getMsg());
        }
        
        return zcpNodeListVo.getData().getItems();
    }
    
    public Map<String, Object> getDeploymentsStatus() throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        ApiResponseVo apiResponseVo = iamApiService.getDeploymentsStatus("");
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
    
    public Map<String, Object> getPodsStatus() throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        ApiResponseVo apiResponseVo = iamApiService.getPodsStatus("");
        if (!apiResponseVo.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new ZcpPortalException(apiResponseVo);
        }
        
        resultMap.putAll(apiResponseVo.getData());
        
        return resultMap;
    }
    
    public Map<String, Object> getMemoryStatus() throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        ApiResponseVo apiResponseVo = iamApiService.getMemoryStatus();
        if (!apiResponseVo.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new ZcpPortalException(apiResponseVo);
        }
        
        resultMap.putAll(apiResponseVo.getData());
        
        return resultMap;
    }
    
    public Map<String, Object> getCpuStatus() throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        ApiResponseVo apiResponseVo = iamApiService.getCpuStatus();
        if (!apiResponseVo.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new ZcpPortalException(apiResponseVo);
        }
        
        resultMap.putAll(apiResponseVo.getData());
        
        return resultMap;
    }

}
