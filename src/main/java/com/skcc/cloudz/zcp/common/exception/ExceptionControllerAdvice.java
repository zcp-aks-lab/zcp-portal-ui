package com.skcc.cloudz.zcp.common.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {
    
    @ExceptionHandler(value = Exception.class)
    public ModelAndView handlerException(HttpServletRequest req, Exception e) {
        if (log.isDebugEnabled()) {
            log.debug("Error Message : {}", e.getMessage());
        }
        
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", e.getMessage());
        modelAndView.addObject("url", req.getRequestURL());
        modelAndView.setViewName("common/error/error");
        
        return modelAndView;
    }

    

}
