package com.skcc.cloudz.zcp.portal.system.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skcc.cloudz.zcp.common.security.service.SecurityService;
import com.skcc.cloudz.zcp.portal.system.domain.dto.MyUserDto;
import com.skcc.cloudz.zcp.portal.system.service.MyService;

@Controller
@RequestMapping(value = MyController.RESOURCE_PATH)
public class MyController {
    
    private static final Logger log = LoggerFactory.getLogger(MyController.class);
    
    static final String RESOURCE_PATH = "/my";
    
    @Autowired
    private SecurityService securityService;
    
    @Autowired
    private MyService myService;
    
    @GetMapping(value = "/my-info", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String myInfo(final Principal principal, Model model) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Principal : {}", principal.getName());
        }
        model.addAttribute("content", myService.getMyInfoByUsername(securityService.getIdToken().getPreferred_username()));
        
        return "content/my/my-info";
    }
    
    @RequestMapping(value = "/updateMyInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> updateMyInfo(@ModelAttribute MyUserDto myUserDto) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        log.debug("myUserDto : {}", myUserDto.toString());
        
        return resultMap;
        
    }
    
    @GetMapping(value = "/my-pwd", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String myPwd(Model model) throws Exception {
        return "content/my/my-pwd";
    }
    
    @GetMapping(value = "/my-cli", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String myCli(Model model) throws Exception {
        return "content/my/my-cli";
    }
    
    

}
