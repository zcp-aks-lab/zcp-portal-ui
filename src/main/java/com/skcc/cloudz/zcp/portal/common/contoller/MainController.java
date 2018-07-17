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
import com.skcc.cloudz.zcp.portal.common.service.K8sSsoService;
import com.skcc.cloudz.zcp.portal.common.service.MainService;

@Controller
public class MainController {
    
    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    
    @Autowired
    private MainService mainService;
    
    @Autowired
    private K8sSsoService k8sSsoService;
    
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
    
    @GetMapping(value = "/main/getCsrfToken", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getCsrfToken() throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        /* 1) get token */
        String token = k8sSsoService.getCsrfToken();
        log.info("token : {}", token);
        
        HashMap<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("kubeConfig", "");
        reqMap.put("password", "");
        //reqMap.put("token", "1234");
        reqMap.put("token", "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJ6Y3Atc3lzdGVtIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6InpjcC1zeXN0ZW0tYWRtaW4tdG9rZW4tcmptN24iLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC5uYW1lIjoiemNwLXN5c3RlbS1hZG1pbiIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VydmljZS1hY2NvdW50LnVpZCI6ImJlOTI0MmM2LTYyOTctMTFlOC1iNzZhLTM2YjhlMjg5NmNjNCIsInN1YiI6InN5c3RlbTpzZXJ2aWNlYWNjb3VudDp6Y3Atc3lzdGVtOnpjcC1zeXN0ZW0tYWRtaW4ifQ.onxzYoJnX7f8UZ2RNMw_mExBv6Qavs-7KH92sLEJdeRdis24caiAhCJWG3_G73zmSJpij21fxsPKYkp9GQ67HySTZTYZRm-UcwPGIoWiRLO-ahiwaBftLuLykgo-92woPRdA-5NKmNX1NrWXLuhEA4LHIiNJYqXcN2snmUpmEDLztX4-jtJkUypCvsiitz9T-1ozyFSpSWXd_gjs_IabI2KCQM2l3-2dEehaM_-r0OZDI7S9OdmsBSQKQJ_sPkpGU4LyCsur9eHH5yx27FvPgsxX6sZJeG2bIVpNDcbhpn9jv27eaiiTCHfb8kBr8Y95-k2mD136Uy_A2fLZaq4G9WsgIewgZ0Fp0biMpd6Ma4PRxDvOUtIxsd7i8kdO2Ozo7NWQ7B_7HXX4px-w6FPtU6mgqYRV36nD3zz-IC7_YvHZGWfyE_U6xcre_HBbWoy6FCMi8R4z__-pb3EAq_ARKw-_XwQVF062WICjJy6TkoKlCB_w5vB9RfY9fPZR0HVu0t2toh5zQyCMu3x6x900M5tEyxDZNQN0d6dRDSH3TkvsDqJ5mV7xiHCWWIqvqKJvIXo_XwNMKpGhSItCMlGcwx-eUxFhR_ksXXUBz9jGRPkG2kBYzy7s7zMaUeDmsjzbE_6LLyaMwjoNl2CJd6uBsjqEAYG--KFQQ6GHL0CyOew");
        reqMap.put("username", "");
        
        /* 2) login */
        Map<String, Object> loginResult = k8sSsoService.login(token, reqMap);
        
        /* 3) login status */
        resultMap.put("token", token);
        resultMap.put("loginResult", loginResult);
                
        return resultMap;
    }
    
    

}
