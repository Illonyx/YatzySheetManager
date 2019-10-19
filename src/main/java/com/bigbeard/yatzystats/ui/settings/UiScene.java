package com.bigbeard.yatzystats.ui.settings;

import com.bigbeard.yatzystats.config.UserConfigurationModel;
import com.bigbeard.yatzystats.ui.settings.gamemode.GamemodeScene;
import com.bigbeard.yatzystats.ui.settings.gamemode.GamesChoiceScene;
import javafx.scene.Scene;
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
        }
        stage.setScene(sceneToLoad);
    }

    public UiSceneRole getNextScene(){
        int currentSceneOrder = this.role.getOrder();
        int nextSceneOrder = ++currentSceneOrder;
        if(nextSceneOrder >= UiSceneRole.values().length) return null;
        else return UiSceneRole.fromValue(nextSceneOrder);
    }

    public UserConfigurationModel getModel(){
        return model;
    }





}
