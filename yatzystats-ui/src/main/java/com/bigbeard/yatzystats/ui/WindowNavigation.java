package com.bigbeard.yatzystats.ui;

import com.bigbeard.yatzystats.ui.models.BaseUserModel;
import com.bigbeard.yatzystats.ui.models.CreateSheetsUserModel;
import com.bigbeard.yatzystats.ui.models.StatsSheetsUserModel;
import com.bigbeard.yatzystats.ui.scenes.createsheet.CreateSheetScene;
import com.bigbeard.yatzystats.ui.scenes.statistics.settings.GamemodeScene;
import com.bigbeard.yatzystats.ui.scenes.statistics.settings.GamesChoiceScene;
import com.bigbeard.yatzystats.ui.scenes.statistics.statsmod.ConfrontationsScene;
import com.bigbeard.yatzystats.ui.scenes.statistics.statsmod.StatsModScene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class WindowNavigation {

    private UiScene currentScene;
    private Stage stage;
    private BaseUserModel model;
    private Logger logger = LogManager.getLogger(WindowNavigation.class);


    public WindowNavigation(Stage stage){
        this.stage = stage;
        this.model = new StatsSheetsUserModel();
    }

    public void initApp() {
        //Charger la scène par défaut
        this.loadScene(UiSceneRole.STARTING_SCENE);
        stage.show();
    }

    public Stage getStage() {
        return stage;
    }

    public BaseUserModel getModel() {
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
                Parent parent = getFXMLComponents(this.currentScene, "fxml" + File.separator + "createsheet-view.fxml");
                this.currentScene.setParent(parent);
                this.model = new CreateSheetsUserModel();
                break;

            default:
                logger.warn("Problème de chargement de scène");
                break;
        }
        if(this.currentScene != null && lastScene != this.currentScene) this.stage.setScene(this.currentScene.getViewScene());
    }

    public Parent getFXMLComponents(UiScene scene, String fxmlPath) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlPath));
        fxmlLoader.setController(scene);
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        } catch (Exception ex) {
            logger.error(ex);
        }
        return parent;
    }
}
