package com.skcc.cloudz.zcp.portal.system.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skcc.cloudz.zcp.common.constants.ApiResult;
import com.skcc.cloudz.zcp.portal.system.domain.dto.MyUserDto;
import com.skcc.cloudz.zcp.portal.system.service.MyService;

@Controller
@RequestMapping(value = MyController.RESOURCE_PATH)
public class MyController {
    
    private static final Logger log = LoggerFactory.getLogger(MyController.class);
    
    static final String RESOURCE_PATH = "/my";
    
    @Autowired
    private MyService myService;
    
    @GetMapping(value = "/my-info", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String myInfo(Model model) throws Exception {
        model.addAttribute("content", myService.getMyInfo());
        
        return "content/system/my/my-info";
    }
    
    @GetMapping(value = "/my-pwd", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String myPwd(Model model) throws Exception {
        return "content/system/my/my-pwd";
    }
    
    @GetMapping(value = "/my-cli", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String myCli(Model model) throws Exception {
        return "content/system/my/my-cli";
    }
    
    @PostMapping(value = "/updatedMyInfo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public Map<String, Object> updatedMyInfo(@RequestBody MyUserDto myUserDto) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            myService.updateUser(myUserDto);
            
            resultMap.put("resultCd", ApiResult.SUCCESS.getCode());    
            resultMap.put("resultMsg", ApiResult.SUCCESS.getName());
        } catch (Exception e) {
            e.printStackTrace();
            
            resultMap.put("resultCd", ApiResult.FAIL.getCode());
            resultMap.put("resultMsg", e.getMessage());
        }
        
        return resultMap;
    }
    
    @PostMapping(value = "/updatedPassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public Map<String, Object> updatedPassword(@RequestBody MyUserDto myUserDto) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            myService.updatePassword(myUserDto);
            
            resultMap.put("resultCd", ApiResult.SUCCESS.getCode());    
            resultMap.put("resultMsg", ApiResult.SUCCESS.getName());
        } catch (Exception e) {
            e.printStackTrace();
            
            resultMap.put("resultCd", ApiResult.FAIL.getCode());
            resultMap.put("resultMsg", e.getMessage());
        }
        
        return resultMap;
    }
    
    @GetMapping(value = "/getZcpKubeConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getZcpKubeConfig() throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            resultMap.put("resultCd", ApiResult.SUCCESS.getCode());    
            resultMap.put("resultMsg", ApiResult.SUCCESS.getName());
            resultMap.put("resultData", myService.getKubeConfig());
        } catch (Exception e) {
            e.printStackTrace();
            
            resultMap.put("resultCd", ApiResult.FAIL.getCode());
            resultMap.put("resultMsg", e.getMessage());
        }
        
        return resultMap;
    }

}
