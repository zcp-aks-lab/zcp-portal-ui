package com.skcc.cloudz.zcp.common.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.skcc.cloudz.zcp.common.service.IamApiService;

@Service
public class IamApiServiceImpl implements IamApiService {
    
    private static final Logger log = LoggerFactory.getLogger(IamApiServiceImpl.class);
    
    @Value("${props.iam.baseUrl}")
    private String iamBaseUrl;
    
    @Autowired
    private RestTemplate restTemplate;

    @SuppressWarnings("rawtypes")
    @Override
    public Map<String, Object> getUser(String username) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        ResponseEntity<Map> responseEntity = null;
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl).path("/iam/user").path("/{username}").buildAndExpand(username).toString();
            log.info("===> Request Url : {}", url);
            
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> requestEntity = new HttpEntity<String>(headers); 
            
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Map.class);
            
            log.info("===> Response status : {}", responseEntity.getStatusCode());
            log.info("===> Response body : {}", responseEntity.getBody());
            
            if (responseEntity!= null && responseEntity.getStatusCode() == HttpStatus.OK) {
                if (responseEntity.getBody().get("msg").equals("Success")) {
                    resultMap = (Map<String, Object>) responseEntity.getBody().get("data");
                }
            }
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        
        return resultMap;
    }

}
