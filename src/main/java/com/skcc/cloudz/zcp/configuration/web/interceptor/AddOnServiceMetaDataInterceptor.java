package com.skcc.cloudz.zcp.configuration.web.interceptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skcc.cloudz.zcp.common.component.AuthUserComponent;
import com.skcc.cloudz.zcp.common.constants.ClusterRole;
import com.skcc.cloudz.zcp.common.constants.ZcpEnviroment;
import com.skcc.cloudz.zcp.common.domain.vo.AddOnServiceMataSubVo;
import com.skcc.cloudz.zcp.common.domain.vo.AddOnServiceMataVo;
import com.skcc.cloudz.zcp.common.security.service.SecurityService;
import com.skcc.cloudz.zcp.common.util.CommonUtil;
import com.skcc.cloudz.zcp.portal.management.user.service.UserService;

public class AddOnServiceMetaDataInterceptor extends HandlerInterceptorAdapter {
    
    private static final Logger log = LoggerFactory.getLogger(AddOnServiceMetaDataInterceptor.class);
    
    @Value("${props.addOnService.filePath}")
    private String addOnServiceFilePath;
    
    @Value("${props.addOnService.fileName}")
    private String addOnServiceFileName;
    
    @Autowired
    private Environment environment;
    
    @Autowired
    private SecurityService securityService;
    
    @Autowired
    private AuthUserComponent authUserComponent;
    
    @Autowired
    private UserService userService;
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView == null || !modelAndView.hasView()) {
            return;
        }
        
        String requestURI = request.getRequestURI().substring(request.getContextPath().length());
        if (log.isInfoEnabled()) {
            log.info("===> requestURI : {}", requestURI);    
        }
        
        authUserComponent.setUserId(securityService.getUserDetails().getUserId());
        authUserComponent.setFirstName(securityService.getUserDetails().getFirstName());
        
        modelAndView.addObject("activePathInfo", this.getAddOnServiceActivePathInfo(requestURI));
        modelAndView.addObject("addOnServiceMetaList", this.getAddOnServiceMetaData());
    }
    
    public List<AddOnServiceMataVo> getAddOnServiceMetaData () {
        List<AddOnServiceMataVo> AddOnServiceMataVoList = new ArrayList<AddOnServiceMataVo>();
        
        try {
            String roleType = this.getUserRoleType();
            log.info("===> roleType : {}", roleType);
            
            if (authUserComponent.getAddOnServiceMetaData(roleType) == null) {
                List<AddOnServiceMataVo> addOnServiceMataList = this.getAddOnServiceMetaDataFileLoad();
                
                for (AddOnServiceMataVo a : addOnServiceMataList) {
                    List<String> roles = null;
                    if (roleType.equals(ClusterRole.CLUSTER_ADMIN.getName()) || roleType.equals(ClusterRole.MEMBER.getName())) {
                        roles = a.getRole().getClusterRoles();
                    } else {
                        roles = a.getRole().getNamespaceRoles();
                    }
                    
                    for (String role : roles) {
                        if (roleType.equals(role) && a.isEnable()) {
                            AddOnServiceMataVoList.add(this.getAddOnServiceMetaDataSub(a));        
                        }
                    }
                }
                Collections.sort(AddOnServiceMataVoList);
                
                authUserComponent.putAddOnServiceMetaData(roleType, AddOnServiceMataVoList);
            } else {
                AddOnServiceMataVoList = (List<AddOnServiceMataVo>) authUserComponent.getAddOnServiceMetaData(roleType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return AddOnServiceMataVoList;
    }
    
    public String getUserRoleType() throws Exception {
        String type = StringUtils.EMPTY;
        
        String clusterRole = securityService.getUserDetails().getClusterRole();
        log.info("===> clusterRole : {}", clusterRole);
        
        if (clusterRole.equals(ClusterRole.CLUSTER_ADMIN.getName())) {
            type = ClusterRole.CLUSTER_ADMIN.getName();
        } else if (clusterRole.equals(ClusterRole.MEMBER.getName())) {
            String namespace = authUserComponent.getNamespace();
            String id = securityService.getUserDetails().getUserId();
            
            if (StringUtils.isEmpty(namespace)) {
                type = ClusterRole.MEMBER.getName();
            } else {
                type = userService.getNamespaceRole(namespace, id);
            }
        }
        
        return type;
    }
    
    public List<AddOnServiceMataVo> getAddOnServiceMetaDataFileLoad() {
        List<AddOnServiceMataVo> addOnServiceMataList = null;
        InputStream inputStream = null;
        
        try {
            String profile = CommonUtil.getInstance().getProfile(environment);
            if (profile.equals(ZcpEnviroment.LOCAL.getName()) ||  profile.equals(ZcpEnviroment.DEV.getName())) {
                inputStream = AddOnServiceMetaDataInterceptor.class.getClassLoader().getResourceAsStream("addOnServiceMetaData.json");
            } else {
                File file = new File(addOnServiceFilePath + addOnServiceFileName);
                inputStream = new FileInputStream(file);
            }
            
            addOnServiceMataList = new ObjectMapper().readValue(inputStream, new TypeReference<List<AddOnServiceMataVo>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return addOnServiceMataList;
    }
    
    public AddOnServiceMataVo getAddOnServiceMetaDataSub(AddOnServiceMataVo addOnServiceMataVo) {
        if (addOnServiceMataVo.getSub() == null) return addOnServiceMataVo;
        
        List<AddOnServiceMataSubVo> addOnServiceMataSubVoList = new ArrayList<AddOnServiceMataSubVo>();
        for (AddOnServiceMataSubVo addOnServiceMataSubVo : addOnServiceMataVo.getSub()) {
            
            if (addOnServiceMataSubVo.isEnable()) {
                addOnServiceMataSubVoList.add(addOnServiceMataSubVo);
            }
        }
        
        addOnServiceMataVo.setSub(addOnServiceMataSubVoList);
        Collections.sort(addOnServiceMataVo.getSub());
        
        return addOnServiceMataVo;
    }
    
    public Map<String, Object> getAddOnServiceActivePathInfo(String uri) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        StringTokenizer st = new StringTokenizer(uri, "/");
        String uris[] = new String[st.countTokens()];
        
        int idx = 0;
        while (st.hasMoreTokens()) {
            uris[idx++] = st.nextToken();
        }
        
        String firstPath = StringUtils.EMPTY;
        if (uris.length > 1) {
            firstPath = "/" + uris[0];
        }
        
        resultMap.put("firstPath", firstPath);
        resultMap.put("fullPath", uri);
        
        return resultMap;
    }

}
