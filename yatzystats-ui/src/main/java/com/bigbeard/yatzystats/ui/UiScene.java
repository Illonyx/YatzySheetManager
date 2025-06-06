package com.bigbeard.yatzystats.ui;

import com.bigbeard.yatzystats.ui.models.GlobalUserModel;
import com.bigbeard.yatzystats.ui.models.StatsSheetsUserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;

public abstract class UiScene {

    private Stage stage;
    private StatsSheetsUserModel model;
    private UiSceneRole role;
    protected WindowNavigation windowNavigation;
    private Parent parent;

    public UiScene(WindowNavigation windowNavigation, UiSceneRole role){
        this.windowNavigation = windowNavigation;
        this.role = role;
    }

    public abstract Scene getViewScene();
    public abstract boolean isViewValid();


    public GlobalUserModel getModel(){
        return this.windowNavigation.getModel();
    }

    public Stage getStage(){
        return this.windowNavigation.getStage();
    }

    public UiSceneRole getRole(){
        return this.role;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    // --------------------------------------------------------
    // Common UI components
    // --------------------------------------------------------

    //Méthode générique permettant de créer un bouton pour aller dans une autre vue
    public Button getWindowNavigationButton(String buttonName, boolean needsValidation, UiSceneRole targetSceneRole){
        Button navButton = new Button(buttonName);
        this.setButtonForWindowNavigation(navButton, needsValidation, targetSceneRole);
        return navButton;
    }

    public void setButtonForWindowNavigation(Button navButton, boolean needsValidation, UiSceneRole targetSceneRole) {
        navButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!needsValidation || (needsValidation && isViewValid())) windowNavigation.loadScene(targetSceneRole);
            }
        });
        navButton.setOnMouseEntered(new EventHandler<javafx.scene.input.MouseEvent>() {
            public void handle(javafx.scene.input.MouseEvent me) {
                getStage().getScene().setCursor(Cursor.HAND); //Change cursor to hand
            }
        });
        navButton.setOnMouseExited(new EventHandler<javafx.scene.input.MouseEvent>() {
            public void handle(javafx.scene.input.MouseEvent me) {
                getStage().getScene().setCursor(Cursor.DEFAULT);
            }
        });
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
        gridPane.setPrefSize(this.getStage().getMinWidth(), this.getStage().getMinHeight());
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(25);
        gridPane.setVgap(15);
        return gridPane;
    }

    public ComboBox getPlayerCombobox(List<String> playerNames){
        ObservableList<String> options = FXCollections.observableList(playerNames);
        ComboBox comboBox = new ComboBox(options);
        comboBox.setValue(options.get(0));
        comboBox.setPrefSize(300,30);
        return comboBox;
    }

    public BackgroundImage getBackgroundImage() {
        URL resourceUrl = getClass().getClassLoader().getResource("icon/background.png");
        Image image = new Image("file:" + resourceUrl.getPath());
        BackgroundSize size = new BackgroundSize(this.getStage().getWidth(), this.getStage().getHeight(),
                false, false, true, false);
        return new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.ROUND, BackgroundPosition.DEFAULT, size);
    }

}
