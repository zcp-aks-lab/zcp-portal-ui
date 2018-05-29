package com.skcc.cloudz.zcp.configuration.web.interceptor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skcc.cloudz.zcp.domain.vo.AddOnServiceMataVo;

public class AddOnServiceMetaDataInterceptor extends HandlerInterceptorAdapter {
    
    private static final Logger log = LoggerFactory.getLogger(AddOnServiceMetaDataInterceptor.class);
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView == null || !modelAndView.hasView()) {
            return;
        }
        
        String requestURI = request.getRequestURI().substring(request.getContextPath().length());
        if (log.isDebugEnabled()) {
            log.debug("requestURI : {}", requestURI);    
        }
        
        modelAndView.addObject("addOnServiceMataData", this.getAddOnServiceMetaData());
    }
    
    public List<AddOnServiceMataVo> getAddOnServiceMetaData () {
        List<AddOnServiceMataVo> list = new ArrayList<AddOnServiceMataVo>();
        
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            InputStream inputStream = AddOnServiceMetaDataInterceptor.class.getClassLoader().getResourceAsStream("addOnServiceMetaData.json");
            if (inputStream != null) {
                list = mapper.readValue(inputStream, new TypeReference<List<AddOnServiceMataVo>>(){});
                
                log.debug("list : {}", list.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return list;
    }

}
