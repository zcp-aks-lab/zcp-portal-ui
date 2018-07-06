package com.skcc.cloudz.zcp.portal.common.contoller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skcc.cloudz.zcp.api.iam.domain.vo.ZcpNodeVo;
import com.skcc.cloudz.zcp.portal.common.service.MainService;

@Controller
public class MainController {
    
    @Autowired
    private MainService mainService;
    
    @GetMapping(value = {"/main", "/"}, consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String main() throws Exception {
        return "content/main";
    }
    
    @GetMapping(value = "/main/getNodes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<ZcpNodeVo> getNodes() throws Exception {
        return mainService.getNodes();
    }
    
    @PostMapping(value = "/main/getChartsData", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getChartsData() throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        resultMap.put("nodesStatus", mainService.getNodesStatus());
        resultMap.put("deploymentsStatus", mainService.getDeploymentsStatus());
        resultMap.put("podsStatus", mainService.getPodsStatus());
        resultMap.put("cpuStatus", mainService.getCpuStatus());
        resultMap.put("memoryStatus", mainService.getMemoryStatus());
                
        return resultMap;
    }
    
    @GetMapping(value = "/test", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String test() throws Exception {
        return "content/test";
    }

}
