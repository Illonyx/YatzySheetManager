package com.bigbeard.yatzystats.ui.theming;

public enum UiFonts {
    COPPERPLATE_GOTHIC_BOLD("Copperplate Gothic Bold");

    private String name;

    UiFonts(String fontName){
        this.name = fontName;
    }

    public String getName(){
        return this.name;
    }
}
