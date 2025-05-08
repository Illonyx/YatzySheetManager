package com.bigbeard.yatzystats.ui;

import com.bigbeard.yatzystats.ui.theming.UIButtonTheming;
import com.bigbeard.yatzystats.ui.theming.UITheming;
import com.bigbeard.yatzystats.ui.theming.UiFonts;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class StartingScene extends UiScene {

    private GridPane gridPane;
    private StackPane stackPane;
    private Text text;
    private Button goToCreationScenarioButton, goToStatisticsModButton, goToSettingsButton;

    public StartingScene(WindowNavigation navigation){
        super(navigation, UiSceneRole.STARTING_SCENE);
        this.initComponents();
    }

    private void initComponents() {
        this.gridPane = new GridPane();
        gridPane.setPrefSize(this.getStage().getMinWidth(), this.getStage().getMinHeight());

        // Chargement du logo principal
        URL resourceUrl = getClass().getClassLoader().getResource("icon/logoyatzycompanion.png");
        Image image = new Image("file:" + resourceUrl.getPath());
        BackgroundSize size = new BackgroundSize(this.getStage().getWidth(), this.getStage().getHeight(),
                false, false, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.ROUND, BackgroundPosition.DEFAULT, size);
        this.gridPane.setBackground(new Background(backgroundImage));

        // Define column constraints (3 columns)
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(40);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(30);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(30);
        gridPane.getColumnConstraints().addAll(col1, col2, col3);

        // Define row constraints (3 rows)
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(33);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(34);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(33);
        gridPane.getRowConstraints().addAll(row1, row2, row3);

        UIButtonTheming uiButtonTheming = new UIButtonTheming(20, UiFonts.COPPERPLATE_GOTHIC_BOLD,Color.ANTIQUEWHITE,"#FF4500");
        this.createScenarioButton(uiButtonTheming);
        this.createStatsButton(uiButtonTheming);

        // Create the button for the bottom left
        GridPane.setConstraints(this.goToCreationScenarioButton, 0, 2);
        GridPane.setHalignment(this.goToCreationScenarioButton, javafx.geometry.HPos.LEFT);
        GridPane.setValignment(this.goToCreationScenarioButton, javafx.geometry.VPos.BOTTOM);
        GridPane.setMargin(this.goToCreationScenarioButton, new Insets(30));

        // Create the button for the bottom center
        GridPane.setConstraints(this.goToStatisticsModButton, 1, 2);
        GridPane.setHalignment(this.goToStatisticsModButton, javafx.geometry.HPos.CENTER);
        GridPane.setValignment(this.goToStatisticsModButton, javafx.geometry.VPos.BOTTOM);
        GridPane.setMargin(this.goToStatisticsModButton, new Insets(30));

        // Add buttons to the grid
        gridPane.getChildren().addAll(this.goToCreationScenarioButton, this.goToStatisticsModButton);

        this.createSettingsButton(uiButtonTheming);
    }

    private void createScenarioButton(UIButtonTheming uiButtonTheming) {
        String sheetCreationLabel = this.getModel().getResourceBundle().getString("menu.sheetcreation.button");
        this.goToCreationScenarioButton =
                this.getWindowNavigationButton(sheetCreationLabel, false, UiSceneRole.CREATE_SHEET_SCENE);
        UITheming.getInstance().applyThemingForButton(this.goToCreationScenarioButton, (UIButtonTheming) uiButtonTheming.clone());
    }


    private void createStatsButton(UIButtonTheming uiButtonTheming) {
        String statsLabel = this.getModel().getResourceBundle().getString("menu.statistics.button");
        this.goToStatisticsModButton =
                this.getWindowNavigationButton(statsLabel, false, UiSceneRole.GAME_MODE_SCENE);
        UIButtonTheming uiButtonTheming1 = (UIButtonTheming) uiButtonTheming.clone();
        uiButtonTheming1.setFxBackgroundColor("#000000");
        UITheming.getInstance().applyThemingForButton(this.goToStatisticsModButton, uiButtonTheming1);
    }

    private void createSettingsButton(UIButtonTheming uiButtonTheming) {
        this.goToSettingsButton = new Button();
        UIButtonTheming uiButtonTheming1 = (UIButtonTheming) uiButtonTheming.clone();
        uiButtonTheming1.setFxBackgroundColor("#FF4500");
        UITheming.getInstance().applyThemingForButton(this.goToSettingsButton, uiButtonTheming1);

        this.goToSettingsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                StartingScene.super.windowNavigation.loadScene(UiSceneRole.SETTINGS_SCENE);
            }
        });

        URL resourceUrl = getClass().getClassLoader().getResource("icon/settings.png");
        assert resourceUrl != null;
        Image image = new Image("file:" + resourceUrl.getPath());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20.0);
        imageView.setFitWidth(20.0);
        this.goToSettingsButton.setGraphic(imageView);

        // Position the button to top-right without HBox
        StackPane.setAlignment(this.goToSettingsButton, Pos.TOP_RIGHT);
        StackPane.setMargin(this.goToSettingsButton, new Insets(20));

        // Add the HBox to a StackPane to allow overlaying on the GridPane
        this.stackPane = new StackPane(gridPane, this.goToSettingsButton);
    }

    @Override
    public Scene getViewScene() {
        return new Scene(this.stackPane, super.getStage().getMinWidth(),super.getStage().getMinHeight());
    }

    @Override
    public boolean isViewValid() {
        return true;
    }
}
