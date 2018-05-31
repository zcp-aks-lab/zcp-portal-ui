package com.skcc.cloudz.zcp.service.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skcc.cloudz.zcp.common.service.IamApiService;
import com.skcc.cloudz.zcp.service.MyService;

@Service
public class MyServiceImpl implements MyService {
    
    private static final Logger log = LoggerFactory.getLogger(MyServiceImpl.class);
    
    @Autowired
    private IamApiService iamApiService;
    
    @Override
    public String getMyInfoById() {
        String baseUrl = StringUtils.EMPTY;
        
        try {
            Map<String, Object> userMap = iamApiService.getUser();
            
            baseUrl = userMap.get("iamBaseUrl") != null ? userMap.get("iamBaseUrl").toString() : "";
            
            log.debug("MyServiceImpl baseUrl : {}", baseUrl);
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        
        return baseUrl;
    }

    
}
