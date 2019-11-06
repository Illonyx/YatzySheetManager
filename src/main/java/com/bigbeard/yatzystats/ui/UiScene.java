package com.bigbeard.yatzystats.ui;

import com.bigbeard.yatzystats.config.UserConfigurationModel;
import com.bigbeard.yatzystats.ui.settings.GamemodeScene;
import com.bigbeard.yatzystats.ui.settings.GamesChoiceScene;
import com.bigbeard.yatzystats.ui.statsmod.StatsModScene;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public abstract class UiScene {

    private Stage stage;
    private UserConfigurationModel model;
    private UiSceneRole role;

    public UiScene(Stage stage, UserConfigurationModel model, UiSceneRole role){
        this.stage = stage;
        this.model = model;
        this.role = role;
    }

    public abstract Scene getViewScene();

    protected void loadScene(UiSceneRole role){
        Scene sceneToLoad = stage.getScene();
        switch (role){
            case GAME_MODE_SCENE:
                sceneToLoad = new GamemodeScene(stage, model).getViewScene();
                break;

            case GAMES_CHOICE_SCENE:
                sceneToLoad = new GamesChoiceScene(stage, model).getViewScene();
                break;

            case STATS_MODE_SCENE:
                sceneToLoad = new StatsModScene(stage,model).getViewScene();
                break;
        }
        stage.setScene(sceneToLoad);
    }

    public UiSceneRole getNextScene(){
        int currentSceneOrder = this.role.getOrder();
        int nextSceneOrder = ++currentSceneOrder;
        if(nextSceneOrder >= UiSceneRole.values().length) return null;
        else return UiSceneRole.fromValue(nextSceneOrder);
    }

    public UiSceneRole getLastScene(){
        int currentSceneOrder = this.role.getOrder();
        int lastSceneOrder = --currentSceneOrder;
        if(lastSceneOrder < 0) return null;
        else return UiSceneRole.fromValue(lastSceneOrder);
    }

    public UserConfigurationModel getModel(){
        return model;
    }

    public Alert createErrorAlert(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert;
    }

    public Stage getStage(){
        return stage;
    }




}
