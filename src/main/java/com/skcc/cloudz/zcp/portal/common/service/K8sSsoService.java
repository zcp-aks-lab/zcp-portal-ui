package com.skcc.cloudz.zcp.portal.common.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.skcc.cloudz.zcp.api.iam.domain.vo.ApiResponseVo;

@Component
public class K8sSsoService {
    
    private static final Logger log = LoggerFactory.getLogger(K8sSsoService.class);
    
    @Autowired
    private RestTemplate restTemplate;
    
    final String DASHBOARD_BASE_URL = "https://kubernetes";
    
    public String getCsrfToken() {
        String result = StringUtils.EMPTY;
        
        try {
            String url = UriComponentsBuilder.fromUriString(DASHBOARD_BASE_URL)
                    .path("/api/v1/csrftoken/login")
                    .buildAndExpand()
                    .toString();
            log.info("===> Request Url : {}", url);
            
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> requestEntity = new HttpEntity<String>(headers); 
            
            @SuppressWarnings("rawtypes")
            ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Map.class);
            
            log.info("===> Response status : {}", responseEntity.getStatusCode().value());
            log.info("===> Response body msg : {}", responseEntity.getBody());
            
            if (responseEntity!= null && responseEntity.getStatusCode() == HttpStatus.OK) {
                result = responseEntity.getBody().get("token").toString();
            }
            
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    @SuppressWarnings("unchecked")
    public Map<String, Object> login(String token, HashMap<String, Object> reqMap) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            String url = UriComponentsBuilder.fromUriString(DASHBOARD_BASE_URL)
                    .path("/api/v1/login")
                    .buildAndExpand()
                    .toString();
            log.info("===> Request Url : {}", url);
            
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(reqMap);
            log.info("===> Request Body : {}", requestBody);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("x-csrf-token", token);
            
            HttpEntity<String> entity = new HttpEntity<String>(requestBody, headers); 
            
            @SuppressWarnings("rawtypes")
            ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
            
            log.info("===> Response status : {}", responseEntity.getStatusCode().value());
            log.info("===> Response body code : {}", responseEntity.getBody());
            log.info("===> Response body data : {}", responseEntity.getBody());
            
            if (responseEntity!= null && responseEntity.getStatusCode() == HttpStatus.OK) {
                resultMap.putAll(responseEntity.getBody());
            }
        } catch (RestClientException | IOException e) {
            e.printStackTrace();
        }
        
        return resultMap;
    }
    
    @SuppressWarnings("unchecked")
    public Map<String, Object> loginStatus(String token) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            String url = UriComponentsBuilder.fromUriString(DASHBOARD_BASE_URL)
                    .path("/api/v1/login/status")
                    .buildAndExpand()
                    .toString();
            log.info("===> Request Url : {}", url);
            
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Cookie", "jweToken=" + token);
            
            HttpEntity<String> entity = new HttpEntity<String>(headers); 
            
            @SuppressWarnings("rawtypes")
            ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            
            log.info("===> Response status : {}", responseEntity.getStatusCode().value());
            log.info("===> Response body code : {}", responseEntity.getBody());
            log.info("===> Response body data : {}", responseEntity.getBody());
            
            if (responseEntity!= null && responseEntity.getStatusCode() == HttpStatus.OK) {
                resultMap.putAll(responseEntity.getBody());
            }
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        
        return resultMap;
    }

}
