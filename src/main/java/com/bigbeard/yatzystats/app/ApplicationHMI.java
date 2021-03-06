package com.bigbeard.yatzystats.app;

import com.bigbeard.yatzystats.ui.WindowNavigation;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.log4j.PropertyConfigurator;

public class ApplicationHMI extends Application {

    // https://www.tutorialspoint.com/javafx/javafx_text.htm
    @Override
    public void start(Stage primaryStage) throws Exception {

        //Load Log4j
        PropertyConfigurator.configure("log4j.properties");

        //Setting title to the scene
        primaryStage.setTitle("Yatzy Statistics tool");
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(300);

        WindowNavigation navigation = new WindowNavigation(primaryStage);
        navigation.initApp();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
