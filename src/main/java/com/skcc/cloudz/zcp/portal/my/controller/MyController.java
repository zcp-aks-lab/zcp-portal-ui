package com.skcc.cloudz.zcp.portal.my.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skcc.cloudz.zcp.common.component.AuthUserComponent;
import com.skcc.cloudz.zcp.common.constants.Result;
import com.skcc.cloudz.zcp.common.exception.ZcpPortalException;
import com.skcc.cloudz.zcp.common.security.service.SecurityService;
import com.skcc.cloudz.zcp.common.util.FileUtil;
import com.skcc.cloudz.zcp.portal.my.service.MyService;
import com.skcc.cloudz.zcp.portal.my.vo.MyUserVo;

@Controller
@RequestMapping(value = MyController.RESOURCE_PATH)
public class MyController {
    
    private static final Logger log = LoggerFactory.getLogger(MyController.class);
    
    static final String RESOURCE_PATH = "/my";
    
    @Value("${props.cliDownloadUrl}")
    private String cliDownloadUrl;
    
    @Autowired
    private MyService myService;
    
    @Autowired
    private AuthUserComponent authUserComponent;
    
    @GetMapping(value = "/profile", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String myInfo(Model model) throws Exception {
        model.addAttribute("zcpUser", myService.getMyUser());
        return "content/my/my-profile";
    }
    
    @GetMapping(value = "/pwd", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String myPwd(Model model) throws Exception {
        model.addAttribute("zcpUser", myService.getMyUser());
        return "content/my/my-pwd";
    }
    
    @GetMapping(value = "/cli", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String myCli(Model model) throws Exception {
        model.addAttribute("cliDownloadUrl", cliDownloadUrl);
        return "content/my/my-cli";
    }
    
    @PostMapping(value = "/updateMyProfile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public Map<String, Object> updatedMyProfile(@RequestBody MyUserVo myUserVo) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            myService.updateUser(myUserVo);
            authUserComponent.setFirstName(myUserVo.getFirstName());
            
            resultMap.put("resultCd", Result.SUCCESS.getCd());
        } catch (Exception e) {
            resultMap.put("resultCd", Result.ERROR.getCd());
            resultMap.put("resultMsg", ZcpPortalException.getExceptionMsg(e));
        }
        
        return resultMap;
    }
    
    @PostMapping(value = "/updatedPassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public Map<String, Object> updatedPassword(@RequestBody MyUserVo myUserVo) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            myService.updatePassword(myUserVo);    
            
            resultMap.put("resultCd", Result.SUCCESS.getCd());
        } catch (Exception e) {
            resultMap.put("resultCd", Result.ERROR.getCd());
            resultMap.put("resultMsg", ZcpPortalException.getExceptionMsg(e));
        }
        
        return resultMap;
    }
    
    @GetMapping(value = "/getZcpKubeConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getZcpKubeConfig() throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            resultMap.put("resultCd", Result.SUCCESS.getCd());
            resultMap.put("resultData", myService.getKubeConfig());
        } catch (Exception e) {
            resultMap.put("resultCd", Result.ERROR.getCd());
            resultMap.put("resultMsg", ZcpPortalException.getExceptionMsg(e));
        }
        
        return resultMap;
    }
    
    @PostMapping(value = "/cliDownload")
    public void cliDownload(HttpServletResponse response, @RequestParam("cli") String cli) {
        try {
            String fileName = "kube.conf";
            File file = new File(fileName);
            
            FileUtil.fileWrite(file, cli);
            FileUtil.fileDownload(response, file);
            FileUtil.fileDelete(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @PostMapping(value = "/otpPassword/{mode}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public Map<String, Object> otpPassword(@PathVariable("mode") String mode) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        if (log.isInfoEnabled()) {
            log.info("mode : {}", mode);
        }
        
        try {
            if (mode.equals("update")) {
                myService.updateOtpPassword();
            } else if (mode.equals("delete")) {
                myService.deleteOtpPassword();
            }
            
            resultMap.put("resultCd", Result.SUCCESS.getCd());
        } catch (Exception e) {
            resultMap.put("resultCd", Result.ERROR.getCd());
            resultMap.put("resultMsg", ZcpPortalException.getExceptionMsg(e));
        }
        
        return resultMap;
    }

}
