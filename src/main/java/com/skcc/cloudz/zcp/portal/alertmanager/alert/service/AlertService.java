package com.skcc.cloudz.zcp.portal.alertmanager.alert.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.skcc.cloudz.zcp.portal.alertmanager.alert.vo.AlertVo;
import com.skcc.cloudz.zcp.portal.alertmanager.alert.vo.ApiServerVo;
import com.skcc.cloudz.zcp.portal.alertmanager.alert.vo.NodeDownVo;
import com.skcc.cloudz.zcp.portal.alertmanager.alert.vo.NodeNotReadyVo;

@Service
public class AlertService {
	
	final String URL_ACTIVECOUNT = "http://localhost:8080/activeCount";
	final String URL_APISERVER = "http://localhost:8080/apiServer";
	final String URL_NODENOTREADY = "http://localhost:8080/nodeNotReady";
	final String URL_NODEDOWN = "http://localhost:8080/nodeDown";
	final String URL_ALERTLIST = "http://localhost:8080/alertList";

	public AlertCountVo getActiveCount() {
		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<AlertCountVo> entity = new HttpEntity<AlertCountVo>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<AlertCountVo> response = restTemplate.exchange(URL_ACTIVECOUNT, HttpMethod.GET, entity,
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
		ResponseEntity<ApiServerVo> response = restTemplate.exchange(URL_APISERVER, HttpMethod.GET, entity,
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
		ResponseEntity<NodeNotReadyVo> response = restTemplate.exchange(URL_NODENOTREADY, HttpMethod.GET, entity,
				NodeNotReadyVo.class);

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
		ResponseEntity<NodeDownVo> response = restTemplate.exchange(URL_NODEDOWN, HttpMethod.GET, entity,
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
		ResponseEntity<AlertVo[]> response = restTemplate.exchange(URL_ALERTLIST, HttpMethod.GET, entity,
				AlertVo[].class);

		HttpStatus statusCode = response.getStatusCode();

		AlertVo[] alertVo = null;
		if (statusCode == HttpStatus.OK) {
			alertVo = response.getBody();
		}

		return alertVo;
	}

}
