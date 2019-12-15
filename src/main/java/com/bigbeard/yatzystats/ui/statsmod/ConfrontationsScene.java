package com.bigbeard.yatzystats.ui.statsmod;

import com.bigbeard.yatzystats.core.players.ConfrontationDTO;
import com.bigbeard.yatzystats.ui.UiScene;
import com.bigbeard.yatzystats.ui.UiSceneRole;
import com.bigbeard.yatzystats.ui.WindowNavigation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

import java.util.List;

public class ConfrontationsScene extends UiScene {

    private GridPane gridPane;
    private ComboBox comboPlayer1, comboPlayer2;

    public ConfrontationsScene(WindowNavigation navigation){
        super(navigation, UiSceneRole.CONFRONTATIONS_SCENE);
        this.initComponents();
    }

    private void initComponents() {

        this.gridPane = new GridPane();
        this.gridPane.setMinSize(super.getStage().getMinWidth(),super.getStage().getMinHeight());
        this.gridPane.setPadding(new Insets(20));
        this.gridPane.setHgap(25);
        this.gridPane.setVgap(15);

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
        this.gridPane.add(this.comboPlayer2,4,5, 2,1);

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
            System.out.println("Génération de la confrontation entre " + firstPlayer + " et " + secondPlayer);
            List<ConfrontationDTO> confrontations = getModel().makeConfrontations(getModel().getSelectedSheets(), firstPlayer, secondPlayer);
            System.out.print(confrontations.size());
            long firstPlayerScore = confrontations.stream()
                    .map(ConfrontationDTO::getWinnerName)
                    .filter(playerName -> playerName.equals(firstPlayer))
                    .count();
            long secondPlayerScore = confrontations.stream()
                    .map(ConfrontationDTO::getWinnerName)
                    .filter(playerName -> playerName.equals(secondPlayer))
                    .count();
            System.out.println("Confrontations : " + firstPlayer + " " + firstPlayerScore +
                    " - " + secondPlayerScore + " " + secondPlayer);
        }
    }

//    public void initScoreHeader(List<ConfrontationDTO> confrontationDTOList){
//
//    }

    @Override
    public Scene getViewScene() {
        return new Scene(this.gridPane, super.getStage().getMinWidth(),super.getStage().getMinHeight());
    }

    @Override
    public boolean isViewValid() {
        return false;
    }
}
