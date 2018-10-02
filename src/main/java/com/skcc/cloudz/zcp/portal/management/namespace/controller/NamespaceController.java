package com.skcc.cloudz.zcp.portal.management.namespace.controller;

import java.util.HashMap;
import java.util.List;
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

import com.skcc.cloudz.zcp.api.iam.domain.vo.ZcpUserVo;
import com.skcc.cloudz.zcp.common.constants.Result;
import com.skcc.cloudz.zcp.common.exception.ZcpPortalException;
import com.skcc.cloudz.zcp.common.security.service.SecurityService;
import com.skcc.cloudz.zcp.portal.management.namespace.service.NamespaceService;
import com.skcc.cloudz.zcp.portal.management.namespace.vo.EnquryNamespaceVO;
import com.skcc.cloudz.zcp.portal.management.user.service.UserService;
import com.skcc.cloudz.zcp.portal.management.user.vo.UserVo;


@Controller
@RequestMapping(value = NamespaceController.RESOURCE_PATH)
public class NamespaceController {
	private static final Logger log = LoggerFactory.getLogger(NamespaceController.class);
    
	static final String RESOURCE_PATH = "/management";
	
	@Autowired
    private SecurityService securityService;
	
    @Autowired
    private NamespaceService namespaceService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping(value = "/namespaces", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String cardNamespace(Model model, @ModelAttribute EnquryNamespaceVO vo) throws Exception {
    	String clusterRole = securityService.getUserDetail().getClusterRole();
    	model.addAttribute("namespace", namespaceService.getResourceQuota(vo));
    	model.addAttribute("labels", namespaceService.getResourceLabel());
    	model.addAttribute("view", clusterRole.equals("cluster-admin") ? true : false);
    	
    	return "content/management/namespace/namespace";
    }
    
    @PostMapping(value = "/namespace/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public Object getNamespace(Model model, @RequestBody EnquryNamespaceVO vo) throws Exception {
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
        String clusterRole = securityService.getUserDetail().getClusterRole();
    	model.addAttribute("namespace", namespace);
    	model.addAttribute("view", clusterRole.equals("cluster-admin") ? true : false);
    	return "content/management/namespace/namespace-detail";
    }
    
    
    @PostMapping(value = "/namespace/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public Map<String, Object> getUsers(@RequestBody HashMap<String, String> data) throws Exception {
    	return namespaceService.getUsers(data);
    }
    
    @PostMapping(value = "/namespace/saveRole", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public void addUserInNamespace(@RequestBody HashMap<String, Object> data) throws Exception {
    	namespaceService.addUserInNamespace(data);
    }
    
    @PostMapping(value = "/namespace/modifyRole", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public void addNamespaceRole(@RequestBody HashMap<String, Object> data) throws Exception {
    	namespaceService.modifyNamespaceRole(data);
    }
    
    @PostMapping(value = "/namespace/delRole", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public void delNamespaceRole(@RequestBody HashMap<String, Object> data) throws Exception {
    	namespaceService.delNamespaceRole(data);
    }
    
    @PostMapping(value = "/namespace/addLabel", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public void addLableOfNamespace(@RequestBody HashMap<String, Object> data) throws Exception {
    	namespaceService.addLableOfNamespace(data);
    }
    
    @PostMapping(value = "/namespace/labels", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public Map<String, Object> getLableOfNamespace(@RequestBody HashMap<String, Object> data) throws Exception {
    	return namespaceService.getLableOfNamespace(data);
    }
    
    @PostMapping(value = "/namespace/delLabel", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public void delLableOfNamespace(@RequestBody HashMap<String, Object> data) throws Exception {
    	namespaceService.delLableOfNamespace(data);
    }
    
    @PostMapping(value = "/namespace/authority", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public List<String> getClusterRole() throws Exception {
    	return namespaceService.getNamespaceRoles();
    }
    
    @GetMapping(value = "/namespace/create", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String goNamespacePage() throws Exception {
    	return "content/management/namespace/namespace-add";
    }
    
    @PostMapping(value = "/namespace/createUser", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public Map<String, Object> createUser(@RequestBody HashMap<String, Object> data) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
        	UserVo userVo = new UserVo();
        	//userVo.setId((String)data.get("username"));
        	userVo.setUsername((String)data.get("username"));
        	userVo.setEmail((String)data.get("email"));
        	userVo.setFirstName((String)data.get("firstName"));
        	userVo.setClusterRole((String)data.get("clusterRole"));
        	userVo.setPassword((String)data.get("password"));
        	userVo.setTemporary(false);
        	//1.사용자 추가
        	userService.setUser(userVo);
        	
        	//2.암호 설정
        	List<ZcpUserVo> list_user = userService.getUsers((String)data.get("username"));
        	userVo.setId(list_user.get(0).getId());
        	userService.resetPassword(userVo);
        	
        	//3.cluster 설정
        	userService.updateClusterRoleBinding(userVo);
        	
        	//namespace 권한 설정
        	data.put("clusterRole", data.get("namespaceRole"));
            namespaceService.addUserInNamespace(data);            
            resultMap.put("resultCd", Result.SUCCESS.getCd());
        } catch (Exception e) {
            resultMap.put("resultCd", Result.ERROR.getCd());
            resultMap.put("resultMsg", ZcpPortalException.getExceptionMsg(e));
        }
        
        return resultMap;
    }
    
    @PostMapping(value = "/namespace/clusterRoles", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody 
    public List<String> getClusterRoles() throws Exception {
        return userService.getClusterRoles("cluster");
    }

    @GetMapping(value = "/popSecretAdd", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
	public String popSecretAdd(Model model) throws Exception {
		return "content/management/namespace/pop/popSecretAdd";
	}
    
}

