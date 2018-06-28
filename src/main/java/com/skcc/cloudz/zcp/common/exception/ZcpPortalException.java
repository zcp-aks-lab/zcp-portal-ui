package com.skcc.cloudz.zcp.common.exception;

import org.apache.commons.lang3.StringUtils;

import com.skcc.cloudz.zcp.api.iam.domain.vo.ApiResponseVo;

public class ZcpPortalException extends Exception {

    private static final long serialVersionUID = 212128515021762929L;
    
    final static String ERROR_MESSAGE = "처리 중 오류가 발생하였습니다. 잠시 후 다시 시도하세요.";
    
    public ZcpPortalException(String message) {
        super(message);
    }
    
    public ZcpPortalException(ApiResponseVo apiResponseVo) {
        super(ERROR_MESSAGE.concat(!StringUtils.isEmpty(apiResponseVo.getCode()) ? "[".concat(apiResponseVo.getCode()).concat("]") : ""));
    }

}
