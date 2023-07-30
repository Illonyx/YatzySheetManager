package com.bigbeard.yatzystats.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.LogManager;

import java.io.File;

public class ApplicationHMI extends Application {

    // https://www.tutorialspoint.com/javafx/javafx_text.htm
    @Override
    public void start(Stage primaryStage) throws Exception {

        //Load Log4j
        LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
        File file = new File("log4j.properties");
        context.setConfigLocation(file.toURI());

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
