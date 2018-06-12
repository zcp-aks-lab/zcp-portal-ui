package com.skcc.cloudz.zcp.portal.system.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skcc.cloudz.zcp.portal.system.domain.dto.MyUserDto;
import com.skcc.cloudz.zcp.portal.system.service.MyService;

@Controller
@RequestMapping(value = MyController.RESOURCE_PATH)
public class MyController {
    
    private static final Logger log = LoggerFactory.getLogger(MyController.class);
    
    static final String RESOURCE_PATH = "/my";
    
    @Autowired
    private MyService myService;
    
    @GetMapping(value = "/my-info", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String myInfo(Model model) throws Exception {
        model.addAttribute("content", myService.getMyInfo());
        
        return "content/system/my/my-info";
    }
    
    @PostMapping(value = "/updatedMyInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public void updatedMyInfo(@ModelAttribute MyUserDto myUserDto) throws Exception {
        myService.updateUser(myUserDto);
    }
    
    @PostMapping(value = "/updatedPassword", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public void updatedPassword(@ModelAttribute MyUserDto myUserDto) throws Exception {
        myService.updatePassword(myUserDto);
    }
    
    @GetMapping(value = "/my-pwd", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String myPwd(Model model) throws Exception {
        return "content/system/my/my-pwd";
    }
    
    @GetMapping(value = "/my-cli", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String myCli(Model model) throws Exception {
        return "content/system/my/my-cli";
    }
    

}
