package com.skcc.cloudz.zcp.common.exception;

import org.apache.commons.lang3.StringUtils;

import com.skcc.cloudz.zcp.api.iam.domain.vo.ApiResponseVo;

public class ZcpPortalException extends Exception {

    private static final long serialVersionUID = 212128515021762929L;
    
    final static String ERROR_MESSAGE = "처리 중 오류가 발생하였습니다.<br/>잠시 후 다시 이용해 주십시오.";
    
    private String errCd;
    private String errMsg;
    
    public ZcpPortalException() {
        super();
    }
    
    public ZcpPortalException(String errMsg) {
        super();
        this.errMsg = errMsg;
    }
    
    public ZcpPortalException(String errCd, String errMsg) {
        super(errMsg);
        this.errCd = errCd;
        this.errMsg = errMsg;
    }
    
    public ZcpPortalException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ZcpPortalException(ApiResponseVo apiResponseVo) {
        super();
        this.errCd = apiResponseVo.getCode();
        this.errMsg = apiResponseVo.getMsg();
    }
    
    public ZcpPortalException(Throwable cause) {
        super(cause);
    }

    public String getErrCd() {
        return errCd;
    }

    public void setErrCd(String errCd) {
        this.errCd = errCd;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
    
    public static String getExceptionMsg(Exception e) {
        String errCd = StringUtils.EMPTY;
        String errMsg = StringUtils.EMPTY;
        
        if (e instanceof ZcpPortalException) {
            ZcpPortalException zcpPortalException = (ZcpPortalException) e;
            
            errCd = !StringUtils.isEmpty(zcpPortalException.getErrCd()) ? " [".concat(zcpPortalException.getErrCd()) + "]" : "";
            errMsg = !StringUtils.isEmpty(zcpPortalException.getErrMsg()) ? zcpPortalException.getErrMsg() : ERROR_MESSAGE;    
        } else {
            errMsg = e.getMessage();
        }
        
        return errMsg.concat(errCd);
    }
    
}
