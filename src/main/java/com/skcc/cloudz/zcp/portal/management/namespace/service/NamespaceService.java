package com.skcc.cloudz.zcp.portal.management.namespace.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skcc.cloudz.zcp.api.iam.domain.vo.ApiResponseVo;
import com.skcc.cloudz.zcp.api.iam.service.impl.IamRestClient;
import com.skcc.cloudz.zcp.common.constants.ApiResult;
import com.skcc.cloudz.zcp.common.security.service.SecurityService;
import com.skcc.cloudz.zcp.common.util.NumberUtil;
import com.skcc.cloudz.zcp.portal.management.namespace.vo.EnquryNamespaceVO;
import com.skcc.cloudz.zcp.portal.management.user.vo.ZcpNamespace;
import com.skcc.cloudz.zcp.portal.management.user.vo.ZcpNamespaceList;

@Service
public class NamespaceService {
    
	private final Logger logger = (Logger) LoggerFactory.getLogger(NamespaceService.class);
	
	@Autowired
    private IamRestClient client;
	
	@Autowired
    private SecurityService securityService;
	
    public Object getResourceQuota(EnquryNamespaceVO vo) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String param = "userId="+ securityService.getUserDetails().getUserId();
        ApiResponseVo response = client.request("/iam/metrics/namespaces?"+param, null);
        
        if(response.getCode().equals("ZCP-0001")) {
	    	logger.debug(response.getMsg());
	    	List<Object> empty = new ArrayList<>();
	    	resultMap.put("items", empty);
	    	resultMap.put("errorMsg", response.getMsg());
	    	return resultMap;
	    }
        
        if (!response.getCode().equals(ApiResult.SUCCESS.getCode())) {
        	throw new Exception(response.getMsg());
        }
        
        ObjectMapper mapper = new ObjectMapper(); 
        ZcpNamespaceList namespaceList = mapper.convertValue(response.getData(), ZcpNamespaceList.class);
        List<ZcpNamespace> listQuotas = namespaceList.getItems();
        Stream<ZcpNamespace> stream = null;
        
        
        if (!StringUtils.isEmpty(vo.getSortItem())) {
			stream = namespaceList.getItems().stream();
			switch (vo.getSortItem()) {
			case "namespace":
				if (vo.isSortOrder())
					stream = stream.sorted((a, b) -> a.getName().compareTo(b.getName()));// asc
				else
					stream = stream.sorted((a, b) -> b.getName().compareTo(a.getName()));
				break;
			case "cpuR":
				if (vo.isSortOrder())
					stream = stream.sorted((a, b) -> a.getUsedCpuRequests().compareTo(b.getUsedCpuRequests()));
				else
					stream = stream.sorted((a, b) -> b.getUsedCpuRequests().compareTo(a.getUsedCpuRequests()));
				break;
			case "cpuL":
				if (vo.isSortOrder())
					stream = stream.sorted((a, b) -> a.getUsedCpuLimits().compareTo(b.getUsedCpuLimits()));
				else
					stream = stream.sorted((a, b) -> b.getUsedCpuLimits().compareTo(a.getUsedCpuLimits()));
				break;
			case "memoryR":
				if (vo.isSortOrder())
					stream = stream.sorted((a, b) -> a.getUsedMemoryRequests().compareTo(b.getUsedMemoryRequests()));
				else
					stream = stream.sorted((a, b) -> a.getUsedMemoryRequests().compareTo(b.getUsedMemoryRequests()));
				break;
			case "memoryL":
				if (vo.isSortOrder())
					stream = stream.sorted((a, b) -> a.getUsedMemoryLimits().compareTo(b.getUsedMemoryLimits()));
				else
					stream = stream.sorted((a, b) -> a.getUsedMemoryLimits().compareTo(b.getUsedMemoryLimits()));
				break;
			case "user":
				if (vo.isSortOrder())
					stream = stream.sorted((a, b) -> NumberUtil.compare(a.getUserCount(), b.getUserCount()));
				else
					stream = stream.sorted((a, b) -> NumberUtil.compare(b.getUserCount(), a.getUserCount()));
				break;
			case "status":
				if (vo.isSortOrder())
					stream = stream.sorted((a, b) -> a.getStatus().compareTo(b.getStatus()));
				else
					stream = stream.sorted((a, b) -> b.getStatus().compareTo(a.getStatus()));
				break;
			case "createTime":
				if (vo.isSortOrder())
					stream = stream.sorted((a, b) -> a.getCreationDate().compareTo(b.getCreationDate()));
				else
					stream = stream.sorted((a, b) -> b.getCreationDate().compareTo(a.getCreationDate()));
				break;
			}
		}
		
		if (!StringUtils.isEmpty(vo.getNamespace())) {
			stream = stream.filter(namespace -> namespace.getName().indexOf(vo.getNamespace()) > -1);
		}

		if (stream != null)
			listQuotas = stream.collect(Collectors.toList());
		
		
		

		namespaceList.setItems(listQuotas);
		return namespaceList;
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
    	String param = "userId="+ securityService.getUserDetails().getUserId();
        String url = "/iam/namespace/{namespace}/resource?"+param;
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
	    		}
	    		if(user.get("email") != null && user.get("email").indexOf(searchWord) > -1) {
	    			newUser.add(user);
	    		}
	    		if(user.get("lastName")!= null && user.get("lastName").indexOf(searchWord) > -1) {
	    			newUser.add(user);
	    		}
	    		if(user.get("firstName")!= null && user.get("firstName").indexOf(searchWord) > -1) {
	    			newUser.add(user);
	    		}
	    		if( user.get("firstName")!= null && user.get("lastName")!= null
	    				&& (user.get("firstName") + user.get("lastName")).indexOf(searchWord) > -1) {
	    			newUser.add(user);
	    		}
	    		newUser = newUser.stream().distinct().collect(Collectors.toList());
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
    
    public void addLableOfNamespace(HashMap<String, Object> data) throws Exception{
    	ApiResponseVo resUser = client.request(HttpMethod.POST, "/iam/namespace/" + data.get("namespace") + "/label", data);
    	if(!resUser.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new Exception(resUser.getMsg());
        }
        
    }
    
    public Map<String, Object> getLableOfNamespace(HashMap<String, Object> data) throws Exception{
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	ApiResponseVo response = client.request(HttpMethod.GET, "/iam/namespace/" + data.get("namespace") + "/labels", null);
    	if(!response.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new Exception(response.getMsg());
        }
    	resultMap.putAll(response.getData());
        return resultMap;
        
    }
    
    public void delLableOfNamespace(HashMap<String, Object> data) throws Exception{
    	ApiResponseVo resUser = client.request(HttpMethod.DELETE, "/iam/namespace/" + data.get("namespace") + "/label", data);
    	if(!resUser.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new Exception(resUser.getMsg());
        }
        
    }
}
