package com.bigbeard.yatzystats.ui;

import com.bigbeard.yatzystats.config.UserConfigurationModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public abstract class UiScene {

    private Stage stage;
    private UserConfigurationModel model;
    private UiSceneRole role;
    private WindowNavigation windowNavigation;

    public UiScene(WindowNavigation windowNavigation, UiSceneRole role){
        this.windowNavigation = windowNavigation;
        this.role = role;
    }

    public abstract Scene getViewScene();
    public abstract boolean isViewValid();


    public UserConfigurationModel getModel(){
        return this.windowNavigation.getModel();
    }

    public Stage getStage(){
        return this.windowNavigation.getStage();
    }

    public UiSceneRole getRole(){
        return this.role;
    }

    // --------------------------------------------------------
    // Common UI components
    // --------------------------------------------------------

    public Alert createErrorAlert(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert;
    }

    //Méthode générique permettant de créer un bouton pour aller dans une autre vue
    public Button getWindowNavigationButton(String buttonName, boolean needsValidation, UiSceneRole targetSceneRole){
        Button navButton = new Button(buttonName);
        navButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!needsValidation || (needsValidation && isViewValid())) windowNavigation.loadScene(targetSceneRole);
            }
        });
        return navButton;
    }

    // Génére un bouton pour aller dans la vue précédente
    public Button getLastSceneButton(){
        return this.getWindowNavigationButton("<< Précedént", false, UiSceneRole.getLastScene(role));
    }

    // Génère un bouton pour aller dans la vue suivante
    public Button getNextSceneButton(){
        return this.getWindowNavigationButton(">> Suivant", true, UiSceneRole.getNextScene(role));
    }

    public GridPane getDefaultGridPaneConfig() {
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(this.getStage().getMinWidth(), this.getStage().getMinHeight());
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(25);
        gridPane.setVgap(15);
        return gridPane;
    }

    public ComboBox getPlayerCombobox(){
        ObservableList<String> options = FXCollections.observableList(getModel().getPlayerNames());
        ComboBox comboBox = new ComboBox(options);
        comboBox.setValue(options.get(0));
        comboBox.setPrefSize(300,30);
        return comboBox;
    }
}
