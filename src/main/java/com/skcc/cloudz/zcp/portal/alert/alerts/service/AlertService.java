package com.skcc.cloudz.zcp.portal.alert.alerts.service;

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

import com.skcc.cloudz.zcp.portal.alert.alerts.vo.AlertCountVo;
import com.skcc.cloudz.zcp.portal.alert.alerts.vo.AlertHistoryVo;
import com.skcc.cloudz.zcp.portal.alert.alerts.vo.AlertVo;
import com.skcc.cloudz.zcp.portal.alert.alerts.vo.ApiServerVo;
import com.skcc.cloudz.zcp.portal.alert.alerts.vo.NodeDownVo;
import com.skcc.cloudz.zcp.portal.alert.alerts.vo.NodeNotReadyVo;

@Service
public class AlertService {

	private static Logger logger = Logger.getLogger(AlertService.class);

	@Value("${props.alertmanager.baseUrl}")
	private String baseUrl;

	public AlertCountVo getActiveCount() {
		String url = UriComponentsBuilder.fromUriString(baseUrl).path("/alertList").build().toString();
		logger.info(url);

		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<AlertVo[]> entity = new HttpEntity<AlertVo[]>(headers);

		RestTemplate restTemplate = new RestTemplate();
		
		AlertVo[] alertVo = null;
		AlertCountVo alertCountVo = new AlertCountVo();
		
		try {
			ResponseEntity<AlertVo[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, AlertVo[].class);
			HttpStatus statusCode = response.getStatusCode();

			if (statusCode == HttpStatus.OK) {
				alertVo = response.getBody();
				alertCountVo.setCount(alertVo.length + "");

			} else {
				alertCountVo.setCount(null);
			}
		} catch(Exception e) {
			e.printStackTrace();
			alertCountVo.setCount(null);
		}

		return alertCountVo;

		/*
		 * String url =
		 * UriComponentsBuilder.fromUriString(baseUrl).path("/activeCount").build().
		 * toString(); logger.info(url);
		 * 
		 * HttpHeaders headers = new HttpHeaders();
		 * 
		 * headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON
		 * })); headers.setContentType(MediaType.APPLICATION_JSON);
		 * 
		 * HttpEntity<AlertCountVo> entity = new HttpEntity<AlertCountVo>(headers);
		 * 
		 * RestTemplate restTemplate = new RestTemplate(); ResponseEntity<AlertCountVo>
		 * response = restTemplate.exchange(url, HttpMethod.GET, entity,
		 * AlertCountVo.class);
		 * 
		 * HttpStatus statusCode = response.getStatusCode();
		 * 
		 * AlertCountVo alertCountVo = new AlertCountVo(); if (statusCode ==
		 * HttpStatus.OK) { alertCountVo = response.getBody(); }
		 * 
		 * return alertCountVo;
		 */

	}

	public ApiServerVo getApiServer() {
		String url = UriComponentsBuilder.fromUriString(baseUrl).path("/apiServer").build().toString();
		logger.info(url);

		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<ApiServerVo> entity = new HttpEntity<ApiServerVo>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ApiServerVo apiServerVo = new ApiServerVo();
		
		try {
			ResponseEntity<ApiServerVo> response = restTemplate.exchange(url, HttpMethod.GET, entity, ApiServerVo.class);
			HttpStatus statusCode = response.getStatusCode();
			
			if (statusCode == HttpStatus.OK) {
				apiServerVo = response.getBody();

				if (apiServerVo.getCode() != null) {
					apiServerVo.setStatus(null);
				}
			} else {
				apiServerVo.setStatus(null);
			}
		} catch(Exception e) {
			e.printStackTrace();
			apiServerVo.setStatus(null);
		}

		return apiServerVo;
	}

	public NodeNotReadyVo getNodeNotReady() {
		String url = UriComponentsBuilder.fromUriString(baseUrl).path("/nodeNotReady").build().toString();
		logger.info(url);

		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<NodeNotReadyVo> entity = new HttpEntity<NodeNotReadyVo>(headers);

		RestTemplate restTemplate = new RestTemplate();
		NodeNotReadyVo nodeNotReadyVo = new NodeNotReadyVo();
		
		try {
			ResponseEntity<NodeNotReadyVo> response = restTemplate.exchange(url, HttpMethod.GET, entity,
					NodeNotReadyVo.class);

			HttpStatus statusCode = response.getStatusCode();

			if (statusCode == HttpStatus.OK) {
				nodeNotReadyVo = response.getBody();

				if (nodeNotReadyVo.getCode() != null) {
					nodeNotReadyVo.setCount(null);
				}
			} else {
				nodeNotReadyVo.setCount(null);
			}
		} catch(Exception e) {
			e.printStackTrace();
			nodeNotReadyVo.setCount(null);
		}

		return nodeNotReadyVo;
	}

	public NodeDownVo getNodeDown() {
		String url = UriComponentsBuilder.fromUriString(baseUrl).path("/nodeDown").build().toString();
		logger.info(url);

		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<NodeDownVo> entity = new HttpEntity<NodeDownVo>(headers);

		RestTemplate restTemplate = new RestTemplate();
		NodeDownVo nodeDownVo = new NodeDownVo();
		
		try {
			ResponseEntity<NodeDownVo> response = restTemplate.exchange(url, HttpMethod.GET, entity, NodeDownVo.class);

			HttpStatus statusCode = response.getStatusCode();

			if (statusCode == HttpStatus.OK) {
				nodeDownVo = response.getBody();

				if (nodeDownVo.getCode() != null) {
					nodeDownVo.setCount(null);
				}
			} else {
				nodeDownVo.setCount(null);
			}
		} catch(Exception e) {
			e.printStackTrace();
			nodeDownVo.setCount(null);
		}

		return nodeDownVo;
	}

	public AlertVo[] getAlertList() throws Exception {
		String url = UriComponentsBuilder.fromUriString(baseUrl).path("/alertList").build().toString();
		logger.info(url);

		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<AlertVo[]> entity = new HttpEntity<AlertVo[]>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<AlertVo[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, AlertVo[].class);

		HttpStatus statusCode = response.getStatusCode();

		AlertVo[] alertVo = null;
		if (statusCode == HttpStatus.OK) {
			alertVo = response.getBody();
		}
		return alertVo;
	}

	public AlertHistoryVo[] getAlertHistoryList(String time) throws Exception {
		String url = UriComponentsBuilder.fromUriString(baseUrl).path("/alertHistory/{time}").buildAndExpand(time)
				.toString();
		logger.info(url);

		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<AlertHistoryVo[]> entity = new HttpEntity<AlertHistoryVo[]>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<AlertHistoryVo[]> response = restTemplate.exchange(url, HttpMethod.GET, entity,
				AlertHistoryVo[].class);

		HttpStatus statusCode = response.getStatusCode();

		AlertHistoryVo[] alertHistoryVo = null;
		if (statusCode == HttpStatus.OK) {
			alertHistoryVo = response.getBody();
		}

		return alertHistoryVo;
	}

}
