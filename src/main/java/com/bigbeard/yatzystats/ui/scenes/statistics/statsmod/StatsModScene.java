package com.bigbeard.yatzystats.ui.scenes.statistics.statsmod;

import com.bigbeard.yatzystats.ui.models.StatsSheetsUserModel;
import com.bigbeard.yatzystats.core.players.PlayerResult;
import com.bigbeard.yatzystats.core.players.StatsModule;
import com.bigbeard.yatzystats.ui.UiScene;
import com.bigbeard.yatzystats.ui.UiSceneRole;
import com.bigbeard.yatzystats.ui.WindowNavigation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

public class StatsModScene extends UiScene {

    private Stage stage;
    private StatsSheetsUserModel model;
    private GridPane gridPane;

    private ComboBox comboBox;
    private TextArea textArea;

    public StatsModScene(WindowNavigation navigation){
        super(navigation, UiSceneRole.STATS_MODE_SCENE);
        getModel().loadStats();
        this.initComponents();
    }

    private void initComponents() {
        this.gridPane = this.getDefaultGridPaneConfig();

        // Chargement du logo principal
        this.gridPane.setBackground(new Background(this.getBackgroundImage()));

        this.textArea = new TextArea();
        this.textArea.setEditable(false);
        this.gridPane.add(textArea, 3, 5 , 5, 5);

        //Switch confrontations button
        Button confrontationsButton = this.getWindowNavigationButton("Confrontations view",
                false, UiSceneRole.CONFRONTATIONS_SCENE);
        this.gridPane.add(confrontationsButton, 8, 2, 3,2);

        //Combobox
        this.comboBox = getPlayerCombobox();
        this.comboBox.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> updateTextarea(newValue));
        this.updateTextarea(getModel().getPlayerNames().get(0));
        this.gridPane.add(this.comboBox,3,2, 5,1);

        //Footer view
        this.gridPane.add(this.getLastSceneButton(), 1, 10, 3, 2);

    }

    private void updateTextarea(Object newValue) {
        List<PlayerResult> prs = getModel().getResultsPerPlayers().get(newValue);
        this.textArea.setText(this.showStats(prs));
    }

    private String showStats(List<PlayerResult> results){
        StringBuffer buff = new StringBuffer();

        buff.append("-- Score du joueur --" + System.lineSeparator());
        buff.append("Moyenne : " + StatsModule.getInstance().getMean(results) + System.lineSeparator());
        buff.append("Plus haut : " + StatsModule.getInstance().getHighestScore(results) + System.lineSeparator());
        buff.append("Plus bas : " + StatsModule.getInstance().getLowestScore(results) + System.lineSeparator());
        buff.append("Standard Deviation : " + StatsModule.getInstance().getStandardDeviation(results) + System.lineSeparator());

        buff.append("-- Combinaisons du joueur --" + System.lineSeparator());
        buff.append("Taux de victoires :" + StatsModule.getInstance().getWinRate(results) + System.lineSeparator());
        buff.append("Taux de yatzées :" + StatsModule.getInstance().getYatzyRate(results) + System.lineSeparator());
        buff.append("BonusRate :" + StatsModule.getInstance().getBonusRate(results) + System.lineSeparator());

        return buff.toString();
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
