package com.skcc.cloudz.zcp.portal.console.controller;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/*
 * https://stackoverflow.com/a/38458914
 */
@Controller
@RequestMapping(value = DummyConsoleController.RESOURCE_PATH, headers = "Connection!=Upgrade")
public class DummyConsoleController {
    static final String RESOURCE_PATH = "/api";
    static final Pattern pattern = Pattern.compile("/");
    
    @GetMapping(value = {"/cluster/**"})
    public String dummy(HttpServletRequest req) throws Exception {
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
        String path = builder.build().getPath();
        String redirect = path.substring("/api/".length()).replace('/', '.') + ".json";

        System.out.println("path     = " + path);
        System.out.println("redirect = " + redirect);

        return "redirect:/dummy/" + redirect;
    }

    @GetMapping(value = {"/pod/list"})
    public String redirect(HttpServletRequest req) throws Exception {
        return "redirect:/iam/rbac/cloudzcp-admin/namespace/zcp-system/Pod";
    }
}
