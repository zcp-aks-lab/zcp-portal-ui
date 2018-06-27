package com.skcc.cloudz.zcp.common.exception;

import com.skcc.cloudz.zcp.api.iam.domain.vo.ApiResponseVo;

public class ZcpPortalException extends Exception {

    private static final long serialVersionUID = 212128515021762929L;
    
    public ZcpPortalException(String message) {
        super(message);
    }
    
    public ZcpPortalException(ApiResponseVo apiResponseVo) {
        super(apiResponseVo.getMsg() != null ? apiResponseVo.getMsg() : "");
    }

}
