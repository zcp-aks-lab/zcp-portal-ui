package com.skcc.cloudz.zcp.portal.alert.channels.controller;

import java.util.HashMap;
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
import com.skcc.cloudz.zcp.portal.alert.channels.service.ChannelService;
import com.skcc.cloudz.zcp.portal.alert.channels.vo.ChannelDtlVo;
import com.skcc.cloudz.zcp.portal.alert.channels.vo.ChannelVo;
import com.skcc.cloudz.zcp.portal.alert.rules.vo.RuleVo;

@Controller
@RequestMapping(value = ChannelController.RESOURCE_PATH)
public class ChannelController {

	static final String RESOURCE_PATH = "/alert";

	@Autowired
	private ChannelService channelService;

	@GetMapping(value = "/channels", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
	public String channelList(Model model) throws Exception {
		return "content/alert/channels/channel";
	}

	@GetMapping(value = "/channelList", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> channelList() throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			resultMap.put("resultCd", ApiResult.SUCCESS.getCode());
			resultMap.put("resultMsg", ApiResult.SUCCESS.getName());
			resultMap.put("resultData", channelService.getChannelList());
		} catch (Exception e) {
			e.printStackTrace();

			resultMap.put("resultCd", ApiResult.FAIL.getCode());
			resultMap.put("resultMsg", e.getMessage());
		}

		return resultMap;
	}

	@PostMapping(value = "/channelDtl", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> getChannelDtl(@RequestBody Map<String, Object> params) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			resultMap.put("resultCd", ApiResult.SUCCESS.getCode());
			resultMap.put("resultMsg", ApiResult.SUCCESS.getName());
			resultMap.put("resultData", channelService.getChannelDtl(params.get("id").toString()));
		} catch (Exception e) {
			e.printStackTrace();

			resultMap.put("resultCd", ApiResult.FAIL.getCode());
			resultMap.put("resultMsg", e.getMessage());
		}

		return resultMap;
	}

	@GetMapping(value = "/popChannelAdd", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
	public String popChannelAdd(Model model) throws Exception {
		return "content/alert/channels/pop/popChannelAdd";
	}
	
	@GetMapping(value = "/popCommon", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
	public String popCommon(Model model) throws Exception {
		return "content/alert/common/alertPopup";
	}

	@PostMapping(value = "/createChannel", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> createChannel(@RequestBody Map<String, Object> params) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		System.out.println(params);
		try {
			resultMap.put("resultCd", ApiResult.SUCCESS.getCode());
			resultMap.put("resultMsg", ApiResult.SUCCESS.getName());
			resultMap.put("resultData", channelService.createChannel(params));
		} catch (Exception e) {
			e.printStackTrace();

			resultMap.put("resultCd", ApiResult.FAIL.getCode());
			resultMap.put("resultMsg", e.getMessage());
		}

		return resultMap;
	}

}
