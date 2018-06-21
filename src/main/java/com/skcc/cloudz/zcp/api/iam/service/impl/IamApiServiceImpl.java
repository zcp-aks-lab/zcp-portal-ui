package com.skcc.cloudz.zcp.api.iam.service.impl;

import java.io.IOException;
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
import com.skcc.cloudz.zcp.api.iam.domain.vo.ZcpUserListVo;
import com.skcc.cloudz.zcp.api.iam.domain.vo.ZcpUserResVo;
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
    public ZcpUserResVo getUser(String id) {
        ZcpUserResVo zcpUserResVo = new ZcpUserResVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl)
                    .path("/iam/user/{id}")
                    .buildAndExpand(id)
                    .toString();
            log.info("===> Request Url : {}", url);
            
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> requestEntity = new HttpEntity<String>(headers); 
            
            ResponseEntity<ZcpUserResVo> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, ZcpUserResVo.class);
            
            log.info("===> Response status : {}", responseEntity.getStatusCode().value());
            log.info("===> Response body msg : {}", responseEntity.getBody().getMsg());
            log.info("===> Response body code : {}", responseEntity.getBody().getCode());
            log.info("===> Response body data : {}", responseEntity.getBody().getData());
            
            if (responseEntity!= null && responseEntity.getStatusCode() == HttpStatus.OK) {
                zcpUserResVo.setCode(responseEntity.getBody().getCode());
                zcpUserResVo.setMsg(responseEntity.getBody().getMsg());
                zcpUserResVo.setData(responseEntity.getBody().getData());    
            }
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        
        return zcpUserResVo;
    }

    @Override
    public ApiResponseVo setUser(HashMap<String, Object> reqMap) {
        ApiResponseVo apiResponseVo = new ApiResponseVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl)
                    .path("/iam/user")
                    .buildAndExpand()
                    .toString();
            log.info("===> Request Url : {}", url);
            
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(reqMap);
            log.info("===> Request Body : {}", requestBody);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<String> entity = new HttpEntity<String>(requestBody, headers); 
            
            ResponseEntity<ApiResponseVo> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, ApiResponseVo.class);
            
            log.info("===> Response status : {}", responseEntity.getStatusCode().value());
            log.info("===> Response body msg : {}", responseEntity.getBody().getMsg());
            log.info("===> Response body code : {}", responseEntity.getBody().getCode());
            log.info("===> Response body data : {}", responseEntity.getBody().getData());
            
            if (responseEntity!= null && responseEntity.getStatusCode() == HttpStatus.OK) {
                apiResponseVo.setCode(responseEntity.getBody().getCode());
                apiResponseVo.setMsg(responseEntity.getBody().getMsg());
                apiResponseVo.setData(responseEntity.getBody().getData());    
            }
        } catch (RestClientException | IOException e) {
            e.printStackTrace();
        }
        
        return apiResponseVo;
    }

    @Override
    public ApiResponseVo updateUser(String id, HashMap<String, Object> reqMap) {
        ApiResponseVo apiResponseVo = new ApiResponseVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl)
                    .path("/iam/user/{id}")
                    .buildAndExpand(id)
                    .toString();
            log.info("===> Request Url : {}", url);
            
            ObjectMapper objectMapper = new ObjectMapper();
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
    public ApiResponseVo deleteUser(String id) {
        ApiResponseVo apiResponseVo = new ApiResponseVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl)
                    .path("/iam/user/{id}")
                    .buildAndExpand(id)
                    .toString();
            log.info("===> Request Url : {}", url);
            
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> requestEntity = new HttpEntity<String>(headers); 
            
            ResponseEntity<ApiResponseVo> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, ApiResponseVo.class);
            
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
    public ApiResponseVo updatePassword(MyUserDto myUserDto) {
        ApiResponseVo apiResponseVo = new ApiResponseVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl)
                    .path("/iam/user/{id}/password")
                    .buildAndExpand(myUserDto.getUserId())
                    .toString();
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
        } catch (RestClientException | IOException e) {
            e.printStackTrace();
        } 
        
        return apiResponseVo;
    }

    @Override
    public ApiResponseVo logout(String userId) {
        ApiResponseVo apiResponseVo = new ApiResponseVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl)
                    .path("/iam/user/{id}/logout")
                    .buildAndExpand(userId)
                    .toString();
            log.info("===> Request Url : {}", url);
            
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> requestEntity = new HttpEntity<String>(headers); 
            
            ResponseEntity<ApiResponseVo> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, ApiResponseVo.class);
            
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
    public ApiResponseVo kubeconfig(String userId, String namespace) {
        ApiResponseVo apiResponseVo = new ApiResponseVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl)
                    .path("/iam/user/{id}/kubeconfig")
                    .queryParam("namespace", namespace)
                    .buildAndExpand(userId)
                    .toString();
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
    public ApiResponseVo serviceAccount(String userId) {
        ApiResponseVo apiResponseVo = new ApiResponseVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl)
                    .path("/iam/user/{id}/serviceAccount")
                    .buildAndExpand(userId)
                    .toString();
            log.info("===> Request Url : {}", url);
            
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> requestEntity = new HttpEntity<String>(headers); 
            
            ResponseEntity<ApiResponseVo> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, ApiResponseVo.class);
            
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
    public ZcpUserListVo users() {
        ZcpUserListVo zcpUserListVo = new ZcpUserListVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl)
                    .path("/iam/users")
                    .buildAndExpand()
                    .toString();
            log.info("===> Request Url : {}", url);
            
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> requestEntity = new HttpEntity<String>(headers); 
            
            ResponseEntity<ZcpUserListVo> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, ZcpUserListVo.class);
            
            log.info("===> Response status : {}", responseEntity.getStatusCode().value());
            log.info("===> Response body msg : {}", responseEntity.getBody().getMsg());
            log.info("===> Response body code : {}", responseEntity.getBody().getCode());
            log.info("===> Response body data : {}", responseEntity.getBody().getData());
            
            if (responseEntity!= null && responseEntity.getStatusCode() == HttpStatus.OK) {
                zcpUserListVo.setCode(responseEntity.getBody().getCode());
                zcpUserListVo.setMsg(responseEntity.getBody().getMsg());
                zcpUserListVo.setData(responseEntity.getBody().getData());    
            }
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        
        return zcpUserListVo;
    }

    @Override
    public ApiResponseVo resetPassword(String id, HashMap<String, Object> reqMap) {
        ApiResponseVo apiResponseVo = new ApiResponseVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl)
                    .path("/iam/user/{id}/resetPassword")
                    .buildAndExpand(id)
                    .toString();
            log.info("===> Request Url : {}", url);
            
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(reqMap);
            log.info("===> Request Body : {}", requestBody);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<String> entity = new HttpEntity<String>(requestBody, headers); 
            
            ResponseEntity<ApiResponseVo> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, ApiResponseVo.class);
            
            log.info("===> Response status : {}", responseEntity.getStatusCode().value());
            log.info("===> Response body msg : {}", responseEntity.getBody().getMsg());
            log.info("===> Response body code : {}", responseEntity.getBody().getCode());
            log.info("===> Response body data : {}", responseEntity.getBody().getData());
            
            if (responseEntity!= null && responseEntity.getStatusCode() == HttpStatus.OK) {
                apiResponseVo.setCode(responseEntity.getBody().getCode());
                apiResponseVo.setMsg(responseEntity.getBody().getMsg());
                apiResponseVo.setData(responseEntity.getBody().getData());    
            }
        } catch (RestClientException | IOException e) {
            e.printStackTrace();
        }
        
        return apiResponseVo;
    }

    @Override
    public ApiResponseVo resetCredentials(String id, HashMap<String, Object> reqMap) {
        ApiResponseVo apiResponseVo = new ApiResponseVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl)
                    .path("/iam/user/{id}/resetCredentials")
                    .buildAndExpand(id)
                    .toString();
            log.info("===> Request Url : {}", url);
            
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(reqMap);
            log.info("===> Request Body : {}", requestBody);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<String> entity = new HttpEntity<String>(requestBody, headers); 
            
            ResponseEntity<ApiResponseVo> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, ApiResponseVo.class);
            
            log.info("===> Response status : {}", responseEntity.getStatusCode().value());
            log.info("===> Response body msg : {}", responseEntity.getBody().getMsg());
            log.info("===> Response body code : {}", responseEntity.getBody().getCode());
            log.info("===> Response body data : {}", responseEntity.getBody().getData());
            
            if (responseEntity!= null && responseEntity.getStatusCode() == HttpStatus.OK) {
                apiResponseVo.setCode(responseEntity.getBody().getCode());
                apiResponseVo.setMsg(responseEntity.getBody().getMsg());
                apiResponseVo.setData(responseEntity.getBody().getData());    
            }
        } catch (RestClientException | IOException e) {
            e.printStackTrace();
        }
        
        return apiResponseVo;
    }

    @Override
    public ApiResponseVo getClusterRoles() {
        ApiResponseVo apiResponseVo = new ApiResponseVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl)
                    .path("/iam/clusterRoles")
                    .buildAndExpand()
                    .toString();
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
    

}
