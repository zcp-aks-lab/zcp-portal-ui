package com.skcc.cloudz.zcp.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = ErrorController.RESOURCE_PATH)
public class ErrorController {
    
    static final String RESOURCE_PATH = "/error";
    
    /**
     * 404 not found error
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/notFound", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String notFound() throws Exception {
        return "common/error/notFound";
    }
    
    /**
     * 500 internal server error
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/serverError", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String serverError() throws Exception {
        return "common/error/serverError";
    }

}
