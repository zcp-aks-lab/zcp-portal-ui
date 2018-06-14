package com.skcc.cloudz.zcp.portal.iam.namespace.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.skcc.cloudz.zcp.api.iam.domain.vo.ApiResponseVo;
import com.skcc.cloudz.zcp.api.iam.service.impl.IamRestClient;
import com.skcc.cloudz.zcp.common.constants.ApiResult;

@Service
public class NamespaceService {
    
	@Autowired
    private IamRestClient client;
	
    public Map<String, Object> getResourceQuota() throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        ApiResponseVo response = client.request(HttpMethod.GET, "/iam/resourceQuota", null);
        if (!response.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new Exception(response.getMsg());
        }
        
        resultMap.putAll(response.getData());
    
        return resultMap;
    }
    
    public void deleteNamespace(String namespace) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        ApiResponseVo response = client.request(HttpMethod.DELETE, "/iam/namespace/"+ namespace, null);
        if (!response.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new Exception(response.getMsg());
        }
        
    }

}
