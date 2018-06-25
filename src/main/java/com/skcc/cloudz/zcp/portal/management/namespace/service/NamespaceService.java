package com.skcc.cloudz.zcp.portal.management.namespace.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.skcc.cloudz.zcp.api.iam.domain.vo.ApiResponseVo;
import com.skcc.cloudz.zcp.api.iam.service.impl.IamRestClient;
import com.skcc.cloudz.zcp.common.constants.ApiResult;
import com.skcc.cloudz.zcp.portal.management.namespace.vo.EnquryNamespaceVO;

@Service
public class NamespaceService {
    
	private final Logger logger = (Logger) LoggerFactory.getLogger(NamespaceService.class);
	
	@Autowired
    private IamRestClient client;
	
    public Map<String, Object> getResourceQuota(EnquryNamespaceVO vo) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        ApiResponseVo response = client.request("/iam/resourceQuotas", vo);
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
    
    public void createNamespace(HashMap<String, Object> data) throws Exception {
        ApiResponseVo response = client.request(HttpMethod.POST, "/iam/namespace", data);
        if (!response.getCode().equals(ApiResult.SUCCESS.getCode())) {
        	logger.debug(response.getMsg());
            throw new Exception(response.getMsg());
        }
    }
    
    public Map<String, Object> getUsers(HashMap<String, String> data) throws Exception{
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	String namespace = data.get("namespace");
    	
    	ApiResponseVo response = client.request(HttpMethod.GET, "/iam/users", null);
        if(!response.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new Exception(response.getMsg());
        }
        
    	if(StringUtils.isEmpty(namespace)) {
    		resultMap.putAll(response.getData());
    	}else {
    		Map<String, Object> items = (Map<String, Object>)response.getData();
    		List<Map<String, Object>> users = (List<Map<String, Object>>)items.get("items");
    		List<Map<String, Object>> newUsers = new ArrayList<>();
    		for(Map<String, Object> user : users) {
    			List<String> namespaceNames = (List<String>)user.get("defaultNamespace");
    			if(namespaceNames != null)
	    			for(String name : namespaceNames) {
	    				if(namespace.equals(name)) {
	    					newUsers.add(user);
	    				}
	    			}
    		}
    		resultMap.put("items", newUsers);
    	}
    	
    	return resultMap;
    }
    
    public void addUserInNamespace(HashMap<String, Object> data) throws Exception{
    	ApiResponseVo resUser = client.request(HttpMethod.GET, "/iam/user/" + data.get("id"), null);
        if(!resUser.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new Exception(resUser.getMsg());
        }
        
        Map<String, Object> map = resUser.getData();
        Map<String, Object> toSaveAttribute = (Map<String, Object>)data.get("defaultNamespace");
        List<String> newNamespace = getDefaultNamespace((Map<String, Object> )map.get("attribute"), (String)toSaveAttribute.get("defaultNamespace"));
        toSaveAttribute.put("defaultNamespace", (List<String>)newNamespace);
        
    	ApiResponseVo response = client.request(HttpMethod.PUT, "/iam/user/"+ data.get("id") , data);
        if(!response.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new Exception(response.getMsg());
        }
    }
    
    private List<String> getDefaultNamespace(Map<String, Object> beforeNamespace, String newNamespace) {//기존것에 추가된 namespace
    	List<String> defaultNamespace = null;
    	if(beforeNamespace == null) {
    		defaultNamespace = new ArrayList<>();
        	defaultNamespace.add(newNamespace);	
    	}else{
    		defaultNamespace =(List<String>)beforeNamespace.get("defaultNamespace");
        	defaultNamespace.add(newNamespace);
    	}
    	
    	return defaultNamespace;
    }
        
        

}
