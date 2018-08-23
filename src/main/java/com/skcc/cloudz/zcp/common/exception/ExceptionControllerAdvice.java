package com.skcc.cloudz.zcp.common.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionControllerAdvice {
    
    private static final Logger log = LoggerFactory.getLogger(ExceptionControllerAdvice.class);
    
    @ExceptionHandler(value = Exception.class)
    public ModelAndView handlerException(HttpServletRequest req, Exception e) {
        if (log.isInfoEnabled()) {
            log.info(" ***************************************************************************** ");
            log.info("url : {}", req.getRequestURL());
            log.info("ErrMsg : {}", e);
            log.info(" ***************************************************************************** ");
        }
        
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg", ZcpPortalException.getExceptionMsg(e));
        modelAndView.setViewName("common/error/serverError");
        
        return modelAndView;
    }
    
    @ExceptionHandler(value = ZcpPortalException.class)
    public ModelAndView handlerException(HttpServletRequest req, ZcpPortalException e) {
        if (log.isInfoEnabled()) {
            log.info(" ***************************************************************************** ");
            log.info("url : {}", req.getRequestURL());
            log.info("ErrCd : {}", e.getErrCd());
            log.info("ErrMsg : {}", e.getErrMsg());
            log.info(" ***************************************************************************** ");
        }
        
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg", ZcpPortalException.getExceptionMsg(e));
        modelAndView.setViewName("common/error/serverError");
        
        return modelAndView;
    }

}
