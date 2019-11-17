package com.bigbeard.yatzystats.app;


import com.bigbeard.yatzystats.config.UserConfigurationModel;
import com.bigbeard.yatzystats.ui.UiScene;
import com.bigbeard.yatzystats.ui.WindowNavigation;
import com.bigbeard.yatzystats.ui.settings.GamemodeScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class ApplicationHMI extends Application {

    // https://www.tutorialspoint.com/javafx/javafx_text.htm
    @Override
    public void start(Stage primaryStage) throws Exception {

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
