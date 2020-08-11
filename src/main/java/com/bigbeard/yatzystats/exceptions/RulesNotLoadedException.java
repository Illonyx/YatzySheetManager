package com.bigbeard.yatzystats.exceptions;

public class RulesNotLoadedException extends Exception {

    private String mainReason;

    public RulesNotLoadedException(String header, String content){
        super(content);
        this.mainReason=header;
    }

    public String getMainReason() {
        return mainReason;
    }

}
