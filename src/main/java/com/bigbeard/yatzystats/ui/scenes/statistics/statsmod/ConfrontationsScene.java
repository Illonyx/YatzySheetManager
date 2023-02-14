package com.bigbeard.yatzystats.ui.scenes.statistics.statsmod;

import com.bigbeard.yatzystats.core.players.ConfrontationDTO;
import com.bigbeard.yatzystats.ui.UiScene;
import com.bigbeard.yatzystats.ui.UiSceneRole;
import com.bigbeard.yatzystats.ui.WindowNavigation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.apache.log4j.Logger;

import java.util.List;

public class ConfrontationsScene extends UiScene {

    private GridPane gridPane;
    private ComboBox comboPlayer1, comboPlayer2;
    private Text globalText;
    private Logger logger = Logger.getLogger(ConfrontationsScene.class);

    public ConfrontationsScene(WindowNavigation navigation){
        super(navigation, UiSceneRole.CONFRONTATIONS_SCENE);
        this.initComponents();
    }

    private void initComponents() {

        this.gridPane = this.getDefaultGridPaneConfig();

        // Chargement du logo principal
        this.gridPane.setBackground(new Background(this.getBackgroundImage()));

        //Switch stats button
        Button confrontationsButton = this.getWindowNavigationButton("Stats view",
                false, UiSceneRole.STATS_MODE_SCENE);
        this.gridPane.add(confrontationsButton, 8, 2, 3,2);

        //Combobox Player 1
        this.comboPlayer1 = getPlayerCombobox();
        this.comboPlayer1.getSelectionModel().select(0);
        this.gridPane.add(this.comboPlayer1,1,5, 2,1);

        //Combo player 2
        this.comboPlayer2 = getPlayerCombobox();
        this.comboPlayer1.getSelectionModel().select(1);
        this.gridPane.add(this.comboPlayer2,5,5, 2,1);

        this.globalText = new Text("");
        this.gridPane.add(this.globalText, 3, 7, 2,1);

        //Button validation
        Button button = new Button("Generer");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                generateConfrontations();
            }
        });
        this.gridPane.add(button, 7, 5, 2,1);


    }

    public void generateConfrontations(){
        String firstPlayer = (String) this.comboPlayer1.getValue();
        String secondPlayer = (String) this.comboPlayer2.getValue();
        if(firstPlayer != null && secondPlayer != null && !firstPlayer.equals(secondPlayer)){
            logger.debug("Génération de la confrontation entre " + firstPlayer + " et " + secondPlayer);
            List<ConfrontationDTO> confrontations = getModel().makeConfrontations(getModel().getSelectedSheets(), firstPlayer, secondPlayer);
            long firstPlayerScore = confrontations.stream()
                    .map(ConfrontationDTO::getWinnerName)
                    .filter(playerName -> playerName.equals(firstPlayer))
                    .count();
            long secondPlayerScore = confrontations.stream()
                    .map(ConfrontationDTO::getWinnerName)
                    .filter(playerName -> playerName.equals(secondPlayer))
                    .count();
            logger.debug("Confrontations : " + firstPlayer + " " + firstPlayerScore +
                    " - " + secondPlayerScore + " " + secondPlayer);
            this.globalText.setText(firstPlayerScore + " - " + secondPlayerScore +
                    "(" + getPlayerLastScores(3, confrontations, firstPlayer) +  ")");
        }
    }

    String getPlayerLastScores(int limit, List<ConfrontationDTO> confrontations, String playerName) {
        return confrontations.stream().limit(limit)
                .map(conf -> conf.getConfrontationScore(playerName))
                .reduce(((s, s2) -> {return s2 + "," + s;})).get();
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
