package com.skcc.cloudz.zcp.portal.system.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skcc.cloudz.zcp.common.service.IamApiService;
import com.skcc.cloudz.zcp.portal.system.service.MyService;

@Service
public class MyServiceImpl implements MyService {
    
    private static final Logger log = LoggerFactory.getLogger(MyServiceImpl.class);
    
    @Autowired
    private IamApiService iamApiService;
    
    @Override
    public Map<String, Object> getMyInfoByUsername(String username) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        if (log.isDebugEnabled()) {
            log.debug("username : {}", username);
        }
        
        try {
            resultMap = iamApiService.getUser(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return resultMap;
    }

    
}
