package com.bigbeard.yatzystats.ui.theming;

import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class UITheming {

    private static UITheming instance = new UITheming();

    private UITheming() {

    }

    public static UITheming getInstance() {
        return instance;
    }

    public void applyThemingForButton(Button button, UIButtonTheming buttonTheming) {
        button.setFont(Font.font(buttonTheming.getFont().getName(), buttonTheming.getFontSize()));
        button.setTextFill(buttonTheming.getTextFill());
        button.setStyle("-fx-background-color: ".concat(buttonTheming.getFxBackgroundColor()));
    }

    public void applyTextTheming(Text text, UIButtonTheming theming) {
        text.setFont(Font.font(theming.getFont().getName(), theming.getFontSize()));
        //Setting the color
        text.setFill(Color.ANTIQUEWHITE);
        //text.setStrokeWidth(2);
        //text.setStroke(Color.BLUE);
    }
}
