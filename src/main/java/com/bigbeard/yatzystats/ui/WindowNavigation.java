package com.bigbeard.yatzystats.ui;

import com.bigbeard.yatzystats.config.UserConfigurationModel;
import com.bigbeard.yatzystats.ui.createsheet.CreateSheetScene;
import com.bigbeard.yatzystats.ui.statistics.settings.GamemodeScene;
import com.bigbeard.yatzystats.ui.statistics.settings.GamesChoiceScene;
import com.bigbeard.yatzystats.ui.statistics.statsmod.ConfrontationsScene;
import com.bigbeard.yatzystats.ui.statistics.statsmod.StatsModScene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

public class WindowNavigation {

    private UiScene currentScene;
    private Stage stage;
    private UserConfigurationModel model;
    private Logger logger = Logger.getLogger(WindowNavigation.class);


    public WindowNavigation(Stage stage){
        this.stage = stage;
        this.model = new UserConfigurationModel();
    }

    public void initApp() {
        //Charger la scène par défaut
        this.loadScene(UiSceneRole.STARTING_SCENE);
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

            case STARTING_SCENE:
                this.currentScene = new StartingScene(this);
                break;

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

            case CREATE_SHEET_SCENE:
                this.currentScene = new CreateSheetScene(this);
                break;

            default:
                logger.warn("Problème de chargement de scène");
                break;
        }
        if(this.currentScene != null && lastScene != this.currentScene) this.stage.setScene(this.currentScene.getViewScene());
    }
}
