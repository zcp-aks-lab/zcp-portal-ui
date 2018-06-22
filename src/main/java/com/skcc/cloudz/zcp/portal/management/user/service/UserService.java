package com.skcc.cloudz.zcp.portal.management.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skcc.cloudz.zcp.api.iam.domain.vo.ApiResponseVo;
import com.skcc.cloudz.zcp.api.iam.domain.vo.ZcpUserListVo;
import com.skcc.cloudz.zcp.api.iam.domain.vo.ZcpUserResVo;
import com.skcc.cloudz.zcp.api.iam.domain.vo.ZcpUserVo;
import com.skcc.cloudz.zcp.api.iam.service.IamApiService;
import com.skcc.cloudz.zcp.common.constants.ApiResult;
import com.skcc.cloudz.zcp.portal.management.user.vo.UserVo;

@Service
public class UserService {
    
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    
    @Autowired
    private IamApiService iamApiService;

    public List<ZcpUserVo> getUsers(String keyword) throws Exception {
        ZcpUserListVo zcpUserListVo = iamApiService.getUsers(keyword);
        if (!zcpUserListVo.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new Exception(zcpUserListVo.getMsg());
        }
        
        return zcpUserListVo.getData().getItems();
    }
    
    public ZcpUserVo getUser(String id) throws Exception {
        ZcpUserResVo zcpUserResVo = iamApiService.getUser(id);
        if (!zcpUserResVo.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new Exception(zcpUserResVo.getMsg());
        }
        
        return zcpUserResVo.getData();
    }
    
    public void setUser(UserVo userVo) throws Exception {
        HashMap<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("email", userVo.getEmail());
        reqMap.put("firstName", userVo.getFirstName());
        reqMap.put("username", userVo.getUsername());
        reqMap.put("clusterRole", userVo.getClusterRole());
        
        ApiResponseVo apiResponseVo = iamApiService.setUser(reqMap);
        if (!apiResponseVo.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new Exception(apiResponseVo.getMsg());
        }
    }
    
    public void updateUser(UserVo userVo) throws Exception {
        HashMap<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("email", userVo.getEmail());
        reqMap.put("firstName", userVo.getFirstName());
        reqMap.put("enabled", userVo.getEnabled());
        reqMap.put("username", userVo.getUsername());
        
        ApiResponseVo apiResponseVo = iamApiService.updateUser(userVo.getId(), reqMap);
        if (!apiResponseVo.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new Exception(apiResponseVo.getMsg());
        }
    }
    
    public void deleteUser(String id) throws Exception {
        ApiResponseVo apiResponseVo = iamApiService.deleteUser(id);
        if (!apiResponseVo.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new Exception(apiResponseVo.getMsg());
        }
    }
    
    public void resetPassword(UserVo userVo) throws Exception {
        HashMap<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("password", userVo.getPassword());
        reqMap.put("temporary", userVo.getTemporary());
        
        ApiResponseVo apiResponseVo = iamApiService.resetPassword(userVo.getId(), reqMap);
        if (!apiResponseVo.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new Exception(apiResponseVo.getMsg());
        }
    }
    
    public void resetCredentials(UserVo userVo) throws Exception  {
        HashMap<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("period", Integer.parseInt(userVo.getPeriod()));
        reqMap.put("type", userVo.getType());
        reqMap.put("actions", userVo.getActions());
        
        ApiResponseVo apiResponseVo = iamApiService.resetCredentials(userVo.getId(), reqMap);
        if (!apiResponseVo.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new Exception(apiResponseVo.getMsg());
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<String> getClusterRoles() throws Exception {
        List<String> clusterRoles = new ArrayList<String>();
        
        ApiResponseVo apiResponseVo = iamApiService.getClusterRoles();
        if (!apiResponseVo.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new Exception(apiResponseVo.getMsg());
        }
        
        Map<String, Object> data = apiResponseVo.getData();
        List<HashMap<String, Object>> items = (List<HashMap<String, Object>>) data.get("items");
        
        for (HashMap<String, Object> item : items) {
            clusterRoles.add(((HashMap<String, Object>) item.get("metadata")).get("name").toString());
        }
        
        return clusterRoles;
    }
    
    public void updateClusterRoleBinding(UserVo userVo) throws Exception  {
        HashMap<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("clusterRole", userVo.getClusterRole());
        
        ApiResponseVo apiResponseVo = iamApiService.updateClusterRoleBinding(userVo.getId(), reqMap);
        if (!apiResponseVo.getCode().equals(ApiResult.SUCCESS.getCode())) {
            throw new Exception(apiResponseVo.getMsg());
        }
    }

}
