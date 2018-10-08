package com.skcc.cloudz.zcp.portal.common.contoller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skcc.cloudz.zcp.api.iam.domain.vo.ZcpUserVo;
import com.skcc.cloudz.zcp.common.component.AuthUserComponent;
import com.skcc.cloudz.zcp.common.constants.ClusterRole;
import com.skcc.cloudz.zcp.common.constants.Result;
import com.skcc.cloudz.zcp.common.exception.ZcpPortalException;
import com.skcc.cloudz.zcp.common.security.service.SecurityService;
import com.skcc.cloudz.zcp.portal.common.service.MainService;
import com.skcc.cloudz.zcp.portal.management.user.service.UserService;

@Controller
public class MainController {
    
    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    
    static final String NAMESPACE_TOTAL = "TOTAL";
    
    @Autowired
    private MainService mainService;
    
    @Autowired
    private AuthUserComponent authUserComponent;
    
    @Autowired
    private UserService userService;
    
    @GetMapping(value = {"/main", "/"}, consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String main(@RequestParam(required = false, value = "namespace") String namespace, Model model) throws Exception {
        String clusterRole = SecurityService.getUserDetail().getClusterRole();
        if (clusterRole == null) return "redirect:/guide/initialize";
        
        if (log.isInfoEnabled()) {
            log.info("request namespace : {}, getSelectedNamespace : {}, session namespace : {}", namespace, this.getSelectedNamespace(namespace), authUserComponent.getNamespace());    
        }
        
        String selectedNamespace = this.getSelectedNamespace(namespace);
        if (clusterRole.equals(ClusterRole.MEMBER.getName())) {
            String defaultNamespace = SecurityService.getUserDetail().getDefaultNamespace();
            selectedNamespace = StringUtils.isEmpty(selectedNamespace) ? defaultNamespace : selectedNamespace;
            
            if (StringUtils.isEmpty(selectedNamespace)) {
                ZcpUserVo zcpUserVo = userService.getUser(SecurityService.getUserId());
                
                if (zcpUserVo != null && zcpUserVo.getNamespaces() != null && !zcpUserVo.getNamespaces().isEmpty()) {
                    String firstNamespace = StringUtils.EMPTY;
                    for (String ns : zcpUserVo.getNamespaces()) {
                        firstNamespace = ns; break;
                    }
                    
                    selectedNamespace = firstNamespace;
                } else {
                    return "redirect:/guide/initialize";
                }
            }
        }
        
        model.addAttribute("selectedNamespace", selectedNamespace);
        authUserComponent.setNamespace(selectedNamespace);
        
        return "content/main";
    }
    
    @GetMapping(value = "/main/getNodes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getNodes() throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            resultMap.put("resultCd", Result.SUCCESS.getCd());
            resultMap.put("resultData", mainService.getNodes());
        } catch (Exception e) {
            resultMap.put("resultCd", Result.ERROR.getCd());
            resultMap.put("resultMsg", ZcpPortalException.getExceptionMsg(e));
        }
        
        return resultMap;
    }
    
    @GetMapping(value = "/main/getNodesStatus", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getNodesStatus() throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            resultMap.put("resultCd", Result.SUCCESS.getCd());
            resultMap.put("resultData", mainService.getNodesStatus());
        } catch (Exception e) {
            resultMap.put("resultCd", Result.ERROR.getCd());
            resultMap.put("resultMsg", ZcpPortalException.getExceptionMsg(e));
        }
        
        return resultMap;
    }
    
    @GetMapping(value = "/main/getDeploymentsStatus", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getDeploymentsStatus(@RequestParam(required = false, value = "namespace") String namespace) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            resultMap.put("resultCd", Result.SUCCESS.getCd());
            resultMap.put("resultData", mainService.getDeploymentsStatus(this.getSelectedNamespace(namespace)));
        } catch (Exception e) {
            resultMap.put("resultCd", Result.ERROR.getCd());
            resultMap.put("resultMsg", ZcpPortalException.getExceptionMsg(e));
        }
        
        return resultMap;
    }
    
    @GetMapping(value = "/main/getPodsStatus", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getPodsStatus(@RequestParam(required = false, value = "namespace") String namespace) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            resultMap.put("resultCd", Result.SUCCESS.getCd());
            resultMap.put("resultData", mainService.getPodsStatus(this.getSelectedNamespace(namespace)));
        } catch (Exception e) {
            resultMap.put("resultCd", Result.ERROR.getCd());
            resultMap.put("resultMsg", ZcpPortalException.getExceptionMsg(e));
        }
        
        return resultMap;
    }
    
    @GetMapping(value = "/main/getCpuStatus", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getCpuStatus() throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            resultMap.put("resultCd", Result.SUCCESS.getCd());
            resultMap.put("resultData", mainService.getCpuStatus());
        } catch (Exception e) {
            resultMap.put("resultCd", Result.ERROR.getCd());
            resultMap.put("resultMsg", ZcpPortalException.getExceptionMsg(e));
        }
        
        return resultMap;
    }
    
    @GetMapping(value = "/main/getMemoryStatus", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getMemoryStatus() throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            resultMap.put("resultCd", Result.SUCCESS.getCd());
            resultMap.put("resultData", mainService.getMemoryStatus());
        } catch (Exception e) {
            resultMap.put("resultCd", Result.ERROR.getCd());
            resultMap.put("resultMsg", ZcpPortalException.getExceptionMsg(e));
        }
        
        return resultMap;
    }
    
    @GetMapping(value = "/main/getUsersStatus", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getUsersStatus(@RequestParam(required = false, value = "namespace") String namespace) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            resultMap.put("resultCd", Result.SUCCESS.getCd());
            resultMap.put("resultData", mainService.getUsersStatus(this.getSelectedNamespace(namespace)));
        } catch (Exception e) {
            resultMap.put("resultCd", Result.ERROR.getCd());
            resultMap.put("resultMsg", ZcpPortalException.getExceptionMsg(e));
        }
        
        return resultMap;
    }
    
    @GetMapping(value = "/main/getJweToken", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getJweToken() throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            resultMap.put("resultCd", Result.SUCCESS.getCd());
            resultMap.put("resultData", mainService.getJweToken());
        } catch (Exception e) {
            resultMap.put("resultCd", Result.ERROR.getCd());
            resultMap.put("resultMsg", ZcpPortalException.getExceptionMsg(e));
        }
        
        return resultMap;
    }
    
    public String getSelectedNamespace(String reqNamespace) {
        String namespace = StringUtils.EMPTY;
        
        if (reqNamespace == null) {
            namespace = !StringUtils.isEmpty(authUserComponent.getNamespace()) ? authUserComponent.getNamespace() : StringUtils.EMPTY;
        } else {
            namespace = !reqNamespace.equals(NAMESPACE_TOTAL) ? reqNamespace : StringUtils.EMPTY;
        }

        return namespace;
    }

}
