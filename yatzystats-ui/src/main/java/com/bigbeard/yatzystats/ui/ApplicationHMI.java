package com.bigbeard.yatzystats.ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class ApplicationHMI extends Application {

    // https://www.tutorialspoint.com/javafx/javafx_text.htm
    @Override
    public void start(Stage primaryStage) {

        //Setting title to the scene
        primaryStage.setResizable(false);
        primaryStage.setTitle("Yatzy Companion");
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(300);

        WindowNavigation navigation = new WindowNavigation(primaryStage);
        navigation.initApp();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
