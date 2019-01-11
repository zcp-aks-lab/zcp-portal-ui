package com.skcc.cloudz.zcp.portal.management.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skcc.cloudz.zcp.common.constants.Result;
import com.skcc.cloudz.zcp.common.domain.vo.CommonVo;
import com.skcc.cloudz.zcp.common.exception.ZcpPortalException;
import com.skcc.cloudz.zcp.portal.management.user.service.UserService;
import com.skcc.cloudz.zcp.portal.management.user.vo.UserVo;

@Controller
@RequestMapping(value = "/management")
public class UserController {
    
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    
    static final String CLUSTER_ROLES_TYPE_CLUSTER = "cluster";
    static final String CLUSTER_ROLES_TYPE_NAMESPACE = "namespace";
    
    @Autowired
    private UserService userService;
    
    @GetMapping(value = "/users", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String userList(@ModelAttribute CommonVo commonVo) throws Exception {
        return "content/management/user/user-list";
    }
    
    @GetMapping(value = "/user/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String createPage(@PathVariable("id") String id, @ModelAttribute CommonVo commonVo, Model model) throws Exception {
        if (log.isInfoEnabled()) {
            log.info("id : {}", id);
            log.info("current : {}", commonVo.getCurrent());
            log.info("keyword : {}", commonVo.getKeyword());
        }
        
        model.addAttribute("clusterRoles", userService.getClusterRoles(CLUSTER_ROLES_TYPE_CLUSTER));
        
        if (id.equals("create")) {
            return "content/management/user/user-create";    
        } else {
            model.addAttribute("zcpUser", userService.getUser(id));
            model.addAttribute("namespaceRoles", userService.getClusterRoles(CLUSTER_ROLES_TYPE_NAMESPACE));
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
            resultMap.put("resultCd", Result.SUCCESS.getCd());
            resultMap.put("resultData", userService.getUsers(userVo.getKeyword()));
        } catch (Exception e) {
            resultMap.put("resultCd", Result.ERROR.getCd());
            resultMap.put("resultMsg", ZcpPortalException.getExceptionMsg(e));
        }
        
        return resultMap;
    }
    
    @PostMapping(value = "/user/createUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public Map<String, Object> createUser(@RequestBody UserVo userVo) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            userService.setUser(userVo);
            
            resultMap.put("resultCd", Result.SUCCESS.getCd());
        } catch (Exception e) {
            resultMap.put("resultCd", Result.ERROR.getCd());
            resultMap.put("resultMsg", ZcpPortalException.getExceptionMsg(e));
        }
        
        return resultMap;
    }
    
    @PostMapping(value = "/user/updateUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public Map<String, Object> updateUser(@RequestBody UserVo userVo) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            userService.updateUser(userVo);
            
            resultMap.put("resultCd", Result.SUCCESS.getCd());
        } catch (Exception e) {
            resultMap.put("resultCd", Result.ERROR.getCd());
            resultMap.put("resultMsg", ZcpPortalException.getExceptionMsg(e));
        }
        
        return resultMap;
    }
    
    @PostMapping(value = "/user/deleteUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public Map<String, Object> deleteUser(@RequestBody UserVo userVo) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            userService.deleteUser(userVo.getId());
            
            resultMap.put("resultCd", Result.SUCCESS.getCd());
        } catch (Exception e) {
            resultMap.put("resultCd", Result.ERROR.getCd());
            resultMap.put("resultMsg", ZcpPortalException.getExceptionMsg(e));
        }
        
        return resultMap;
    }
    
    @PostMapping(value = "/user/resetPassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public Map<String, Object> resetPassword(@RequestBody UserVo userVo) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            userService.resetPassword(userVo);
            
            resultMap.put("resultCd", Result.SUCCESS.getCd());
        } catch (Exception e) {
            resultMap.put("resultCd", Result.ERROR.getCd());
            resultMap.put("resultMsg", ZcpPortalException.getExceptionMsg(e));
        }
        
        return resultMap;
    }
    
    @PostMapping(value = "/user/resetCredentials", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public Map<String, Object> resetCredentials(@RequestBody UserVo userVo) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            userService.resetCredentials(userVo);
            
            resultMap.put("resultCd", Result.SUCCESS.getCd());
        } catch (Exception e) {
            resultMap.put("resultCd", Result.ERROR.getCd());
            resultMap.put("resultMsg", ZcpPortalException.getExceptionMsg(e));
        }
        
        return resultMap;
    }
    
    @PostMapping(value = "/user/updateClusterRoleBinding", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public Map<String, Object> updateClusterRoleBinding(@RequestBody UserVo userVo) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            userService.updateClusterRoleBinding(userVo);
            
            resultMap.put("resultCd", Result.SUCCESS.getCd());
        } catch (Exception e) {
            resultMap.put("resultCd", Result.ERROR.getCd());
            resultMap.put("resultMsg", ZcpPortalException.getExceptionMsg(e));
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
            }
            
            resultMap.put("resultCd", Result.SUCCESS.getCd());
        } catch (Exception e) {
            resultMap.put("resultCd", Result.ERROR.getCd());
            resultMap.put("resultMsg", ZcpPortalException.getExceptionMsg(e));
        }
        
        return resultMap;
    }
    
    @PostMapping(value = "/user/otpPassword/{mode}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public Map<String, Object> otpPassword(@PathVariable("mode") String mode, @RequestBody UserVo userVo) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            if (mode.equals("update")) {
                userService.updateOtpPassword(userVo.getId());
            } else if (mode.equals("delete")) {
                userService.deleteOtpPassword(userVo.getId());
            }
            
            resultMap.put("resultCd", Result.SUCCESS.getCd());
        } catch (Exception e) {
            resultMap.put("resultCd", Result.ERROR.getCd());
            resultMap.put("resultMsg", ZcpPortalException.getExceptionMsg(e));
        }
        
        return resultMap;
    }
    
    @PostMapping(value = "/user/{id}/attribute", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public Map<String, Object> updateAttribute(@PathVariable("id") String id, @RequestBody UserVo.Attribute attributeVo) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            userService.updateAttributes(id, attributeVo.getKey(), attributeVo.getValue());
            
            resultMap.put("resultCd", Result.SUCCESS.getCd());
        } catch (Exception e) {
            resultMap.put("resultCd", Result.ERROR.getCd());
            resultMap.put("resultMsg", ZcpPortalException.getExceptionMsg(e));
        }
        
        return resultMap;
    }

}
