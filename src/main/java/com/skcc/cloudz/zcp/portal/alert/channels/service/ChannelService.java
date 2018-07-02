package com.skcc.cloudz.zcp.portal.alert.channels.service;

import java.util.Arrays;

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
import com.skcc.cloudz.zcp.portal.alert.channels.vo.ChannelDtlVo;
import com.skcc.cloudz.zcp.portal.alert.channels.vo.ChannelVo;
import com.skcc.cloudz.zcp.portal.alert.rules.vo.RuleVo;

@Service
public class ChannelService {

	private static Logger logger = Logger.getLogger(ChannelService.class);

	@Value("${props.alertmanager.baseUrl}")
	private String baseUrl;

	@Value("${props.iam.baseUrl}")
	private String iamBaseUrl;

	@Autowired
	Message message;

	public ChannelVo[] getChannelList() {
		String url = UriComponentsBuilder.fromUriString(baseUrl).path("/channel").build().toString();
		logger.info(url);

		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<ChannelVo[]> entity = new HttpEntity<ChannelVo[]>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<ChannelVo[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, ChannelVo[].class);

		HttpStatus statusCode = response.getStatusCode();

		ChannelVo[] channelVo = null;
		if (statusCode == HttpStatus.OK) {
			channelVo = response.getBody();
		}

		return channelVo;
	}
	
	public ChannelDtlVo getChannelDtl(String id) {
		String url = UriComponentsBuilder.fromUriString(baseUrl).path("/channel/{id}").buildAndExpand(id).toString();
		logger.info(url);

		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<ChannelDtlVo> entity = new HttpEntity<ChannelDtlVo>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<ChannelDtlVo> response = restTemplate.exchange(url, HttpMethod.GET, entity, ChannelDtlVo.class);

		HttpStatus statusCode = response.getStatusCode();

		ChannelDtlVo channelDtlVo = null;
		if (statusCode == HttpStatus.OK) {
			channelDtlVo = response.getBody();
		}

		return channelDtlVo;
	}
}
