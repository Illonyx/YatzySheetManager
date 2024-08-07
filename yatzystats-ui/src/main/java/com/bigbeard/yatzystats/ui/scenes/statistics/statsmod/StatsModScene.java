package com.bigbeard.yatzystats.ui.scenes.statistics.statsmod;

import com.bigbeard.yatzystats.core.model.players.ConfrontationDTO;
import com.bigbeard.yatzystats.core.model.players.PlayerResult;
import com.bigbeard.yatzystats.core.model.players.StatsModule;
import com.bigbeard.yatzystats.ui.UiScene;
import com.bigbeard.yatzystats.ui.models.StatsSheetsUserModel;
import com.bigbeard.yatzystats.ui.UiSceneRole;
import com.bigbeard.yatzystats.ui.WindowNavigation;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;
import java.util.List;

public class StatsModScene extends UiScene {

    private Logger logger = LogManager.getLogger(StatsModScene.class);

    private Stage stage;
    private StatsSheetsUserModel model;

    @FXML
    TabPane tabsPane;

    @FXML
    SplitPane statsSplitPane;

    @FXML
    Button goBackButton;

    @FXML
    ComboBox<String> playersCombobox;

    @FXML
    TextArea statsArea;

    @FXML
    ComboBox<String> player1Combobox;

    @FXML
    ComboBox<String> player2Combobox;

    @FXML
    Button duelButton;

    @FXML
    PieChart piechartDuel;

    @FXML
    TextArea textareaDuel;

    @FXML
    Tab statsTab;

    @FXML
    SplitPane duelSplitPane;

    public StatsModScene(WindowNavigation navigation){
        super(navigation, UiSceneRole.STATS_MODE_SCENE);
        this.model = getModel().getStatsSheetsUserModel();
        model.loadStats();
    }

    private void initComponents() {
        tabsPane.setBackground(new Background(this.getBackgroundImage()));
        // Chargement du logo principal
        this.initStatsTab();
        this.initConfrontationsTab();
    }

    private void initStatsTab() {
        // bouton goback
        this.setButtonForWindowNavigation(goBackButton, false, UiSceneRole.GAMES_CHOICE_SCENE);

        //Combobox joueurs vue principale
        playersCombobox.setItems(FXCollections.observableList(model.getPlayerNames()));
        playersCombobox.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> updateTextarea(newValue));
        playersCombobox.setValue(model.getPlayerNames().get(0));
        this.updateTextarea(model.getPlayerNames().get(0));
    }

    private void initConfrontationsTab() {
        //Combobox joueur 1 vue confrontations
        player1Combobox.setItems(FXCollections.observableList(model.getPlayerNames()));
        player1Combobox.setValue(model.getPlayerNames().get(0));

        //Combobox joueur 2 vue confrontations
        player2Combobox.setItems(FXCollections.observableList(model.getPlayerNames()));
        player2Combobox.setValue(model.getPlayerNames().get(1));

        duelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                generateConfrontations();
            }
        });
    }

    private void updateTextarea(Object newValue) {
        List<PlayerResult> prs = model.getResultsPerPlayers().get(newValue);
        statsArea.setText(this.showStats(prs));
    }

    private String showStats(List<PlayerResult> results){
        List<String> top5high = StatsModule.getInstance().getHighestScores(5,results);
        StringBuilder buff = new StringBuilder();

        buff.append("-- Score du joueur --").append(System.lineSeparator());
        buff.append("Moyenne : ").append(StatsModule.getInstance().getMean(results)).append(System.lineSeparator());
        buff.append("Plus haut : ").append(StatsModule.getInstance().getHighestScore(results)).append(System.lineSeparator());
        buff.append("Plus bas : ").append(StatsModule.getInstance().getLowestScore(results)).append(System.lineSeparator());
        buff.append("Standard Deviation : ").append(StatsModule.getInstance().getStandardDeviation(results)).append(System.lineSeparator());

        buff.append("-- Combinaisons du joueur --").append(System.lineSeparator());
        buff.append("Taux de victoires :").append(StatsModule.getInstance().getWinRate(results)).append(System.lineSeparator());
        buff.append("Taux de yatzées :").append(StatsModule.getInstance().getYatzyRate(results)).append(System.lineSeparator());
        buff.append("BonusRate :").append(StatsModule.getInstance().getBonusRate(results)).append(System.lineSeparator());
        buff.append("Top 5 scores :").append(String.join(",", top5high)).append(System.lineSeparator());

        return buff.toString();
    }

    public void generateConfrontations(){
        String firstPlayer = (String) this.player1Combobox.getValue();
        String secondPlayer = (String) this.player2Combobox.getValue();
        if(firstPlayer != null && secondPlayer != null && !firstPlayer.equals(secondPlayer)){
            logger.debug("Génération de la confrontation entre " + firstPlayer + " et " + secondPlayer);
            List<ConfrontationDTO> confrontations = model.makeConfrontations(model.getSelectedSheets(), firstPlayer, secondPlayer);

            PieChart.Data slice1 = getConfrontationsPieChart(confrontations, firstPlayer);
            PieChart.Data slice2 = getConfrontationsPieChart(confrontations, secondPlayer);
            this.piechartDuel.setData(FXCollections.observableArrayList(slice1, slice2));
            // Customize PieChart
            piechartDuel.setLabelLineLength(10); // Length of the lines from the pie slice to the label
            piechartDuel.setLegendVisible(true);
            piechartDuel.setLabelsVisible(true); // Show labels on the slices

            logger.debug("Confrontations : " + slice1.getName() + "/ " + slice2.getName());
            this.textareaDuel.setText(slice1.getPieValue() + " - " + slice2.getPieValue() +
                    "(" + getPlayerLastScores(3, confrontations, firstPlayer) +  ")");
        }
    }

    private PieChart.Data getConfrontationsPieChart(List<ConfrontationDTO> confrontations, String playerName) {
        long playerScore = confrontations.stream()
                .map(ConfrontationDTO::getWinnerName)
                .filter(winnerName -> winnerName.equals(playerName))
                .count();
        return new PieChart.Data(getLabel(playerName, playerScore, confrontations.size()), playerScore);
    }

    private String getLabel(String playerName, long playerScore, long confrontationsSize) {
        NumberFormat percentFormatter = NumberFormat.getPercentInstance();
        double elDouble = (double) playerScore / confrontationsSize;
        return playerName + " - " + percentFormatter.format(elDouble);
    }

    String getPlayerLastScores(int limit, List<ConfrontationDTO> confrontations, String playerName) {
        return confrontations.stream().limit(limit)
                .map(conf -> conf.getConfrontationScore(playerName))
                .reduce(((s, s2) -> {return s2 + "," + s;})).get();
    }

    @Override
    public Scene getViewScene() {
        this.initComponents();
        return new Scene(tabsPane, super.getStage().getMinWidth(),super.getStage().getMinHeight());
    }

    @Override
    public boolean isViewValid() {
        return false;
    }
}
