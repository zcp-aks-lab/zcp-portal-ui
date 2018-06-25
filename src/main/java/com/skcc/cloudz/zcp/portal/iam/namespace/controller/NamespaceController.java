package com.skcc.cloudz.zcp.portal.iam.namespace.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
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
    
	static final String RESOURCE_PATH = "/management";
	
    @Autowired
    private NamespaceService namespaceService;
    
    @GetMapping(value = "/namespaces", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String cardNamespace(Model model, @ModelAttribute EnquryNamespaceVO vo) throws Exception {
    	model.addAttribute("namespace", namespaceService.getResourceQuota(vo));
    	model.addAttribute("labels", namespaceService.getResourceLabel());
    	return "content/management/namespace/namespace";
    }
    
    @PostMapping(value = "/namespace/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public Map<String, Object> getNamespace(Model model, @ModelAttribute EnquryNamespaceVO vo) throws Exception {
    	return namespaceService.getResourceQuota(vo);
    }
    
    @PostMapping(value = "/namespace/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public void deleteNamespace(@RequestBody HashMap<String, String> name) throws Exception {
    	log.debug("namespace=" + name.get("namespace"));
    	namespaceService.deleteNamespace(name.get("namespace"));
    }
    
    @PostMapping(value = "/namespace/getNamespaceResource", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public Map<String, Object> getNamespaceResource(@RequestBody EnquryNamespaceVO info) throws Exception {
    	return namespaceService.getNamespaceResource(info.getNamespace());
    }
    
    @PostMapping(value = "/namespace/createAndEdit", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public void createNamespace(@RequestBody HashMap<String, Object> data) throws Exception {
    	namespaceService.createNamespace(data);
    }
    
    @GetMapping(value = "/namespace/{namespace}", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String editNamespace(Model model, @PathVariable("namespace") String namespace , @ModelAttribute EnquryNamespaceVO vo) throws Exception {
    	model.addAttribute("namespace", namespace);
    	return "content/management/namespace/namespace-detail";
    }
    
}

