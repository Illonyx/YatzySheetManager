package com.bigbeard.yatzystats.ui.createsheet;

import com.bigbeard.yatzystats.ui.UiScene;
import com.bigbeard.yatzystats.ui.UiSceneRole;
import com.bigbeard.yatzystats.ui.WindowNavigation;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

import java.util.List;

public class CreateSheetScene extends UiScene {

    private GridPane gridPane;
    private List<String> players;

    public CreateSheetScene(WindowNavigation navigation) {
        super(navigation, UiSceneRole.CREATE_SHEET_SCENE);
        this.initComponents();
    }

    private void initComponents() {
        this.gridPane = this.getDefaultGridPaneConfig();
    }

    @Override
    public Scene getViewScene() {
        return new Scene(this.gridPane, super.getStage().getMinWidth(),super.getStage().getMinHeight());
    }

    @Override
    public boolean isViewValid() {
        return false;
    }
}
