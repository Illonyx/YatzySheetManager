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
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.NumberFormat;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

    @FXML
    BarChart<String, Number> barChart;

    @FXML
    CategoryAxis xAxis;

    public StatsModScene(WindowNavigation navigation) {
        super(navigation, UiSceneRole.STATS_MODE_SCENE);
        this.model = getModel().getStatsSheetsUserModel();
        model.loadStats();
    }

    private void initComponents() {
        tabsPane.setBackground(new Background(this.getBackgroundImage()));
        // Chargement du logo principal
        this.initStatsTab();
        this.initConfrontationsTab();
        this.initGlobalTab();
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

        duelButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                generateConfrontations();
            }
        });
    }

    private void initGlobalTab() {
        // Define the X and Y axes
        //this.initialize();
        barChart.getXAxis().setLabel("Test");

        // Create a series and add data
        // Create an AtomicInteger to track the index
        AtomicInteger index = new AtomicInteger(1);
        List<PlayerResult> allResults = getModel().getStatsSheetsUserModel().getAllResults();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Sample Data");

        allResults.stream()
                .sorted(Comparator.comparing(PlayerResult::score).reversed())  // Sort by score in reverse order
                .limit(10)  // Limit to top 5
                .forEach(res -> {
                    // Use the helper method to create and add the data
                    XYChart.Data<String, Number> barData = createBarData(index.getAndIncrement(), res);
                    series.getData().add(barData);  // Add the bar data to the series
                });

        barChart.setData(FXCollections.observableList(List.of(series)));

        // Add the series to the chart
        NumberAxis yAxis = (NumberAxis) barChart.getYAxis();
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(250.0);
        yAxis.setUpperBound(350.0);
        yAxis.setTickUnit(1.0);
    }

    private XYChart.Data<String, Number> createBarData(int index, PlayerResult res) {
        // Create a new Data object with the index as the X value and the player's score as the Y value
        XYChart.Data<String, Number> barData = new XYChart.Data<>(String.valueOf(index), res.score());

        // Add a listener to check when the node (bar) is fully initialized and rendered
        barData.nodeProperty().addListener((observable, oldNode, newNode) -> {
            if (newNode != null) {
                // Now the bar is fully initialized, we can add the label
                StackPane bar = (StackPane) newNode;
                String label = String.format("%s - %s", res.playerName(), res.score());
                Text dataLabel = new Text(label);

                // Rotate the label to be vertical
                dataLabel.setRotate(90);

                // Add the label to the bar and position it
                bar.getChildren().add(dataLabel);
                dataLabel.setTranslateX(-15);  // Adjust horizontal position (inside the bar)
                dataLabel.setTranslateY(10);   // Adjust vertical position to center
            }
        });

        // Return the data object to be added to the chart
        return barData;
    }

    private void updateTextarea(Object newValue) {
        List<PlayerResult> prs = model.getResultsPerPlayers().get(newValue);
        statsArea.setText(this.showStats(prs));
    }

    private String showStats(List<PlayerResult> results) {
        List<String> top5high = StatsModule.getInstance().getHighestScores(5, results);
        StringBuilder buff = new StringBuilder();

        buff.append("-- Score du joueur --").append(System.lineSeparator());
        buff.append("Moyenne : ").append(StatsModule.getInstance().getMean(results)).append(System.lineSeparator());
        buff.append("Plus haut : ").append(StatsModule.getInstance().getHighestScore(results)).append(System.lineSeparator());
        buff.append("Plus bas : ").append(StatsModule.getInstance().getLowestScore(results)).append(System.lineSeparator());
        buff.append("Scores à partir de 300 :").append(StatsModule.getInstance().get300Rate(results)).append(System.lineSeparator());
        buff.append("Scores en dessous de 200 :").append(StatsModule.getInstance().getUnder200Rate(results)).append(System.lineSeparator());
        buff.append("Standard Deviation : ").append(StatsModule.getInstance().getStandardDeviation(results)).append(System.lineSeparator());

        buff.append("-- Combinaisons du joueur --").append(System.lineSeparator());
        buff.append("Taux de victoires :").append(StatsModule.getInstance().getWinRate(results)).append(System.lineSeparator());
        buff.append("Taux de yatzées :").append(StatsModule.getInstance().getYatzyRate(results)).append(System.lineSeparator());
        buff.append("BonusRate :").append(StatsModule.getInstance().getBonusRate(results)).append(System.lineSeparator());
        buff.append("Top 5 scores :").append(String.join(",", top5high)).append(System.lineSeparator());

        return buff.toString();
    }

    public void generateConfrontations() {
        String firstPlayer = this.player1Combobox.getValue();
        String secondPlayer = this.player2Combobox.getValue();
        if (firstPlayer != null && secondPlayer != null && !firstPlayer.equals(secondPlayer)) {
            logger.debug("Génération de la confrontation entre " + firstPlayer + " et " + secondPlayer);
            List<ConfrontationDTO> confrontations = model.makeConfrontations(getModel().getStatsSheetsUserModel().getSelectedSheets(), firstPlayer, secondPlayer);

            PieChart.Data slice1 = getConfrontationsPieChart(confrontations, firstPlayer);
            PieChart.Data slice2 = getConfrontationsPieChart(confrontations, secondPlayer);
            this.piechartDuel.setData(FXCollections.observableArrayList(slice1, slice2));
            // Customize PieChart
            piechartDuel.setLabelLineLength(10); // Length of the lines from the pie slice to the label
            piechartDuel.setLegendVisible(true);
            piechartDuel.setLabelsVisible(true); // Show labels on the slices

            logger.debug("Confrontations : " + slice1.getName() + "/ " + slice2.getName());
            this.textareaDuel.setText(slice1.getPieValue() + " - " + slice2.getPieValue() +
                    "(" + getPlayerLastScores(3, confrontations, firstPlayer) + ")");
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
                .reduce(((s, s2) -> s2 + "," + s)).get();
    }

    @Override
    public Scene getViewScene() {
        this.initComponents();
        return new Scene(tabsPane, super.getStage().getMinWidth(), super.getStage().getMinHeight());
    }

    @Override
    public boolean isViewValid() {
        return false;
    }
}
