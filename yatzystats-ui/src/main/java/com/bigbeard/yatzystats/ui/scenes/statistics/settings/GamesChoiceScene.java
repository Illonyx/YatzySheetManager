package com.bigbeard.yatzystats.ui.scenes.statistics.settings;

import com.bigbeard.yatzystats.core.model.sheets.SheetDto;
import com.bigbeard.yatzystats.ui.UiScene;
import com.bigbeard.yatzystats.ui.models.StatsSheetsUserModel;
import com.bigbeard.yatzystats.ui.UiSceneRole;
import com.bigbeard.yatzystats.ui.WindowNavigation;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.Background;
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
    private final StatsSheetsUserModel model;
    private GridPane gridPane;
    private TextArea textArea;
    private final Map<String, Boolean> listCheckboxValues = new HashMap<>();

    public GamesChoiceScene(WindowNavigation navigation){
        super(navigation, UiSceneRole.GAMES_CHOICE_SCENE);
        this.initComponents();
        this.model = getModel().getStatsSheetsUserModel();
    }

    private void initComponents(){
        this.gridPane = this.getDefaultGridPaneConfig();

        // Chargement du logo principal
        this.gridPane.setBackground(new Background(this.getBackgroundImage()));

        this.textArea = new TextArea();
        this.textArea.setEditable(false);
        this.gridPane.add(textArea, 6, 0 , 7, 8);

        //Initialize listview with found sheet values
        ListView<String> list = new ListView<String>();
        ObservableList<String> items = FXCollections.observableList (
                getModel().getStatsSheetsUserModel().getSheets().stream().map(SheetDto::getSheetName).toList());
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

        //Master toogle
        Button selectAllButton = new Button("Tout sélectionner");
        selectAllButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                list.getItems().forEach(item -> listCheckboxValues.put(item, true));
                list.refresh();
            }
        });
        this.gridPane.add(selectAllButton, 0,10, 3, 2);
    }

    private void updateTextarea(String selectedItem){
        Optional<SheetDto> selectedSheet = getModel().getStatsSheetsUserModel().getSheets().stream().filter(sheet -> {
            return sheet.getSheetName().equals(selectedItem);
        }).findFirst();
        selectedSheet.ifPresent(sheetDto -> textArea.setText(sheetDto.toString()));
    }

    @Override
    public boolean isViewValid() {
        List<String> selectedSheets = this.listCheckboxValues.entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .toList();
        if(selectedSheets.isEmpty()){
            Alert alert = super.createErrorAlert("Pas de choix de sélection", "", "Aucune feuille n'a été sélectionnée.");
            alert.showAndWait();
            return false;
        } else {
            List<SheetDto> selectedSheetDto = model.getSheets().stream()
                    .filter(sheetDto -> selectedSheets.contains(sheetDto.getSheetName()))
                    .collect(Collectors.toList());
            model.setSelectedSheets(selectedSheetDto);
            return true;
        }
    }

    @Override
    public Scene getViewScene() {
        return new Scene(gridPane, super.getStage().getMinWidth(),super.getStage().getMinHeight());
    }
}

