package com.skcc.cloudz.zcp.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidRequestException() {
        super("Bad Request");
    }

    public InvalidRequestException(String message) {
        super(message);
    }
}
