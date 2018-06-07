package com.skcc.cloudz.zcp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = IamUserController.RESOURCE_PATH)
public class IamUserController {

    private static final Logger log = LoggerFactory.getLogger(IamUserController.class);
    
    static final String RESOURCE_PATH = "/iam/user";
    
    @GetMapping(value = "/user-list", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String userList(Model model) throws Exception {
        return "content/iam/user/user-list";
    }
}
