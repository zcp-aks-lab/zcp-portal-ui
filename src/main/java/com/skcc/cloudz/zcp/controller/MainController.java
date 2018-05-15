package com.skcc.cloudz.zcp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skcc.cloudz.zcp.common.security.service.SecurityService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {
    
    @Value("${props.appName}")
    private String appName;
    
    @Autowired
    private SecurityService securityService;
    
    @RequestMapping(value = {"/main", "/"}, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> main() {
        if (log.isDebugEnabled()) {
            log.debug("appName : {}", appName);
        }
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        resultMap.put("resultMsg", "SUCCESS");
        resultMap.put("resultData", securityService.getUserDetails());
        
        return resultMap;
    }

}
