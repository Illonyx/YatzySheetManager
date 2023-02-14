package com.bigbeard.yatzystats.ui;

import com.bigbeard.yatzystats.ui.theming.UIButtonTheming;
import com.bigbeard.yatzystats.ui.theming.UITheming;
import com.bigbeard.yatzystats.ui.theming.UiFonts;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;

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

        // Chargement du logo principal
        URL resourceUrl = getClass().getClassLoader().getResource("icon/logoyatzycompanion.png");
        Image image = new Image("file:" + resourceUrl.getPath());
        BackgroundSize size = new BackgroundSize(this.getStage().getWidth(), this.getStage().getHeight(),
                false, false, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.ROUND, BackgroundPosition.DEFAULT, size);
        this.gridPane.setBackground(new Background(backgroundImage));

        //Button validation
        UIButtonTheming uiButtonTheming = new UIButtonTheming(20, UiFonts.COPPERPLATE_GOTHIC_BOLD,Color.ANTIQUEWHITE,"#FF4500");

        this.goToCreationScenarioButton =
                this.getWindowNavigationButton("Création de feuilles", false, UiSceneRole.CREATE_SHEET_SCENE);
        UITheming.getInstance().applyThemingForButton(this.goToCreationScenarioButton, (UIButtonTheming) uiButtonTheming.clone());
        this.gridPane.add(this.goToCreationScenarioButton, 1,14,5,1);

        this.goToStatisticsModButton =
                this.getWindowNavigationButton("Statistiques", false, UiSceneRole.GAME_MODE_SCENE);
        UIButtonTheming uiButtonTheming1 = (UIButtonTheming) uiButtonTheming.clone();
        uiButtonTheming1.setFxBackgroundColor("#000000");
        UITheming.getInstance().applyThemingForButton(this.goToStatisticsModButton, uiButtonTheming1);
        this.gridPane.add(this.goToStatisticsModButton, 8,14,5,1);
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
