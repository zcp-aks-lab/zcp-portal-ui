package com.skcc.cloudz.zcp.portal.alert.rules.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.skcc.cloudz.zcp.common.util.Message;
import com.skcc.cloudz.zcp.portal.alert.rules.vo.RepeatVo;
import com.skcc.cloudz.zcp.portal.alert.rules.vo.RuleVo;

@Service
public class RuleService {

	private static Logger logger = Logger.getLogger(RuleService.class);

	@Value("${props.alertmanager.baseUrl}")
	private String baseUrl;

	@Value("${props.iam.baseUrl}")
	private String iamBaseUrl;

	@Autowired
	Message message;

	public RuleVo[] getRuleList() {
		String url = UriComponentsBuilder.fromUriString(baseUrl).path("/rule").build().toString();
		logger.info(url);

		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<RuleVo[]> entity = new HttpEntity<RuleVo[]>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<RuleVo[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, RuleVo[].class);

		HttpStatus statusCode = response.getStatusCode();

		RuleVo[] ruleVo = null;
		if (statusCode == HttpStatus.OK) {
			ruleVo = response.getBody();
		}

		return ruleVo;
	}

	public RuleVo deleteRule(RuleVo params) {
		String url = UriComponentsBuilder.fromUriString(baseUrl).path("/rule/{id}").buildAndExpand(params.getId())
				.toString();
		logger.info(url);

		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<RuleVo> entity = new HttpEntity<RuleVo>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<RuleVo> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, RuleVo.class);

		HttpStatus statusCode = response.getStatusCode();

		RuleVo ruleVo = null;
		if (statusCode == HttpStatus.OK) {
			ruleVo = response.getBody();
		}

		return ruleVo;
	}

	public RuleVo createRule(Map<String, Object> params) {
		String url = UriComponentsBuilder.fromUriString(baseUrl).path("/rule").build().toString();
		logger.info(url);

		RuleVo ruleParam = new RuleVo();

		ruleParam.setChannel(params.get("channel").toString());
		ruleParam.setDuration(params.get("duration").toString());
		ruleParam.setSeverity(params.get("severity").toString());
		ruleParam.setType(params.get("type").toString());

		if ("NodeDown".equals(params.get("type"))) {
			ruleParam.setValue1(message.get("NodeDown"));
			ruleParam.setCondition("=");
			ruleParam.setValue2("0");

		} else if ("ApiserverDown".equals(params.get("type"))) {
			ruleParam.setValue1(message.get("ApiserverDown"));
			ruleParam.setCondition("=");
			ruleParam.setValue2("0");

		} else if ("K8SNodeNotReady".equals(params.get("type"))) {
			ruleParam.setValue1(message.get("K8SNodeNotReady"));
			ruleParam.setCondition("=");
			ruleParam.setValue2("0");

		} else {
			ruleParam.setValue1(message.get(ruleParam.getType()));
			ruleParam.setCondition(params.get("condition").toString());
			ruleParam.setValue2(params.get("value2").toString());
		}

		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<RuleVo> entity = new HttpEntity<RuleVo>(ruleParam, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<RuleVo> response = restTemplate.exchange(url, HttpMethod.POST, entity, RuleVo.class);

		HttpStatus statusCode = response.getStatusCode();

		RuleVo ruleVo = null;
		if (statusCode == HttpStatus.OK) {
			ruleVo = response.getBody();
		}

		return ruleVo;
	}

	public RepeatVo getRepeatInterval() {
		String url = UriComponentsBuilder.fromUriString(baseUrl).path("/repeat").build().toString();
		logger.info(url);

		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<RepeatVo> entity = new HttpEntity<RepeatVo>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<RepeatVo> response = restTemplate.exchange(url, HttpMethod.GET, entity, RepeatVo.class);

		HttpStatus statusCode = response.getStatusCode();

		RepeatVo repeatVo = new RepeatVo();
		if (statusCode == HttpStatus.OK) {
			repeatVo = response.getBody();
		}

		return repeatVo;
	}

	public RepeatVo updateRepeat(Map<String, Object> params) {
		String url = UriComponentsBuilder.fromUriString(baseUrl).path("/repeat").build().toString();
		logger.info(url);

		RepeatVo repeatParam = new RepeatVo();
		repeatParam.setRepeat_interval(params.get("repeat") + "m");

		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<RepeatVo> entity = new HttpEntity<RepeatVo>(repeatParam, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<RepeatVo> response = restTemplate.exchange(url, HttpMethod.PUT, entity, RepeatVo.class);

		HttpStatus statusCode = response.getStatusCode();

		RepeatVo repeatVo = null;
		if (statusCode == HttpStatus.OK) {
			repeatVo = response.getBody();
		}

		return repeatVo;
	}

	public List<String> getNamespace() throws Exception {
		String url = UriComponentsBuilder.fromUriString(baseUrl).path("/namespaceList").build().toString();
		logger.info(url);

		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, String[].class);

		HttpStatus statusCode = response.getStatusCode();

		List<String> namespaceList = new ArrayList<String>();
		String[] list = null;
		if (statusCode == HttpStatus.OK) {
			list = response.getBody();
			for (String nsList : list) {
				namespaceList.add(nsList);
			}
		}

		return namespaceList;
	}

}
