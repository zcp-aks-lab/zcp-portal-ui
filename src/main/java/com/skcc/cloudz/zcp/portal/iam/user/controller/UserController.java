package com.skcc.cloudz.zcp.portal.iam.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skcc.cloudz.zcp.api.iam.domain.vo.ZcpUserVo;
import com.skcc.cloudz.zcp.common.constants.ApiResult;
import com.skcc.cloudz.zcp.portal.iam.user.domain.dto.UserDto;
import com.skcc.cloudz.zcp.portal.iam.user.service.UserService;

@Controller
@RequestMapping(value = UserController.RESOURCE_PATH)
public class UserController {
    
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    
    static final String RESOURCE_PATH = "/iam/users";
    
    @Autowired
    private UserService userService;
    
    @GetMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String userList(Model model) throws Exception {
        return "content/iam/user/user-list";
    }
    
    @GetMapping(value = "{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String createPage(@PathVariable("id") String id, Model model) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("id : {}", id);
        }
        
        if (id.equals("create")) {
            return "content/iam/user/user-create";    
        } else {
            ZcpUserVo zcpUserVo = userService.getUser(id);
            model.addAttribute("zcpUser", zcpUserVo);
            
            return "content/iam/user/user-detail";
        }
        
    }
    
    @GetMapping(value = "/getUsers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getUsers() throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            resultMap.put("resultCd", ApiResult.SUCCESS.getCode());    
            resultMap.put("resultMsg", ApiResult.SUCCESS.getName());
            resultMap.put("resultData", userService.getUsers());
        } catch (Exception e) {
            e.printStackTrace();
            
            resultMap.put("resultCd", ApiResult.FAIL.getCode());
            resultMap.put("resultMsg", e.getMessage());
        }
        
        return resultMap;
    }
    
    @PostMapping(value = "/updateUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public Map<String, Object> updateUser(@RequestBody UserDto userDto) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            userService.updateUser(userDto);
            
            resultMap.put("resultCd", ApiResult.SUCCESS.getCode());    
            resultMap.put("resultMsg", ApiResult.SUCCESS.getName());
        } catch (Exception e) {
            e.printStackTrace();
            
            resultMap.put("resultCd", ApiResult.FAIL.getCode());
            resultMap.put("resultMsg", e.getMessage());
        }
        
        return resultMap;
    }
    
    @PostMapping(value = "/resetPassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public Map<String, Object> resetPassword(@RequestBody UserDto userDto) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            userService.resetPassword(userDto);
            
            resultMap.put("resultCd", ApiResult.SUCCESS.getCode());    
            resultMap.put("resultMsg", ApiResult.SUCCESS.getName());
        } catch (Exception e) {
            e.printStackTrace();
            
            resultMap.put("resultCd", ApiResult.FAIL.getCode());
            resultMap.put("resultMsg", e.getMessage());
        }
        
        return resultMap;
    }
    
    
    

}
