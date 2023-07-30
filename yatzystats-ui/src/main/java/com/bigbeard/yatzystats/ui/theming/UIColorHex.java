package com.bigbeard.yatzystats.ui.theming;

public enum UIColorHex {
    ANTIQUE_WHITE("d0"),
    BLUE("ff");

    private String hexValue;

    UIColorHex(String value){
        this.hexValue = value;
    }

    public String getColorValue(){
        return this.hexValue;
    }
}
