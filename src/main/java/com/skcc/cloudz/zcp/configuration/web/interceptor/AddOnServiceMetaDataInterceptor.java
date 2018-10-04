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
import com.skcc.cloudz.zcp.api.iam.domain.vo.ApiResponseVo;
import com.skcc.cloudz.zcp.api.iam.service.IamApiService;
import com.skcc.cloudz.zcp.common.component.AuthUserComponent;
import com.skcc.cloudz.zcp.common.constants.ApiResult;
import com.skcc.cloudz.zcp.common.constants.ClusterRole;
import com.skcc.cloudz.zcp.common.constants.ZcpEnviroment;
import com.skcc.cloudz.zcp.common.domain.vo.AddOnServiceMataSubVo;
import com.skcc.cloudz.zcp.common.domain.vo.AddOnServiceMataVo;
import com.skcc.cloudz.zcp.common.exception.ZcpPortalException;
import com.skcc.cloudz.zcp.common.security.service.SecurityService;
import com.skcc.cloudz.zcp.common.util.CommonUtil;

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
    private IamApiService iamApiService;
    
    @Autowired
    private AuthUserComponent authUserComponent;
    
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
            
            if (authUserComponent.getAddOnServiceMetaData(roleType) == null) {
                List<AddOnServiceMataVo> addOnServiceMataList = this.getAddOnServiceMetaDataFileLoad();
                
                for (AddOnServiceMataVo addOnServiceMataVo : addOnServiceMataList) {
                    List<String> roles = roleType.equals(ClusterRole.CLUSTER_ADMIN.getName()) ? addOnServiceMataVo.getRole().getClusterRoles() : addOnServiceMataVo.getRole().getNamespaceRoles();
                    
                    for (String role : roles) {
                        if (roleType.equals(role) && addOnServiceMataVo.isEnable()) {
                            AddOnServiceMataVoList.add(this.getAddOnServiceMetaDataSub(addOnServiceMataVo));        
                        }
                    }
                }
                
                authUserComponent.putAddOnServiceMetaData(roleType, AddOnServiceMataVoList);
            } else {
                AddOnServiceMataVoList = (List<AddOnServiceMataVo>) authUserComponent.getAddOnServiceMetaData(roleType);
            }
            
            Collections.sort(AddOnServiceMataVoList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return AddOnServiceMataVoList;
    }
    
    public String getUserRoleType() throws ZcpPortalException {
        String type = StringUtils.EMPTY;
        
        String clusterRole = securityService.getUserDetails().getClusterRole();
        log.info("===> clusterRole : {}", clusterRole);
        
        if (clusterRole.equals(ClusterRole.CLUSTER_ADMIN.getName())) {
            type = ClusterRole.CLUSTER_ADMIN.getName();
        } else if (clusterRole.equals(ClusterRole.MEMBER.getName())) {
            type = this.getNamespaceRole();
        }
        
        return type;
    }
    
    public List<AddOnServiceMataVo> getAddOnServiceMetaDataFileLoad() {
        List<AddOnServiceMataVo> addOnServiceMataList = null;
        InputStream inputStream = null;
        
        try {
            String profile = CommonUtil.getInstance().getProfile(environment);
            log.info("===> profile : {}", profile);
            
            inputStream = AddOnServiceMetaDataInterceptor.class.getClassLoader().getResourceAsStream("addOnServiceMetaData.json");
            
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
    
    @SuppressWarnings("unchecked")
    public String getNamespaceRole() throws ZcpPortalException {
        String id = securityService.getUserDetails().getUserId();
        String namespace = authUserComponent.getNamespace();
        
        ApiResponseVo apiResponseVo = iamApiService.getNamespaceRoleBinding(namespace, id);
        if (!apiResponseVo.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new ZcpPortalException(apiResponseVo);
        }
        
        String namespaceRole = ((HashMap<String, Object>) apiResponseVo.getData().get("roleRef")).get("name").toString();
        log.info("===> namespaceRole : {}", namespaceRole);
        
        return getChangeNamespace(namespaceRole);
    }
    
    public String getChangeNamespace(String namespace) {
        String ret = StringUtils.EMPTY;
        
        if (namespace.equals("edit")) {
            ret = "deploy-manager";
        } else if (namespace.equals("view")) {
            ret = "developer";
        } else {
            ret = "admin";
        }
        
        return ret;
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
