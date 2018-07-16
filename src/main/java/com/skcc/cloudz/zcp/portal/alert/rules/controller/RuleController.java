package com.skcc.cloudz.zcp.portal.alert.rules.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skcc.cloudz.zcp.common.constants.ApiResult;
import com.skcc.cloudz.zcp.portal.alert.rules.service.RuleService;
import com.skcc.cloudz.zcp.portal.alert.rules.vo.RepeatVo;
import com.skcc.cloudz.zcp.portal.alert.rules.vo.RuleVo;
import com.skcc.cloudz.zcp.portal.management.user.service.UserService;

@Controller
@RequestMapping(value = RuleController.RESOURCE_PATH)
public class RuleController {

	static final String RESOURCE_PATH = "/alert";

	@Autowired
	private RuleService ruleService;

	@Autowired
	private UserService userService;

	@GetMapping(value = "/rules", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
	public String rules(Model model) throws Exception {
		RepeatVo repeatVo = new RepeatVo();
		repeatVo = ruleService.getRepeatInterval();

		String repeat = repeatVo.getRepeat_interval();
		model.addAttribute("repeatInterval", repeat.replace("m", ""));

		return "content/alert/rules/rules";
	}

	@GetMapping(value = "/ruleList", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> ruleList() throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			resultMap.put("resultCd", ApiResult.SUCCESS.getCode());
			resultMap.put("resultMsg", ApiResult.SUCCESS.getName());
			resultMap.put("resultData", ruleService.getRuleList());
		} catch (Exception e) {
			e.printStackTrace();

			resultMap.put("resultCd", ApiResult.FAIL.getCode());
			resultMap.put("resultMsg", e.getMessage());
		}

		return resultMap;
	}

	@GetMapping(value = "/addRule", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
	public String addRule(Model model) throws Exception {
		List<String> namespace = userService.getNamespaces();
		model.addAttribute("namespace", namespace);

		List<String> channel = ruleService.getChannels();
		model.addAttribute("channel", channel);

		return "content/alert/rules/addrules";
	}
	
	@PostMapping(value = "/getDeployment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> getDeployment(@RequestBody Map<String, Object> params) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap.put("resultCd", ApiResult.SUCCESS.getCode());
			resultMap.put("resultMsg", ApiResult.SUCCESS.getName());
			resultMap.put("resultData", ruleService.getDeployments(params));
			
		} catch (Exception e) {
			e.printStackTrace();

			resultMap.put("resultCd", ApiResult.FAIL.getCode());
			resultMap.put("resultMsg", e.getMessage());
		}

		return resultMap;
	}

	@PostMapping(value = "/createRule", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> createRule(@RequestBody Map<String, Object> params) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		System.out.println(params);
		try {
			resultMap.put("resultCd", ApiResult.SUCCESS.getCode());
			resultMap.put("resultMsg", ApiResult.SUCCESS.getName());
			resultMap.put("resultData", ruleService.createRule(params));
		} catch (Exception e) {
			e.printStackTrace();

			resultMap.put("resultCd", ApiResult.FAIL.getCode());
			resultMap.put("resultMsg", e.getMessage());
		}

		return resultMap;
	}

	@GetMapping(value = "/detailRule/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
	public String detailRule(Model model, @PathVariable("id") String id) throws Exception {
		RuleVo ruleDtl = ruleService.getRuleDtl(id);

		List<String> channel = ruleService.getChannels();
		model.addAttribute("channel", channel);

		if (ruleDtl != null) {
			model.addAttribute("type", ruleDtl.getType());
			model.addAttribute("duration", ruleDtl.getDuration());
			model.addAttribute("severity", ruleDtl.getSeverity());
			model.addAttribute("selectedChannel", ruleDtl.getChannel());
			model.addAttribute("condition", ruleDtl.getCondition());
			model.addAttribute("value2", ruleDtl.getValue2().trim());
			model.addAttribute("value", ruleDtl.getValue());
		}

		return "content/alert/rules/detailrules";
	}

	@PostMapping(value = "/updateRule", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> updateRule(@RequestBody Map<String, Object> params) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			resultMap.put("resultCd", ApiResult.SUCCESS.getCode());
			resultMap.put("resultMsg", ApiResult.SUCCESS.getName());
			resultMap.put("resultData", ruleService.updateRule(params));
		} catch (Exception e) {
			e.printStackTrace();

			resultMap.put("resultCd", ApiResult.FAIL.getCode());
			resultMap.put("resultMsg", e.getMessage());
		}

		return resultMap;
	}

	@PostMapping(value = "/deleteRule", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> deleteRule(@RequestBody RuleVo ruleVo) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			ruleService.deleteRule(ruleVo);

			resultMap.put("resultCd", ApiResult.SUCCESS.getCode());
			resultMap.put("resultMsg", ApiResult.SUCCESS.getName());
		} catch (Exception e) {
			e.printStackTrace();

			resultMap.put("resultCd", ApiResult.FAIL.getCode());
			resultMap.put("resultMsg", e.getMessage());
		}

		return resultMap;
	}

	@PostMapping(value = "/updateRepeat", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> updateRepeat(@RequestBody Map<String, Object> params) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			resultMap.put("resultCd", ApiResult.SUCCESS.getCode());
			resultMap.put("resultMsg", ApiResult.SUCCESS.getName());
			resultMap.put("resultData", ruleService.updateRepeat(params));
		} catch (Exception e) {
			e.printStackTrace();

			resultMap.put("resultCd", ApiResult.FAIL.getCode());
			resultMap.put("resultMsg", e.getMessage());
		}

		return resultMap;
	}

}
