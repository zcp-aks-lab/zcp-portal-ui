package com.skcc.cloudz.zcp.portal.guide.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = GuideController.RESOURCE_PATH)
public class GuideController {
    
    static final String RESOURCE_PATH = "/guide";
    
    @GetMapping(value = "/initialize", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String initialize(Model model) throws Exception {
        return "content/guide/initialize";
    }

}
