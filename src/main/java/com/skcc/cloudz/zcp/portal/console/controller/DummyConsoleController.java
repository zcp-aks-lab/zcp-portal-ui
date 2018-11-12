package com.skcc.cloudz.zcp.portal.console.controller;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@RequestMapping(value = DummyConsoleController.RESOURCE_PATH)
public class DummyConsoleController {
    static final String RESOURCE_PATH = "/api";
    static final Pattern pattern = Pattern.compile("/");
    
    @GetMapping("**")
    public String initialize(HttpServletRequest req) throws Exception {
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
        String path = builder.build().getPath();
        String redirect = path.substring("/api/".length()).replace('/', '.') + ".json";

        System.out.println("path     = " + path);
        System.out.println("redirect = " + redirect);

        return "redirect:/dummy/" + redirect;
    }
}
