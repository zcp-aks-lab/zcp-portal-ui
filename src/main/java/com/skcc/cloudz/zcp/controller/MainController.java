package com.skcc.cloudz.zcp.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.skcc.cloudz.zcp.common.security.service.SecurityService;

@Controller
public class MainController {
    
    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    
    @Value("${props.appName}")
    private String appName;
    
    @Autowired
    private SecurityService securityService;
    
    @GetMapping(value = {"/main", "/"}, consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String main(final Principal principal, Model model) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Principal : {}", principal);
        }
        
        //model.addAttribute("sUser", securityService.getUserDetails());
        
        log.debug("getUserDetails : {}", securityService.getUserDetails());
        log.debug("getIdToken : {}", securityService.getIdToken());
        log.debug("getAccessToken : {}", securityService.getAccessToken());
        
        //return "content/main";
        return "redirect:/my/my-info";
    }

}
