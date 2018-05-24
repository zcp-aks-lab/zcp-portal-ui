package com.skcc.cloudz.zcp.controller;

import java.security.Principal;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MyController {
    
    @GetMapping(value = "/my/my-info", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String myInfo(final Principal principal, Model model) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Principal : {}", principal);
        }
        
        return "content/my/my-info";
    }

}
