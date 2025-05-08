package com.bigbeard.yatzystats.core.exceptions;

public class FileNotLoadedException extends Exception {

    private final ErrorCode errorCode;

    public FileNotLoadedException(ErrorCode errorCode, String content) {
        super(content);
        this.errorCode=errorCode;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
}
