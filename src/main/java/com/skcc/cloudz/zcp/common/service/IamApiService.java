package com.skcc.cloudz.zcp.common.service;

import java.util.Map;

public interface IamApiService {
    
    Map<String, Object> getUser(String username);

}
