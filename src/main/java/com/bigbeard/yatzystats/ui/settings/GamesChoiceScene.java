package com.bigbeard.yatzystats.ui.settings;

import com.bigbeard.yatzystats.config.UserConfigurationModel;
import com.bigbeard.yatzystats.core.sheets.SheetDto;
import com.bigbeard.yatzystats.ui.UiScene;
import com.bigbeard.yatzystats.ui.UiSceneRole;
import com.bigbeard.yatzystats.ui.WindowNavigation;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class GamesChoiceScene extends UiScene {

    private Stage stage;
    private UserConfigurationModel model;
    private GridPane gridPane;
    private TextArea textArea;
    private Map<String, Boolean> listCheckboxValues = new HashMap<>();

    public GamesChoiceScene(WindowNavigation navigation){
        super(navigation, UiSceneRole.GAMES_CHOICE_SCENE);
        this.initComponents();
    }

    private void initComponents(){
        this.gridPane = new GridPane();
        this.gridPane.setMinSize(super.getStage().getMinWidth(),super.getStage().getMinHeight());
        this.gridPane.setPadding(new Insets(20));
        this.gridPane.setHgap(25);
        this.gridPane.setVgap(15);

        this.textArea = new TextArea();
        this.textArea.setEditable(false);
        this.gridPane.add(textArea, 6, 0 , 7, 8);

        //Initialize listview with found sheet values
        ListView<String> list = new ListView<String>();
        ObservableList<String> items = FXCollections.observableList (
                super.getModel().getFoundSheets().stream().map(SheetDto::getSheetName).collect(Collectors.toList()));
        list.setItems(items);

        //When an element is clicked on, display it on textArea
        list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateTextarea(newValue));

        //Add checkbox to each item of the list, checkbox states are registered in property listCheckboxValues
        list.setCellFactory(CheckBoxListCell.forListView(new Callback<String, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(String item) {
                BooleanProperty observable = new SimpleBooleanProperty();
                if(listCheckboxValues.get(item) != null){
                    observable.setValue(listCheckboxValues.get(item));
                } else observable.setValue(false);
                observable.addListener((obs, wasSelected, isNowSelected) -> listCheckboxValues.put(item,isNowSelected));
                return observable;
            }
        }));
        this.gridPane.add(list, 0,0, 5, 10);

        //Button to go to the stats module
        this.gridPane.add(this.getNextSceneButton(), 10, 10, 3, 2);
    }

    private void updateTextarea(String selectedItem){
        Optional<SheetDto> selectedSheet = getModel().getFoundSheets().stream().filter(sheet -> {
            return sheet.getSheetName().equals(selectedItem);
        }).findFirst();
        if(selectedSheet.isPresent()){
            textArea.setText(selectedSheet.get().toString());
        }
    }

    @Override
    public boolean isViewValid() {
        List<String> selectedSheets = this.listCheckboxValues.entrySet().stream()
                .filter(stringBooleanEntry -> stringBooleanEntry.getValue() == true)
                .map(stringBooleanEntry -> stringBooleanEntry.getKey())
                .collect(Collectors.toList());
        if(selectedSheets.size() == 0){
            Alert alert = super.createErrorAlert("Pas de choix de sélection", "", "Aucune feuille n'a été sélectionnée.");
            alert.showAndWait();
            return false;
        } else {
            List<SheetDto> selectedSheetDto = super.getModel().getFoundSheets().stream()
                    .filter(sheetDto -> selectedSheets.contains(sheetDto.getSheetName()))
                    .collect(Collectors.toList());
            getModel().setSelectedSheets(selectedSheetDto);
            return true;
        }
    }

    @Override
    public Scene getViewScene() {
        return new Scene(gridPane, super.getStage().getMinWidth(),super.getStage().getMinHeight());
    }
}

