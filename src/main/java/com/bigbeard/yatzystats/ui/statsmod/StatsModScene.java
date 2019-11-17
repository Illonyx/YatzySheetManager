package com.bigbeard.yatzystats.ui.statsmod;

import com.bigbeard.yatzystats.config.UserConfigurationModel;
import com.bigbeard.yatzystats.ui.UiScene;
import com.bigbeard.yatzystats.ui.UiSceneRole;
import com.bigbeard.yatzystats.ui.WindowNavigation;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StatsModScene extends UiScene {

    private Stage stage;
    private UserConfigurationModel model;
    private GridPane gridPane;

    public StatsModScene(WindowNavigation navigation){
        super(navigation, UiSceneRole.STATS_MODE_SCENE);
        this.initComponents();
    }

    private void initComponents() {
        this.gridPane = new GridPane();
        this.gridPane.setMinSize(super.getStage().getMinWidth(),super.getStage().getMinHeight());
        this.gridPane.setPadding(new Insets(20));
        this.gridPane.setHgap(25);
        this.gridPane.setVgap(15);

        Text text = new Text("Stats Mod Scene");
        this.gridPane.add(text, 0,0,2,2);
    }

    @Override
    public Scene getViewScene() {
        return new Scene(gridPane, super.getStage().getMinWidth(),super.getStage().getMinHeight());
    }

    @Override
    public boolean isViewValid() {
        return false;
    }
}
