package com.skcc.cloudz.zcp.portal.common.contoller;

import java.util.HashMap;
import java.util.List;
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

import com.skcc.cloudz.zcp.api.iam.domain.vo.ZcpNodeVo;
import com.skcc.cloudz.zcp.common.constants.AccessRole;
import com.skcc.cloudz.zcp.common.security.service.SecurityService;
import com.skcc.cloudz.zcp.portal.common.service.MainService;

@Controller
public class MainController {
    
    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    
    @Autowired
    private MainService mainService;
    
    @GetMapping(value = {"/main", "/"}, consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String main(@RequestParam(required = false, value = "namespace") String namespace, Model model) throws Exception {
        
        String accessRole = SecurityService.getUserDetail().getAccessRole();
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
    
    @GetMapping(value = "/main/getChartsData", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getChartsData(@RequestParam(required = false, value = "namespace") String namespace) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        resultMap.put("nodesStatus", mainService.getNodesStatus());
        resultMap.put("deploymentsStatus", mainService.getDeploymentsStatus(namespace));
        resultMap.put("podsStatus", mainService.getPodsStatus(namespace));
        resultMap.put("cpuStatus", mainService.getCpuStatus());
        resultMap.put("memoryStatus", mainService.getMemoryStatus());
        resultMap.put("usersStatus", mainService.getUsersStatus(namespace));
                
        return resultMap;
    }

}
