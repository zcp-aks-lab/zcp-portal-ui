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
    
    static final String TYPE_MEMORY = "memory";
    static final String TYPE_CPU = "cpu";
    
    @Autowired
    private IamApiService iamApiService;
    
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

}
