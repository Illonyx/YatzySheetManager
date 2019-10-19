package com.bigbeard.yatzystats.ui.settings.gamemode;

import com.bigbeard.yatzystats.config.UserConfigurationModel;
import com.bigbeard.yatzystats.ui.settings.UiScene;
import com.bigbeard.yatzystats.ui.settings.UiSceneRole;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import org.apache.commons.compress.utils.Lists;

import java.util.Arrays;

public class GamesChoiceScene extends UiScene {

    private Stage stage;
    private UserConfigurationModel model;

    public GamesChoiceScene(Stage stage, UserConfigurationModel model){
        super(stage, model, UiSceneRole.GAMES_CHOICE_SCENE);
    }

    @Override
    public Scene getViewScene() {

        System.out.println("ld" + super.getModel().getFoundSheets().size());
        //Creating a line object
        Line line = new Line();

        //Setting the properties to a line
        line.setStartX(100.0);
        line.setStartY(150.0);
        line.setEndX(500.0);
        line.setEndY(150.0);

        //Creating a Group
        Group root = new Group(line);

        return new Scene(root, 600,300);
    }
}

