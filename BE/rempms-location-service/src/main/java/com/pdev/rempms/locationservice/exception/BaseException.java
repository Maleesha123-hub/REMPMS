package com.pdev.rempms.locationservice.exception;

import lombok.Data;

@Data
public class BaseException extends RuntimeException{

    private static final long serialVersionUID = -5813841768213591498L;

    private final int errorCode;

    private final String errorDescription;

    public BaseException(int errorCode, Throwable cause, String errorDescription){
        super(cause);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public BaseException(int errorCode, String errorDescription){
        super(errorDescription);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public BaseException(int errorCode, String errorDescription, Throwable cause){
        super(errorDescription, cause);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }
}
