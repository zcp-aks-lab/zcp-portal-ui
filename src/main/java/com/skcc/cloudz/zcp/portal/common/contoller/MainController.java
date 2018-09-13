package com.skcc.cloudz.zcp.portal.common.contoller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skcc.cloudz.zcp.api.iam.domain.vo.ZcpNodeVo;
import com.skcc.cloudz.zcp.common.constants.AccessRole;
import com.skcc.cloudz.zcp.common.security.service.SecurityService;
import com.skcc.cloudz.zcp.portal.common.service.MainService;

@Controller
public class MainController {
    
    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    
    @Value("${props.dashboard.baseUrl}")
    private String dashboardBaseUrl;
    
    @Autowired
    private MainService mainService;
    
    @GetMapping(value = {"/main", "/"}, consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String main(@RequestParam(required = false, value = "namespace") String namespace, Model model) throws Exception {
        
        String accessRole = SecurityService.getUserDetail().getAccessRole();
        if (accessRole == null) {
            return "redirect:/guide/initialize";
        } 
        
        if (accessRole.equals(AccessRole.CLUSTER_ADMIN.getName())) {
            model.addAttribute("selectedNamespace", namespace);
        } else {
            String defaultNamespace = SecurityService.getUserDetail().getDefaultNamespace();
            
            model.addAttribute("selectedNamespace", StringUtils.isEmpty(namespace) ? defaultNamespace : namespace);    
        }
        
        return "content/main";
    }
    
    @GetMapping(value = "/main/getNodes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<ZcpNodeVo> getNodes() throws Exception {
        return mainService.getNodes();
    }
    
    @GetMapping(value = "/main/getNodesStatus", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getNodesStatus() throws Exception {
        return mainService.getNodesStatus();
    }
    
    @GetMapping(value = "/main/getDeploymentsStatus", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getDeploymentsStatus(@RequestParam(required = false, value = "namespace") String namespace) throws Exception {
        return mainService.getDeploymentsStatus(namespace);
    }
    
    @GetMapping(value = "/main/getPodsStatus", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getPodsStatus(@RequestParam(required = false, value = "namespace") String namespace) throws Exception {
        return mainService.getPodsStatus(namespace);
    }
    
    @GetMapping(value = "/main/getCpuStatus", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getCpuStatus() throws Exception {
        return mainService.getCpuStatus();
    }
    
    @GetMapping(value = "/main/getMemoryStatus", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getMemoryStatus() throws Exception {
        return mainService.getMemoryStatus();
    }
    
    @GetMapping(value = "/main/getUsersStatus", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getUsersStatus(@RequestParam(required = false, value = "namespace") String namespace) throws Exception {
        return mainService.getUsersStatus(namespace);
    }
    
    @GetMapping(value = "/main/getJweToken", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getJweToken() throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        resultMap.put("result", mainService.getJweToken());
        resultMap.put("dashboardBaseUrl", dashboardBaseUrl);
        
        return resultMap;
    }

}
