package com.skcc.cloudz.zcp.portal.alert.rules.service;

import java.util.Arrays;

import org.apache.log4j.Logger;
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

import com.skcc.cloudz.zcp.portal.alert.rules.vo.RuleVo;

@Service
public class RuleService {

	private static Logger logger = Logger.getLogger(RuleService.class);

	@Value("${props.alertmanager.baseUrl}")
	private String baseUrl;

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
		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<RuleVo> entity = new HttpEntity<RuleVo>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<RuleVo> response = restTemplate.exchange(baseUrl + "/rule/{id}", HttpMethod.DELETE, entity,
				RuleVo.class, params.getId());

		HttpStatus statusCode = response.getStatusCode();

		RuleVo ruleVo = null;
		if (statusCode == HttpStatus.OK) {
			ruleVo = response.getBody();
		}

		return ruleVo;
	}

}
