package com.bigbeard.yatzystats.app;


import com.bigbeard.yatzystats.config.UserConfigurationModel;
import com.bigbeard.yatzystats.ui.settings.gamemode.GamemodeScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class ApplicationHMI extends Application {

    //https://www.tutorialspoint.com/javafx/javafx_text.htm
    @Override
    public void start(Stage primaryStage) throws Exception {
//        //Creating a line object
//        Line line = new Line();
//
//        //Setting the properties to a line
//        line.setStartX(100.0);
//        line.setStartY(150.0);
//        line.setEndX(500.0);
//        line.setEndY(150.0);
//
//        //Creating a Group
//        Group root = new Group(line);



        //Creating a Scene
        GamemodeScene gamemodeScene = new GamemodeScene(primaryStage, new UserConfigurationModel());

        //Setting title to the scene
        primaryStage.setTitle("Yatzy Statistics tool");

        //Adding the scene to the stage
        primaryStage.setScene(gamemodeScene.getViewScene());

        //Displaying the contents of a scene
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
