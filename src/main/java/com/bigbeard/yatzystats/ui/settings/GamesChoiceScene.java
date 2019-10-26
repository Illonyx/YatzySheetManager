package com.bigbeard.yatzystats.ui.settings;

import com.bigbeard.yatzystats.config.UserConfigurationModel;
import com.bigbeard.yatzystats.core.sheets.SheetDto;
import com.bigbeard.yatzystats.ui.UiScene;
import com.bigbeard.yatzystats.ui.UiSceneRole;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.stream.Collectors;

public class GamesChoiceScene extends UiScene {

    private Stage stage;
    private UserConfigurationModel model;
    private GridPane gridPane;
    private TextArea textArea;

    public GamesChoiceScene(Stage stage, UserConfigurationModel model){
        super(stage, model, UiSceneRole.GAMES_CHOICE_SCENE);
        this.initComponents();
    }

    private void initComponents(){
        this.gridPane = new GridPane();
        this.gridPane.setMinSize(600,300);
        this.gridPane.setPadding(new Insets(20));
        this.gridPane.setHgap(25);
        this.gridPane.setVgap(15);

        this.textArea = new TextArea();
        this.textArea.setEditable(false);
        this.gridPane.add(textArea, 6, 0 , 7, 10);

        ListView<String> list = new ListView<String>();
        ObservableList<String> items = FXCollections.observableList (
                super.getModel().getFoundSheets().stream().map(SheetDto::getSheetName).collect(Collectors.toList()));
        list.setItems(items);

        list.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    updateTextarea(newValue);
                }
        );
        this.gridPane.add(list, 0,0, 5, 10);

    }

    private void updateTextarea(String selectedItem){
        Optional<SheetDto> selectedSheet = GamesChoiceScene.super.getModel().getFoundSheets().stream().filter(sheet -> {
            return sheet.getSheetName().equals(selectedItem);
        }).findFirst();
        if(selectedSheet.isPresent()){
            textArea.setText(selectedSheet.get().toString());
        }
    }

    @Override
    public Scene getViewScene() {
        return new Scene(this.gridPane, 600,300);
    }
}

