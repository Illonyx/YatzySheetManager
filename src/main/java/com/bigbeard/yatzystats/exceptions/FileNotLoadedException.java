package com.bigbeard.yatzystats.exceptions;

public class FileNotLoadedException extends Exception {

    private String mainReason;

    public FileNotLoadedException(String header, String content){
        super(content);
        this.mainReason=header;
    }

    public String getMainReason() {
        return mainReason;
    }
}
