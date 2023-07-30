package com.bigbeard.yatzystats.ui.theming;

import javafx.scene.paint.Color;

//https://howtodoinjava.com/design-patterns/creational/builder-pattern-in-java/
public class UIButtonTheming implements Cloneable {

    private int fontSize;
    private UiFonts font;
    private Color textFill;
    private String fxBackgroundColor;

    public UIButtonTheming() {
        this.fontSize = 12;
        this.font = UiFonts.COPPERPLATE_GOTHIC_BOLD;
        this.textFill = Color.AQUA;
        this.fxBackgroundColor = "#ffffff";
    }

    public UIButtonTheming(int fontSize, UiFonts font, Color textFill, String fxBackgroundColor) {
        this.fontSize = fontSize;
        this.font = font;
        this.textFill = textFill;
        this.fxBackgroundColor = fxBackgroundColor;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public UiFonts getFont() {
        return font;
    }

    public void setFont(UiFonts font) {
        this.font = font;
    }

    public Color getTextFill() {
        return textFill;
    }

    public void setTextFill(Color textFill) {
        this.textFill = textFill;
    }

    public String getFxBackgroundColor() {
        return fxBackgroundColor;
    }

    public void setFxBackgroundColor(String fxBackgroundColor) {
        this.fxBackgroundColor = fxBackgroundColor;
    }

    public Object clone()
    {
        Object toReturn;
        try {
            toReturn = super.clone();
        } catch (CloneNotSupportedException cle){
            toReturn = new UIButtonTheming();
        }
        return toReturn;
    }
}
