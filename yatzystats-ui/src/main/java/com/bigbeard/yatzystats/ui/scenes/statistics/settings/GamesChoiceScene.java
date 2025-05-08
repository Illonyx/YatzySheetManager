package com.bigbeard.yatzystats.ui.scenes.statistics.settings;

import com.bigbeard.yatzystats.core.model.players.PlayerResult;
import com.bigbeard.yatzystats.core.model.sheets.SheetDto;
import com.bigbeard.yatzystats.ui.UiScene;
import com.bigbeard.yatzystats.ui.UiSceneRole;
import com.bigbeard.yatzystats.ui.WindowNavigation;
import com.bigbeard.yatzystats.ui.scenes.common.UserValidationWarningCode;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;

import java.util.*;
import java.util.stream.Collectors;

public class GamesChoiceScene extends UiScene {

    @FXML GridPane gridPane;
    @FXML ListView<String> gamesListView;
    @FXML TextArea gameSummaryTextarea;

    @FXML ComboBox<String> playersCombobox;
    @FXML ComboBox<String> yearCombobox;

    @FXML Button warningsButton;
    @FXML Button goBackButton;
    @FXML Button goStatsButton;

    private final Map<String, Boolean> listCheckboxValues = new HashMap<>();

    public GamesChoiceScene(WindowNavigation navigation){
        super(navigation, UiSceneRole.GAMES_CHOICE_SCENE);
    }

    private void initComponents() {

        // Chargement du logo principal
        this.gridPane.setBackground(new Background(this.getBackgroundImage()));

        // Init filters
        this.initPlayerCombobox();
        this.initYearCombobox();

        gameSummaryTextarea.setEditable(false);

        //Initialize listview with found sheet values
        ObservableList<String> items = FXCollections.observableList (
                getModel().getStatsSheetsUserModel().getSheets().stream().map(SheetDto::sheetName).toList());
        gamesListView.setItems(items);

        //When an element is clicked on, display it on textArea
        gamesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateTextarea(newValue));

        //Add checkbox to each item of the list, checkbox states are registered in property listCheckboxValues
        gamesListView.setCellFactory(CheckBoxListCell.forListView(item -> {
            BooleanProperty observable = new SimpleBooleanProperty();
            if (listCheckboxValues.get(item) != null) {
                observable.setValue(listCheckboxValues.get(item));
            } else observable.setValue(false);
            observable.addListener((obs, wasSelected, isNowSelected) -> listCheckboxValues.put(item, isNowSelected));
            return observable;
        }));

        //Master toogle
//        Button selectAllButton = new Button("Tout sélectionner");
//        selectAllButton.setOnAction(actionEvent -> {
//            list.getItems().forEach(item -> listCheckboxValues.put(item, true));
//            list.refresh();
//        });

        // Navigation buttons
        this.setButtonForWindowNavigation(goBackButton, false, UiSceneRole.GAME_MODE_SCENE);
        this.setButtonForWindowNavigation(goStatsButton, true, UiSceneRole.STATS_MODE_SCENE);
    }

    private void initYearCombobox() {
        ObservableList<String> items = FXCollections.observableList (
                getModel().getStatsSheetsUserModel().getSheets().stream()
                        .map(SheetDto::playerList)
                        .flatMap(List::stream)
                        .map(PlayerResult::year)
                        .distinct().sorted(Comparator.reverseOrder()).toList());
        yearCombobox.setItems(items);
    }

    private void initPlayerCombobox() {
        ObservableList<String> items = FXCollections.observableList (
                getModel().getStatsSheetsUserModel().getSheets().stream()
                        .map(SheetDto::playerList)
                        .flatMap(List::stream)
                        .map(PlayerResult::playerName)
                        .distinct().toList());
        playersCombobox.setItems(items);
    }

    private void updateTextarea(String selectedItem){
        Optional<SheetDto> selectedSheet = getModel().getStatsSheetsUserModel().getSheets()
                .stream()
                .filter(sheet -> sheet.sheetName().equals(selectedItem)).findFirst();
        selectedSheet.ifPresent(sheetDto -> gameSummaryTextarea.setText(sheetDto.toString()));
    }

    @Override
    public boolean isViewValid() {
        List<String> selectedSheets = this.listCheckboxValues.entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .toList();
        if(selectedSheets.isEmpty()){
            Alert alert = getModel().getExceptionAlertBuilder().getWarningAlert(UserValidationWarningCode.STATS_NO_GAME_SELECTION);
            alert.showAndWait();
            return false;
        } else {
            List<SheetDto> selectedSheetDto = getModel().getStatsSheetsUserModel().getSheets().stream()
                    .filter(sheetDto -> selectedSheets.contains(sheetDto.sheetName()))
                    .collect(Collectors.toList());
            getModel().getStatsSheetsUserModel().setSelectedSheets(selectedSheetDto);
            return true;
        }
    }

    @Override
    public Scene getViewScene() {
        this.initComponents();
        return new Scene(this.getParent(), super.getStage().getMinWidth(),super.getStage().getMinHeight());
    }
}

