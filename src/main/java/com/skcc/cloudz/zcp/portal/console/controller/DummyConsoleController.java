package com.skcc.cloudz.zcp.portal.console.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.skcc.cloudz.zcp.api.iam.domain.vo.ZcpUserVo;
import com.skcc.cloudz.zcp.common.constants.ClusterRole;
import com.skcc.cloudz.zcp.common.domain.vo.AddOnServiceMataVo;
import com.skcc.cloudz.zcp.common.domain.vo.AddOnServiceMataVo.Role;
import com.skcc.cloudz.zcp.common.security.vo.OpenIdConnectUserDetailsVo;
import com.skcc.cloudz.zcp.configuration.properties.ConsoleProperties;
import com.skcc.cloudz.zcp.configuration.web.interceptor.AddOnServiceMetaDataInterceptor;
import com.skcc.cloudz.zcp.portal.management.user.service.UserService;
import com.skcc.cloudz.zcp.portal.my.service.MyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

/*
 * https://stackoverflow.com/a/38458914
 */
@Controller
@RequestMapping(value = DummyConsoleController.RESOURCE_PATH, headers = "Connection!=Upgrade")
public class DummyConsoleController {
    static final String RESOURCE_PATH = "/api";

    private String redirect = "/iam/resource/{kind}";
    private Multimap<String, AddOnServiceMataVo> meta = ArrayListMultimap.create();

    @Autowired
    private ConsoleProperties props;
    
    @Autowired
    private MyService myService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddOnServiceMetaDataInterceptor metaService;

    @GetMapping(value = { "/resource/{kind}" })
    public String redirect(Principal principal, @PathVariable String kind,
            @RequestParam MultiValueMap<String, String> params)
            throws Exception {
        String username = principal.getName();
        params.add("username", username);

        Map<String, String> vars = Maps.newHashMap();
        vars.put("username", username);
        vars.put("kind", kind);

        String url = UriComponentsBuilder.fromPath(redirect)
                        .queryParams(params)
                        .buildAndExpand(vars)
                        .toString();

        return "redirect:" + url;
    }

    @RequestMapping(value = { "/resource/{kind}/{name:.+}" }, method = {RequestMethod.GET, RequestMethod.PUT})
    public String redirectResource(Principal principal, @PathVariable String kind, @PathVariable String name,
            @RequestParam MultiValueMap<String, String> params)
            throws Exception {
        String username = principal.getName();
        params.add("username", username);

        Map<String, String> vars = Maps.newHashMap();
        vars.put("username", username);
        vars.put("kind", kind);
        vars.put("name", name);

        String url = UriComponentsBuilder.fromPath(redirect + "/" + name)
                            .queryParams(params)
                            .buildAndExpand(vars)
                            .toString();

        return "redirect:" + url;
    }

    @RequestMapping(value = { "/log" }, method = {RequestMethod.GET})
    public String redirectLogs(Principal principal,
            @RequestParam MultiValueMap<String, String> params)
            throws Exception {
        String username = principal.getName();
        params.add("username", username);

        Map<String, String> vars = Maps.newHashMap();
        vars.put("username", principal.getName());
        vars.put("ns", params.getFirst("ns"));
        vars.put("kind", params.getFirst("kind"));
        vars.put("name", params.getFirst("name"));

        String url = UriComponentsBuilder.fromPath(redirect + "/{name}/logs")
                            .queryParams(params)
                            .buildAndExpand(vars)
                            .toString();

        return "redirect:" + url;
    }

    @ResponseBody
    @GetMapping(value = "/profile")
    public ZcpUserVo profile() throws Exception {
        return myService.getMyUser();
    }

    @ResponseBody
    @GetMapping("/config")
    public Object config(){
        return props;
    }

    @ResponseBody
    @GetMapping(value = "/menu")
    public Collection<AddOnServiceMataVo> menu(@RequestParam String namespace) throws Exception {
        Collection<AddOnServiceMataVo> menu = null;
        OpenIdConnectUserDetailsVo user = (OpenIdConnectUserDetailsVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String role = user.getClusterRole();
        if(isSupserUser(role)){
            menu = meta.get(role);
        } else {
            String id = user.getUserId();
            role = userService.getNamespaceRole(namespace, id);
            menu = meta.get(role);
        }

        // validation
        return menu == null ? meta.get(ClusterRole.MEMBER.toString()) : menu;
    }

    @PostConstruct
    @PutMapping(value = "/menu")
    public void reloadMenus(){
        meta.clear();
        List<AddOnServiceMataVo> raw = metaService.getAddOnServiceMetaDataFileLoad();
        Collections.sort(raw);
        for(AddOnServiceMataVo m : raw) {
            if(!m.isEnable())
                continue;

            Role roles = m.getRole();
            for (String cluster : roles.getClusterRoles()) {
                meta.put(cluster, m);
            }

            for (String namespace : roles.getNamespaceRoles()) {
                meta.put(namespace, m);
            }
        }
    }

    private boolean isSupserUser(String csRole) {
        return ClusterRole.CLUSTER_ADMIN.getName().equals(csRole);
    }
}
