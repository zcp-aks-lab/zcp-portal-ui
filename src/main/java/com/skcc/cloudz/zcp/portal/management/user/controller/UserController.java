package com.skcc.cloudz.zcp.portal.management.user.controller;

import java.util.HashMap;
import java.util.List;
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
import com.skcc.cloudz.zcp.portal.management.user.service.UserService;
import com.skcc.cloudz.zcp.portal.management.user.vo.UserVo;
import com.skcc.cloudz.zcp.portal.my.controller.MyController;

@Controller
@RequestMapping(value = "/management")
public class UserController {
    
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private UserService userService;
    
    @GetMapping(value = "/users", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String userList(Model model) throws Exception {
        return "content/management/user/user-list";
    }
    
    @GetMapping(value = "/user/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String createPage(@PathVariable("id") String id, Model model) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("id : {}", id);
        }
        
        List<String> clusterRoles = userService.getClusterRoles();
        model.addAttribute("clusterRoles", clusterRoles);
        
        if (id.equals("create")) {
            return "content/management/user/user-create";    
        } else {
            model.addAttribute("zcpUser", userService.getUser(id));
            model.addAttribute("roleBindings", userService.getRoleBindings(id));
            model.addAttribute("namespaces", userService.getNamespaces());
            
            return "content/management/user/user-detail";
        }
    }
    
    @PostMapping(value = "/user/getUsers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getUsers(@RequestBody UserVo userVo) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            resultMap.put("resultCd", ApiResult.SUCCESS.getCode());    
            resultMap.put("resultMsg", ApiResult.SUCCESS.getName());
            resultMap.put("resultData", userService.getUsers(userVo.getKeyword()));
        } catch (Exception e) {
            e.printStackTrace();
            
            resultMap.put("resultCd", ApiResult.FAIL.getCode());
            resultMap.put("resultMsg", e.getMessage());
        }
        
        return resultMap;
    }
    
    @PostMapping(value = "/user/createUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public Map<String, Object> createUser(@RequestBody UserVo userVo) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            userService.setUser(userVo);
            
            resultMap.put("resultCd", ApiResult.SUCCESS.getCode());    
            resultMap.put("resultMsg", ApiResult.SUCCESS.getName());
        } catch (Exception e) {
            e.printStackTrace();
            
            resultMap.put("resultCd", ApiResult.FAIL.getCode());
            resultMap.put("resultMsg", e.getMessage());
        }
        
        return resultMap;
    }
    
    @PostMapping(value = "/user/updateUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public Map<String, Object> updateUser(@RequestBody UserVo userVo) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            userService.updateUser(userVo);
            
            resultMap.put("resultCd", ApiResult.SUCCESS.getCode());    
            resultMap.put("resultMsg", ApiResult.SUCCESS.getName());
        } catch (Exception e) {
            e.printStackTrace();
            
            resultMap.put("resultCd", ApiResult.FAIL.getCode());
            resultMap.put("resultMsg", e.getMessage());
        }
        
        return resultMap;
    }
    
    @PostMapping(value = "/user/deleteUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public Map<String, Object> deleteUser(@RequestBody UserVo userVo) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            userService.deleteUser(userVo.getId());
            
            resultMap.put("resultCd", ApiResult.SUCCESS.getCode());    
            resultMap.put("resultMsg", ApiResult.SUCCESS.getName());
        } catch (Exception e) {
            e.printStackTrace();
            
            resultMap.put("resultCd", ApiResult.FAIL.getCode());
            resultMap.put("resultMsg", e.getMessage());
        }
        
        return resultMap;
    }
    
    @PostMapping(value = "/user/resetPassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public Map<String, Object> resetPassword(@RequestBody UserVo userVo) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            userService.resetPassword(userVo);
            
            resultMap.put("resultCd", ApiResult.SUCCESS.getCode());    
            resultMap.put("resultMsg", ApiResult.SUCCESS.getName());
        } catch (Exception e) {
            e.printStackTrace();
            
            resultMap.put("resultCd", ApiResult.FAIL.getCode());
            resultMap.put("resultMsg", e.getMessage());
        }
        
        return resultMap;
    }
    
    @PostMapping(value = "/user/resetCredentials", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public Map<String, Object> resetCredentials(@RequestBody UserVo userVo) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            userService.resetCredentials(userVo);
            
            resultMap.put("resultCd", ApiResult.SUCCESS.getCode());    
            resultMap.put("resultMsg", ApiResult.SUCCESS.getName());
        } catch (Exception e) {
            e.printStackTrace();
            
            resultMap.put("resultCd", ApiResult.FAIL.getCode());
            resultMap.put("resultMsg", e.getMessage());
        }
        
        return resultMap;
    }
    
    @PostMapping(value = "/user/updateClusterRoleBinding", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public Map<String, Object> updateClusterRoleBinding(@RequestBody UserVo userVo) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            userService.updateClusterRoleBinding(userVo);
            
            resultMap.put("resultCd", ApiResult.SUCCESS.getCode());    
            resultMap.put("resultMsg", ApiResult.SUCCESS.getName());
        } catch (Exception e) {
            e.printStackTrace();
            
            resultMap.put("resultCd", ApiResult.FAIL.getCode());
            resultMap.put("resultMsg", e.getMessage());
        }
        
        return resultMap;
    }
    
    @PostMapping(value = "/user/roleBinding/{mode}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public Map<String, Object> roleBinding(@PathVariable("mode") String mode, @RequestBody UserVo userVo) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            if (mode.equals("create")) {
                userService.createRoleBinding(userVo);
            } else if (mode.equals("update")) {
                userService.updateRoleBinding(userVo);
            } else if (mode.equals("delete")) {
                userService.deleteRoleBinding(userVo);
            } else {
                throw new Exception("Invalid action mode!!!");
            }
            
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
