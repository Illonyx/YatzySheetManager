package com.bigbeard.yatzystats.app;


import com.bigbeard.yatzystats.config.UserConfigurationModel;
import com.bigbeard.yatzystats.ui.UiScene;
import com.bigbeard.yatzystats.ui.settings.GamemodeScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class ApplicationHMI extends Application {

    // https://www.tutorialspoint.com/javafx/javafx_text.htm
    @Override
    public void start(Stage primaryStage) throws Exception {

        //Creating a Scene
        UiScene firstScene = new GamemodeScene(primaryStage, new UserConfigurationModel());

        //Setting title to the scene
        primaryStage.setTitle("Yatzy Statistics tool");

        //Adding the scene to the stage
        primaryStage.setScene(firstScene.getViewScene());

        //Displaying the contents of a scene
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
