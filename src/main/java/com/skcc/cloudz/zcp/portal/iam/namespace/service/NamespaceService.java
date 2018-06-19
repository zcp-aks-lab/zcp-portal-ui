package com.skcc.cloudz.zcp.portal.iam.namespace.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.skcc.cloudz.zcp.api.iam.domain.vo.ApiResponseVo;
import com.skcc.cloudz.zcp.api.iam.service.impl.IamRestClient;
import com.skcc.cloudz.zcp.common.constants.ApiResult;
import com.skcc.cloudz.zcp.portal.iam.namespace.vo.EnquryNamespaceVO;

@Service
public class NamespaceService {
    
	@Autowired
    private IamRestClient client;
	
    public Map<String, Object> getResourceQuota(EnquryNamespaceVO vo) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        ApiResponseVo response = client.request("/iam/resourceQuota", vo);
        if (!response.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new Exception(response.getMsg());
        }
        
        resultMap.putAll(response.getData());
    
        return resultMap;
    }
    
    public Map<String, Object> getResourceLabel() throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        ApiResponseVo response = client.request(HttpMethod.GET, "/iam/namespace/labels", null);
        if (!response.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new Exception(response.getMsg());
        }
        
        resultMap.putAll(response.getData());
    
        return resultMap;
    }
    
    public void deleteNamespace(String namespace) throws Exception {
        ApiResponseVo response = client.request(HttpMethod.DELETE, "/iam/namespace/"+ namespace, null);
        if (!response.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new Exception(response.getMsg());
        }
    }

    public Map<String, Object> getNamespaceResource(String namespace) throws Exception {
    	if(StringUtils.isEmpty(namespace)) return null;
    	Map<String, Object> resultMap = new HashMap<String, Object>();
        String url = "/iam/namespace/{namespace}/resource";
        ApiResponseVo response = client.request(HttpMethod.GET, url.replace("{namespace}", namespace), null);
        if(!response.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new Exception(response.getMsg());
        }
        
        resultMap.putAll(response.getData());
    
        return resultMap;
    }
    
    public void createNamespace(HashMap<String, String> data) throws Exception {
        ApiResponseVo response = client.request(HttpMethod.PUT, "/iam/namespace", null);
        if (!response.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new Exception(response.getMsg());
        }
    }

}
