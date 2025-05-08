package com.bigbeard.yatzystats.core.exceptions;

public class RulesNotLoadedException extends Exception {

    private final ErrorCode errorCode;

    public RulesNotLoadedException(ErrorCode errorCode, String content){
        super(content);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
