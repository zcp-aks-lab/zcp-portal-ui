package com.skcc.cloudz.zcp.portal.alertmanager.alert.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.skcc.cloudz.zcp.portal.alertmanager.alert.vo.AlertCountVo;
import com.skcc.cloudz.zcp.portal.alertmanager.alert.vo.AlertHistoryVo;
import com.skcc.cloudz.zcp.portal.alertmanager.alert.vo.AlertVo;
import com.skcc.cloudz.zcp.portal.alertmanager.alert.vo.ApiServerVo;
import com.skcc.cloudz.zcp.portal.alertmanager.alert.vo.NodeDownVo;
import com.skcc.cloudz.zcp.portal.alertmanager.alert.vo.NodeNotReadyVo;

@Service
public class AlertService {

	@Value("${props.alertmanager.baseUrl}")
	private String baseUrl;

	// String URL_ACTIVECOUNT = "http://localhost:8080/activeCount";
	// String URL_APISERVER = "http://localhost:8080/apiServer";
	// String URL_NODENOTREADY = "http://localhost:8080/nodeNotReady";
	// String URL_NODEDOWN = "http://localhost:8080/nodeDown";
	// String URL_ALERTLIST = "http://localhost:8080/alertList";
	// String URL_HISTORYLIST = "http://localhost:8080/alertHistory";

	public AlertCountVo getActiveCount() {
		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<AlertCountVo> entity = new HttpEntity<AlertCountVo>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<AlertCountVo> response = restTemplate.exchange(baseUrl + "/activeCount", HttpMethod.GET, entity,
				AlertCountVo.class);

		HttpStatus statusCode = response.getStatusCode();

		AlertCountVo alertCountVo = new AlertCountVo();
		if (statusCode == HttpStatus.OK) {
			alertCountVo = response.getBody();
		}

		return alertCountVo;
	}

	public ApiServerVo getApiServer() {
		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<ApiServerVo> entity = new HttpEntity<ApiServerVo>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<ApiServerVo> response = restTemplate.exchange(baseUrl + "/apiServer", HttpMethod.GET, entity,
				ApiServerVo.class);

		HttpStatus statusCode = response.getStatusCode();

		ApiServerVo apiServerVo = new ApiServerVo();
		if (statusCode == HttpStatus.OK) {
			apiServerVo = response.getBody();
		}

		return apiServerVo;
	}

	public NodeNotReadyVo getNodeNotReady() {
		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<NodeNotReadyVo> entity = new HttpEntity<NodeNotReadyVo>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<NodeNotReadyVo> response = restTemplate.exchange(baseUrl + "/nodeNotReady", HttpMethod.GET,
				entity, NodeNotReadyVo.class);

		HttpStatus statusCode = response.getStatusCode();

		NodeNotReadyVo nodeNotReadyVo = new NodeNotReadyVo();
		if (statusCode == HttpStatus.OK) {
			nodeNotReadyVo = response.getBody();
		}

		return nodeNotReadyVo;
	}

	public NodeDownVo getNodeDown() {
		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<NodeDownVo> entity = new HttpEntity<NodeDownVo>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<NodeDownVo> response = restTemplate.exchange(baseUrl + "/nodeDown", HttpMethod.GET, entity,
				NodeDownVo.class);

		HttpStatus statusCode = response.getStatusCode();

		NodeDownVo nodeDownVo = new NodeDownVo();
		if (statusCode == HttpStatus.OK) {
			nodeDownVo = response.getBody();
		}

		return nodeDownVo;
	}

	public AlertVo[] getAlertList() {
		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<AlertVo[]> entity = new HttpEntity<AlertVo[]>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<AlertVo[]> response = restTemplate.exchange(baseUrl + "/alertList", HttpMethod.GET, entity,
				AlertVo[].class);

		HttpStatus statusCode = response.getStatusCode();

		AlertVo[] alertVo = null;
		if (statusCode == HttpStatus.OK) {
			alertVo = response.getBody();
		}

		return alertVo;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AlertHistoryVo[] getAlertHistoryList() {
		HttpHeaders headers = new HttpHeaders();
		Map params = new HashMap();
		params.put("time", "time1");

		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<AlertHistoryVo[]> entity = new HttpEntity<AlertHistoryVo[]>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<AlertHistoryVo[]> response = restTemplate.exchange(baseUrl + "/alertHistory/{time}",
				HttpMethod.GET, entity, AlertHistoryVo[].class, params);

		HttpStatus statusCode = response.getStatusCode();

		AlertHistoryVo[] alertHistoryVo = null;
		if (statusCode == HttpStatus.OK) {
			alertHistoryVo = response.getBody();
		}

		return alertHistoryVo;
	}

}
