package com.skcc.cloudz.zcp.api.iam.service;

import java.util.HashMap;

import com.skcc.cloudz.zcp.api.iam.domain.vo.ApiResponseVo;
import com.skcc.cloudz.zcp.api.iam.domain.vo.ZcpNodeListVo;
import com.skcc.cloudz.zcp.api.iam.domain.vo.ZcpUserListVo;
import com.skcc.cloudz.zcp.api.iam.domain.vo.ZcpUserResVo;

public interface IamApiService {
    
    ZcpUserResVo getUser(String id);
    
    ApiResponseVo setUser(HashMap<String, Object> reqMap);
    
    ApiResponseVo updateUser(String id, HashMap<String, Object> reqMap);
    
    ApiResponseVo deleteUser(String id);
    
    ApiResponseVo updatePassword(String id, HashMap<String, Object> reqMap); 
    
    ApiResponseVo logout(String id);
    
    ApiResponseVo kubeconfig(String id, String namespace);
    
    ApiResponseVo serviceAccount(String id);
    
    ZcpUserListVo getUsers(String keyword);
    
    ApiResponseVo resetPassword(String id, HashMap<String, Object> reqMap);
    
    ApiResponseVo resetCredentials(String id, HashMap<String, Object> reqMap);
    
    ApiResponseVo getClusterRoles();
    
    ApiResponseVo updateClusterRoleBinding(String id, HashMap<String, Object> reqMap);
    
    ApiResponseVo getRoleBindings(String id);
    
    ApiResponseVo getNamespaces();
    
    ApiResponseVo createRoleBinding(String namespace, HashMap<String, Object> reqMap);
    
    ApiResponseVo updateRoleBinding(String namespace, HashMap<String, Object> reqMap);
    
    ApiResponseVo deleteRoleBinding(String namespace, HashMap<String, Object> reqMap);
    
    ApiResponseVo updateOtpPassword(String id);
    
    ApiResponseVo deleteOtpPassword(String id);
    
    ZcpNodeListVo getNodes();
    
    ApiResponseVo getDeploymentsStatus(String namespace);
    
    ApiResponseVo getNodesStatus(String namespace);
    
    ApiResponseVo getPodsStatus(String namespace);
    
    ApiResponseVo getCpuStatus();
    
    ApiResponseVo getMemoryStatus();

}
