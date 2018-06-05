package com.skcc.cloudz.zcp.common.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.skcc.cloudz.zcp.common.service.IamApiService;

@Service
public class IamApiServiceImpl implements IamApiService {
    
    private static final Logger log = LoggerFactory.getLogger(IamApiServiceImpl.class);
    
    @Value("${props.iam.baseUrl}")
    private String iamBaseUrl;
    
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Map<String, Object> getUser() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        log.debug("iamBaseUrl : {}", iamBaseUrl);
        log.debug("restTemplate : {}", restTemplate);
        
        // TODO: IAM API CALL
        
        
        return resultMap;
    }

    @Override
    public Map<String, Object> getNamespace() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getClusterRoleBinding() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        resultMap.put("accessRole", "admin");
        
        return resultMap;
    }

}
