package com.skcc.cloudz.zcp.portal.console.controller;

import java.security.Principal;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

/*
 * https://stackoverflow.com/a/38458914
 */
@Controller
@RequestMapping(value = DummyConsoleController.RESOURCE_PATH, headers = "Connection!=Upgrade")
public class DummyConsoleController {
    static final String RESOURCE_PATH = "/api";
    static final Pattern pattern = Pattern.compile("/");
    
    private String redirect = "/iam/rbac/{username}/namespace/{ns}/{kind}";
    
    @GetMapping(value = {"/cluster/**", "cluster/list"})
    public String dummy(HttpServletRequest req) throws Exception {
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
        String path = builder.build().getPath();
        String redirect = path.substring("/api/".length()).replace('/', '.') + ".json";

        System.out.println("path     = " + path);
        System.out.println("redirect = " + redirect);

        return "redirect:/dummy/" + redirect;
    }

    @GetMapping(value = {"/cluster/{cs}/namespace/list"})
    public String namespaceList(Principal principal, @RequestParam Map<String, String> params) throws Exception {
        params.put("username", principal.getName());

    	String url = UriComponentsBuilder.fromPath("/iam/rbac/{username}/namespace")
            .buildAndExpand(params)
            .toString();

        return "redirect:" + url;
    }

    @GetMapping(value = {"/{kind}/list"})
    public String redirect(Principal principal,
            @PathVariable String kind,
            @RequestParam Map<String, String> params) throws Exception {
        params.put("username", principal.getName());
        params.put("kind", kind);

    	String url = UriComponentsBuilder.fromPath(redirect)
            .buildAndExpand(params)
            .toString();


        return "redirect:" + url;
    }
}
