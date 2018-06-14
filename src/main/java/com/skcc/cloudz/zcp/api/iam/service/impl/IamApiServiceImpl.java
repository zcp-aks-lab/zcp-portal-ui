package com.skcc.cloudz.zcp.api.iam.service.impl;

import java.util.HashMap;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.skcc.cloudz.zcp.api.iam.domain.vo.ApiResponseVo;
import com.skcc.cloudz.zcp.api.iam.service.IamApiService;
import com.skcc.cloudz.zcp.portal.system.domain.dto.MyUserDto;

@Service
public class IamApiServiceImpl implements IamApiService {
    
    private static final Logger log = LoggerFactory.getLogger(IamApiServiceImpl.class);
    
    @Value("${props.iam.baseUrl}")
    private String iamBaseUrl;
    
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ApiResponseVo getUser(String userId) {
        ApiResponseVo apiResponseVo = new ApiResponseVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl).path("/iam/user/{id}").buildAndExpand(userId).toString();
            log.info("===> Request Url : {}", url);
            
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> requestEntity = new HttpEntity<String>(headers); 
            
            ResponseEntity<ApiResponseVo> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, ApiResponseVo.class);
            
            log.info("===> Response status : {}", responseEntity.getStatusCode().value());
            log.info("===> Response body msg : {}", responseEntity.getBody().getMsg());
            log.info("===> Response body code : {}", responseEntity.getBody().getCode());
            log.info("===> Response body data : {}", responseEntity.getBody().getData());
            
            if (responseEntity!= null && responseEntity.getStatusCode() == HttpStatus.OK) {
                apiResponseVo.setCode(responseEntity.getBody().getCode());
                apiResponseVo.setMsg(responseEntity.getBody().getMsg());
                apiResponseVo.setData(responseEntity.getBody().getData());    
            }
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        
        return apiResponseVo;
    }

    @Override
    public ApiResponseVo setUser(MyUserDto myUserDto) {
        ApiResponseVo apiResponseVo = new ApiResponseVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl).path("/iam/user/{id}").buildAndExpand(myUserDto.getUserId()).toString();
            log.info("===> Request Url : {}", url);
            
            ObjectMapper objectMapper = new ObjectMapper();
            
            HashMap<String, Object> reqMap = new HashMap<String, Object>();
            reqMap.put("defaultNamespace", myUserDto.getDefaultNamespace());
            reqMap.put("email", myUserDto.getEmail());
            reqMap.put("firstName", myUserDto.getFirstName());
            reqMap.put("username", myUserDto.getUsername());
            
            String requestBody = objectMapper.writeValueAsString(reqMap);
            log.info("===> Request Body : {}", requestBody);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<String> entity = new HttpEntity<String>(requestBody, headers);
            
            ResponseEntity<ApiResponseVo> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, entity, ApiResponseVo.class);
            
            log.info("===> Response status : {}", responseEntity.getStatusCode().value());
            log.info("===> Response body msg : {}", responseEntity.getBody().getMsg());
            log.info("===> Response body code : {}", responseEntity.getBody().getCode());
            log.info("===> Response body data : {}", responseEntity.getBody().getData());
            
            apiResponseVo.setCode(responseEntity.getBody().getCode());
            apiResponseVo.setMsg(responseEntity.getBody().getMsg());
            apiResponseVo.setData(responseEntity.getBody().getData());
        } catch (RestClientException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return apiResponseVo;
    }

    @Override
    public ApiResponseVo updatePassword(MyUserDto myUserDto) {
        ApiResponseVo apiResponseVo = new ApiResponseVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl).path("/iam/user/{id}/password").buildAndExpand(myUserDto.getUserId()).toString();
            log.info("===> Request Url : {}", url);
            
            ObjectMapper objectMapper = new ObjectMapper();
            
            HashMap<String, Object> reqMap = new HashMap<String, Object>();
            reqMap.put("currentPassword", myUserDto.getCurrentPassword());
            reqMap.put("newPassword", myUserDto.getNewPassword());
            
            String requestBody = objectMapper.writeValueAsString(reqMap);
            log.info("===> Request Body : {}", requestBody);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<String> entity = new HttpEntity<String>(requestBody, headers);
            
            ResponseEntity<ApiResponseVo> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, entity, ApiResponseVo.class);
            
            log.info("===> Response status : {}", responseEntity.getStatusCode().value());
            log.info("===> Response body msg : {}", responseEntity.getBody().getMsg());
            log.info("===> Response body code : {}", responseEntity.getBody().getCode());
            log.info("===> Response body data : {}", responseEntity.getBody().getData());
            
            apiResponseVo.setCode(responseEntity.getBody().getCode());
            apiResponseVo.setMsg(responseEntity.getBody().getMsg());
            apiResponseVo.setData(responseEntity.getBody().getData());
        } catch (RestClientException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return apiResponseVo;
    }
    

}
