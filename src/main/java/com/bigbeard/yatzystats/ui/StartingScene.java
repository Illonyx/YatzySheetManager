package com.bigbeard.yatzystats.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class StartingScene extends UiScene {

    private GridPane gridPane;
    private Text text;
    private Button goToCreationScenarioButton, goToStatisticsModButton;

    public StartingScene(WindowNavigation navigation){
        super(navigation, UiSceneRole.STARTING_SCENE);
        this.initComponents();
    }

    private void initComponents() {
        this.gridPane = this.getDefaultGridPaneConfig();
        this.text = new Text("Bienvenue dans le Yatzy Companion");
        this.gridPane.add(this.text, 0,0,7,2);

        //Button validation
        this.goToCreationScenarioButton =
                this.getWindowNavigationButton("Aller vers le module Creation de fichiers", false, UiSceneRole.CREATE_SHEET_SCENE);
        this.gridPane.add(this.goToCreationScenarioButton, 5,5,3,1);

        this.goToStatisticsModButton =
                this.getWindowNavigationButton("Aller vers le module Statistiques", false, UiSceneRole.GAME_MODE_SCENE);
        this.gridPane.add(this.goToStatisticsModButton, 5,8,3,1);
    }

    @Override
    public Scene getViewScene() {
        return new Scene(this.gridPane, super.getStage().getMinWidth(),super.getStage().getMinHeight());
    }

    @Override
    public boolean isViewValid() {
        return true;
    }
}
