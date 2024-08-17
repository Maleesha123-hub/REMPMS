package com.pdev.rempms.recruitmentservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FallBackHandlerException extends Exception{

    private static final long serialVersionUID = -5813841768213591498L;

    private final int errorCode;

    private final String errorDescription;

    private final Object data;


}
