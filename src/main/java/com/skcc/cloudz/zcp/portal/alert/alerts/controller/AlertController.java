package com.skcc.cloudz.zcp.portal.alert.alerts.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skcc.cloudz.zcp.common.constants.ApiResult;
import com.skcc.cloudz.zcp.portal.alert.alerts.service.AlertService;
import com.skcc.cloudz.zcp.portal.alert.alerts.vo.AlertCountVo;
import com.skcc.cloudz.zcp.portal.alert.alerts.vo.ApiServerVo;
import com.skcc.cloudz.zcp.portal.alert.alerts.vo.NodeDownVo;
import com.skcc.cloudz.zcp.portal.alert.alerts.vo.NodeNotReadyVo;

@Controller
@RequestMapping(value = AlertController.RESOURCE_PATH)
public class AlertController {
	static final String RESOURCE_PATH = "/alert";

	@Autowired
	private AlertService alertService;

	@GetMapping(value = "/alerts", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
	public String alertStat(Model model) throws Exception {
		AlertCountVo alertCountVo = new AlertCountVo();

		alertCountVo = alertService.getActiveCount();
		model.addAttribute("activeCount", alertCountVo.getCount());

		ApiServerVo apiServerVo = new ApiServerVo();

		apiServerVo = alertService.getApiServer();
		model.addAttribute("apiServer", apiServerVo.getStatus());

		NodeNotReadyVo nodeNotReadyVo = new NodeNotReadyVo();

		nodeNotReadyVo = alertService.getNodeNotReady();
		model.addAttribute("nodeNotReadyCnt", nodeNotReadyVo.getCount());
		model.addAttribute("nodeNotReadyTotCnt", nodeNotReadyVo.getTotalCount());

		NodeDownVo nodeDownVo = new NodeDownVo();

		nodeDownVo = alertService.getNodeDown();
		model.addAttribute("nodeDownCnt", nodeDownVo.getCount());
		model.addAttribute("nodeDownTotCnt", nodeDownVo.getTotalCount());

		return "content/alert/alerts/alerting";
	}

	@GetMapping(value = "/alertList", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> alertList() throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap.put("resultCd", ApiResult.SUCCESS.getCode());
			resultMap.put("resultMsg", ApiResult.SUCCESS.getName());
			resultMap.put("resultData", alertService.getAlertList());
		} catch (Exception e) {
			e.printStackTrace();

			resultMap.put("resultCd", ApiResult.FAIL.getCode());
			resultMap.put("resultMsg", e.getMessage());
		}

		return resultMap;
	}

	@GetMapping(value = "/alertHistoryList", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> alertHistoryList(@RequestParam Map<String, Object> params) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (params.get("time") == null)
			params.put("time", "1");
		try {
			resultMap.put("resultCd", ApiResult.SUCCESS.getCode());
			resultMap.put("resultMsg", ApiResult.SUCCESS.getName());
			resultMap.put("resultData", alertService.getAlertHistoryList(params.get("time").toString()));
		} catch (Exception e) {
			e.printStackTrace();

			resultMap.put("resultCd", ApiResult.FAIL.getCode());
			resultMap.put("resultMsg", e.getMessage());
		}

		return resultMap;
	}

}
