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
import com.skcc.cloudz.zcp.api.iam.domain.vo.ZcpNodeListVo;
import com.skcc.cloudz.zcp.api.iam.domain.vo.ZcpUserListVo;
import com.skcc.cloudz.zcp.api.iam.domain.vo.ZcpUserResVo;
import com.skcc.cloudz.zcp.api.iam.service.IamApiService;

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
    public ApiResponseVo updatePassword(String id, HashMap<String, Object> reqMap) {
        ApiResponseVo apiResponseVo = new ApiResponseVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl)
                    .path("/iam/user/{id}/password")
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
    public ApiResponseVo kubeconfig(String id, String namespace) {
        ApiResponseVo apiResponseVo = new ApiResponseVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl)
                    .path("/iam/user/{id}/kubeconfig")
                    .queryParam("namespace", namespace)
                    .buildAndExpand(id)
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
    public ZcpUserListVo getUsers(String keyword) {
        ZcpUserListVo zcpUserListVo = new ZcpUserListVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl)
                    .path("/iam/users")
                    .queryParam("keyword", keyword)
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

    @Override
    public ApiResponseVo updateClusterRoleBinding(String id, HashMap<String, Object> reqMap) {
        ApiResponseVo apiResponseVo = new ApiResponseVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl)
                    .path("/iam/user/{id}/clusterRoleBinding")
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
        } catch (RestClientException | IOException e) {
            e.printStackTrace();
        } 
        
        return apiResponseVo;
    }

    @Override
    public ApiResponseVo getRoleBindings(String id) {
        ApiResponseVo apiResponseVo = new ApiResponseVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl)
                    .path("/iam/user/{id}/roleBindings")
                    .buildAndExpand(id)
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
    public ApiResponseVo getNamespaces() {
        ApiResponseVo apiResponseVo = new ApiResponseVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl)
                    .path("/iam/namespaces")
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

    @Override
    public ApiResponseVo createRoleBinding(String namespace, HashMap<String, Object> reqMap) {
        ApiResponseVo apiResponseVo = new ApiResponseVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl)
                    .path("/iam/namespace/{namespace}/roleBinding")
                    .buildAndExpand(namespace)
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
    public ApiResponseVo updateRoleBinding(String namespace, HashMap<String, Object> reqMap) {
        ApiResponseVo apiResponseVo = new ApiResponseVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl)
                    .path("/iam/namespace/{namespace}/roleBinding")
                    .buildAndExpand(namespace)
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
    public ApiResponseVo deleteRoleBinding(String namespace, HashMap<String, Object> reqMap) {
        ApiResponseVo apiResponseVo = new ApiResponseVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl)
                    .path("/iam/namespace/{namespace}/roleBinding")
                    .buildAndExpand(namespace)
                    .toString();
            log.info("===> Request Url : {}", url);
            
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(reqMap);
            log.info("===> Request Body : {}", requestBody);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<String> entity = new HttpEntity<String>(requestBody, headers); 
            
            ResponseEntity<ApiResponseVo> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, entity, ApiResponseVo.class);
            
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
    public ApiResponseVo updateOtpPassword(String id) {
        ApiResponseVo apiResponseVo = new ApiResponseVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl)
                    .path("/iam/user/{id}/otpPassword")
                    .buildAndExpand(id)
                    .toString();
            log.info("===> Request Url : {}", url);
            
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> requestEntity = new HttpEntity<String>(headers); 
            
            ResponseEntity<ApiResponseVo> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, ApiResponseVo.class);
            
            log.info("===> Response status : {}", responseEntity.getStatusCode().value());
            log.info("===> Response body msg : {}", responseEntity.getBody().getMsg());
            log.info("===> Response body code : {}", responseEntity.getBody().getCode());
            log.info("===> Response body data : {}", responseEntity.getBody().getData());
            
            apiResponseVo.setCode(responseEntity.getBody().getCode());
            apiResponseVo.setMsg(responseEntity.getBody().getMsg());
            apiResponseVo.setData(responseEntity.getBody().getData());
        } catch (RestClientException e) {
            e.printStackTrace();
        } 
        
        return apiResponseVo;
    }

    @Override
    public ApiResponseVo deleteOtpPassword(String id) {
        ApiResponseVo apiResponseVo = new ApiResponseVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl)
                    .path("/iam/user/{id}/otpPassword")
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
            
            apiResponseVo.setCode(responseEntity.getBody().getCode());
            apiResponseVo.setMsg(responseEntity.getBody().getMsg());
            apiResponseVo.setData(responseEntity.getBody().getData());
        } catch (RestClientException e) {
            e.printStackTrace();
        } 
        
        return apiResponseVo;
    }

    @Override
    public ZcpNodeListVo getNodes() {
        ZcpNodeListVo zcpNodeListVo = new ZcpNodeListVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl)
                    .path("/iam/metrics/nodes")
                    .buildAndExpand()
                    .toString();
            log.info("===> Request Url : {}", url);
            
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> requestEntity = new HttpEntity<String>(headers); 
            
            ResponseEntity<ZcpNodeListVo> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, ZcpNodeListVo.class);
            
            log.info("===> Response status : {}", responseEntity.getStatusCode().value());
            log.info("===> Response body msg : {}", responseEntity.getBody().getMsg());
            log.info("===> Response body code : {}", responseEntity.getBody().getCode());
            log.info("===> Response body data : {}", responseEntity.getBody().getData());
            
            if (responseEntity!= null && responseEntity.getStatusCode() == HttpStatus.OK) {
                zcpNodeListVo.setCode(responseEntity.getBody().getCode());
                zcpNodeListVo.setMsg(responseEntity.getBody().getMsg());
                zcpNodeListVo.setData(responseEntity.getBody().getData());    
            }
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        
        return zcpNodeListVo;
        
    }

    @Override
    public ApiResponseVo getDeploymentsStatus(String namespace) {
        ApiResponseVo apiResponseVo = new ApiResponseVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl)
                    .path("/iam/metrics/deployments/status")
                    .queryParam("namespace", namespace)
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

    @Override
    public ApiResponseVo getNodesStatus(String namespace) {
        ApiResponseVo apiResponseVo = new ApiResponseVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl)
                    .path("/iam/metrics/nodes/status")
                    .queryParam("namespace", namespace)
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

    @Override
    public ApiResponseVo getPodsStatus(String namespace) {
        ApiResponseVo apiResponseVo = new ApiResponseVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl)
                    .path("/iam/metrics/pods/status")
                    .queryParam("namespace", namespace)
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

    @Override
    public ApiResponseVo getClusterStatus(String type) {
        ApiResponseVo apiResponseVo = new ApiResponseVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl)
                    .path("/iam/metrics/cluster/{type}/status/")
                    .buildAndExpand(type)
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
    public ApiResponseVo getUsersStatus(String namespace) {
        ApiResponseVo apiResponseVo = new ApiResponseVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl)
                    .path("/iam/metrics/users/status")
                    .queryParam("namespace", namespace)
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
