package com.skcc.cloudz.zcp.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.skcc.cloudz.zcp.common.security.service.SecurityService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {
    
    @Value("${props.appName}")
    private String appName;
    
    @Autowired
    private SecurityService securityService;
    
    @GetMapping(value = {"/main", "/"}, consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String main(final Principal principal, Model model) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Principal : {}", principal);
        }
        
        model.addAttribute("sUser", securityService.getUserDetails());
        
        return "content/main";
    }

}
