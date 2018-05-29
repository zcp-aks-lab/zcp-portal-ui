package com.skcc.cloudz.zcp.service.impl;

import org.springframework.stereotype.Service;

import com.skcc.cloudz.zcp.service.MyService;

@Service
public class MyServiceImpl implements MyService {

    @Override
    public String getMyInfoById() {
        return "test";
    }

    
}
