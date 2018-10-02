package com.skcc.cloudz.zcp.configuration.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.skcc.cloudz.zcp.api.iam.domain.vo.ZcpUserVo;
import com.skcc.cloudz.zcp.common.security.service.SecurityService;
import com.skcc.cloudz.zcp.portal.management.user.service.UserService;

public class UserNamespaceInterceptor extends HandlerInterceptorAdapter {
    
    @Autowired
    private UserService userService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        if (modelAndView == null || !modelAndView.hasView()) {
            return;
        }
        
        String clusterRole = SecurityService.getUserDetail().getClusterRole();
        if (!StringUtils.isEmpty(clusterRole)) {
            ZcpUserVo zcpUserVo = userService.getUser(SecurityService.getUserId());
            
            modelAndView.addObject("userNamespaces", zcpUserVo.getNamespaces());
            modelAndView.addObject("userDefaultNamespace", zcpUserVo.getDefaultNamespace());    
        }
    }

}
