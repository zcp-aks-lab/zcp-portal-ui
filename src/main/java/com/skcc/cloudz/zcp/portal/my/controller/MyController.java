package com.skcc.cloudz.zcp.portal.my.controller;

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
import com.skcc.cloudz.zcp.portal.my.service.MyService;
import com.skcc.cloudz.zcp.portal.my.vo.MyUserVo;

@Controller
@RequestMapping(value = MyController.RESOURCE_PATH)
public class MyController {
    
private static final Logger log = LoggerFactory.getLogger(MyController.class);
    
    static final String RESOURCE_PATH = "/my";
    
    @Autowired
    private MyService myService;
    
    @GetMapping(value = "/profile", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String myInfo(Model model) throws Exception {
        model.addAttribute("zcpUser", myService.getMyUser());
        
        return "content/my/my-profile";
    }
    
    @GetMapping(value = "/pwd", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String myPwd(Model model) throws Exception {
        return "content/my/my-pwd";
    }
    
    @GetMapping(value = "/cli", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String myCli(Model model) throws Exception {
        return "content/my/my-cli";
    }
    
    @PostMapping(value = "/updateMyProfile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public Map<String, Object> updatedMyProfile(@RequestBody MyUserVo myUserVo) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            myService.updateUser(myUserVo);
            
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
    public Map<String, Object> updatedPassword(@RequestBody MyUserVo myUserVo) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            myService.updatePassword(myUserVo);
            
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
