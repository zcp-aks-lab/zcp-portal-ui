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
    	ApiResponseVo response =null;
    	if(StringUtils.isEmpty(namespace)) {
    		response = client.request(HttpMethod.GET, "/iam/users", null);
            if(!response.getCode().equals(ApiResult.SUCCESS.getCode())) {
                throw new Exception(response.getMsg());
            }
            
    		
    	}else {
    		response = client.request(HttpMethod.GET, "/iam/namespace/"+namespace + "/users", null);
            if(!response.getCode().equals(ApiResult.SUCCESS.getCode())) {
                throw new Exception(response.getMsg());
            }
    	}
    	String searchWord = data.get("searchWord");
    	if(StringUtils.isNoneEmpty(searchWord)) {
	    	List<Object> newUser = new ArrayList();
	    	List<Map<String, String>> users =(List<Map<String, String>>)((Map<String, Object>)response.getData()).get("items");
	    	for(Map<String, String> user : users) {
	    		if(user.get("username").indexOf(searchWord) > -1) {
	    			newUser.add(user);
	    		}else if(user.get("email") != null && user.get("email").indexOf(searchWord) > -1) {
	    			newUser.add(user);
	    		}else if(user.get("lastName").indexOf(searchWord) > -1) {
	    			newUser.add(user);
	    		}else if(user.get("firstName").indexOf(searchWord) > -1) {
	    			newUser.add(user);
	    		}else if((user.get("firstName") + user.get("lastName")).indexOf(searchWord) > -1) {
	    			newUser.add(user);
	    		}
	    	}
	    	Map<String, Object> rtnData = new HashMap<>();
	    	rtnData.put("items", newUser);
	    	
	    	return rtnData;
    	}
    	
    	 resultMap.putAll(response.getData());
    	    
         return resultMap;
    }
    
    public void addUserInNamespace(HashMap<String, Object> data) throws Exception{
    	ApiResponseVo resUser = client.request(HttpMethod.POST, "/iam/namespace/" + data.get("namespace") + "/roleBinding", data);
        if(!resUser.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new Exception(resUser.getMsg());
        }
        
    }
    
    public void modifyNamespaceRole(HashMap<String, Object> data) throws Exception{
    	ApiResponseVo resUser = client.request(HttpMethod.PUT, "/iam/namespace/" + data.get("namespace") + "/roleBinding", data);
    	if(!resUser.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new Exception(resUser.getMsg());
        }
        
    }
    
    public void delNamespaceRole(HashMap<String, Object> data) throws Exception{
    	ApiResponseVo resUser = client.request(HttpMethod.DELETE, "/iam/namespace/" + data.get("namespace") + "/roleBinding", data);
    	if(!resUser.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new Exception(resUser.getMsg());
        }
        
    }
}
