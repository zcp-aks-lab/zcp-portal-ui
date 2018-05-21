package com.skcc.cloudz.zcp.configuration.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AddOnServiceMetaDataInterceptor extends HandlerInterceptorAdapter {
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView == null || !modelAndView.hasView()) {
            return;
        }
        
        // TODO: get add on service meta data
        log.debug("get add on service meta data");
        
        modelAndView.addObject("addOnServieMetaData", "data");
    }

}
