package com.bigbeard.yatzystats.ui;

import com.bigbeard.yatzystats.config.UserConfigurationModel;
import com.bigbeard.yatzystats.ui.settings.GamemodeScene;
import com.bigbeard.yatzystats.ui.settings.GamesChoiceScene;
import com.bigbeard.yatzystats.ui.statsmod.ConfrontationsScene;
import com.bigbeard.yatzystats.ui.statsmod.StatsModScene;
import javafx.stage.Stage;

public class WindowNavigation {

    private UiScene currentScene;
    private Stage stage;
    private UserConfigurationModel model;

    public WindowNavigation(Stage stage){
        this.stage = stage;
        this.model = new UserConfigurationModel();
    }

    public void initApp(){
        //Charger la scène par défaut
        this.loadScene(UiSceneRole.GAME_MODE_SCENE);
        stage.show();
    }

    public Stage getStage() {
        return stage;
    }

    public UserConfigurationModel getModel() {
        return model;
    }

    protected void loadScene(UiSceneRole role){
        UiScene lastScene = this.currentScene;
        switch (role){
            case GAME_MODE_SCENE:
                this.currentScene = new GamemodeScene(this);
                break;

            case GAMES_CHOICE_SCENE:
                this.currentScene = new GamesChoiceScene(this);
                break;

            case STATS_MODE_SCENE:
                this.currentScene = new StatsModScene(this);
                break;

            case CONFRONTATIONS_SCENE:
                this.currentScene = new ConfrontationsScene(this);
                break;

            default:
                System.out.println("PRoblème de chargement de scène");
                break;
        }
        if(this.currentScene != null && lastScene != this.currentScene) this.stage.setScene(this.currentScene.getViewScene());
    }
}
