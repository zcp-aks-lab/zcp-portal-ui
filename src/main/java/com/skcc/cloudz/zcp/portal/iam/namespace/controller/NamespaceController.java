package com.skcc.cloudz.zcp.portal.iam.namespace.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skcc.cloudz.zcp.portal.iam.namespace.service.NamespaceService;
import com.skcc.cloudz.zcp.portal.iam.namespace.vo.EnquryNamespaceVO;


@Controller
@RequestMapping(value = NamespaceController.RESOURCE_PATH)
public class NamespaceController {
	private static final Logger log = LoggerFactory.getLogger(NamespaceController.class);
    
	static final String RESOURCE_PATH = "/iam";
	
    @Autowired
    private NamespaceService namespaceService;
    
    @GetMapping(value = "/namespace/namespace-list", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String cardNamespace(Model model, @ModelAttribute EnquryNamespaceVO vo) throws Exception {
    	model.addAttribute("namespace", namespaceService.getResourceQuota(vo));
    	model.addAttribute("labels", namespaceService.getResourceLabel());
    	return "content/iam/namespace/namespace";
    }
    
    @PostMapping(value = "/namespace/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public void deleteNamespace(@RequestBody HashMap<String, String> name) throws Exception {
    	log.debug("namespace=" + name);
    	//namespaceService.deleteNamespace(name);
    }
    
}

