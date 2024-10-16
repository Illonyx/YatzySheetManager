package com.bigbeard.yatzystats.ui.scenes.statistics.settings;

import com.bigbeard.yatzystats.core.exceptions.FileNotLoadedException;
import com.bigbeard.yatzystats.core.exceptions.RulesNotLoadedException;
import com.bigbeard.yatzystats.core.model.rules.GameRules;
import com.bigbeard.yatzystats.ui.UiScene;
import com.bigbeard.yatzystats.ui.UiSceneRole;
import com.bigbeard.yatzystats.ui.WindowNavigation;
import com.bigbeard.yatzystats.ui.scenes.common.RulesDialog;
import com.bigbeard.yatzystats.ui.theming.UIButtonTheming;
import com.bigbeard.yatzystats.ui.theming.UITheming;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

/**
 * User has to select two items :
 * - The concerned files
 * - The concerned Gamemode
 */
public class GamemodeScene extends UiScene {

    @FXML
    GridPane gridPane;
    @FXML
    Text stepText;
    @FXML
    Text folderSelectionText;
    @FXML
    TextField selectedFilePathTextField;
    @FXML
    Button selectFileButton;

    // Rules
    @FXML
    Text rulesText;
    @FXML
    ComboBox<GameRules> rulesCombobox;
    @FXML
    Button ruleInfoButton;

    @FXML
    Button goHomeButton;
    @FXML
    Button processNextStepButton;

    public GamemodeScene(WindowNavigation navigation) {
        super(navigation, UiSceneRole.GAME_MODE_SCENE);
    }

    private void initComponents() {

        // Chargement du logo principal
        gridPane.setBackground(new Background(this.getBackgroundImage()));

        UIButtonTheming theming = new UIButtonTheming();
        UITheming.getInstance().applyTextTheming(stepText, theming);
        UITheming.getInstance().applyTextTheming(folderSelectionText, theming);
        UITheming.getInstance().applyTextTheming(rulesText, theming);

        //1. Choix du fichier

        String savedPath = getModel().getUserProperties().sheetCreationPath();
        if (savedPath != null) {
            selectedFilePathTextField.setText(savedPath);
        }
        this.setBrowseButton(super.getStage(), savedPath);

        //2. Choix du mode de jeu
        List<GameRules> availableGameRules = getModel().getAvailableGameRules();
        GameRules defaultRulesValue = availableGameRules.stream()
                .filter(gameRules -> gameRules.formatId().equals(getModel().getUserProperties().defaultRulesFile()))
                .findFirst()
                .orElse(availableGameRules.get(0));
        rulesCombobox.setItems(FXCollections.observableList(availableGameRules));
        rulesCombobox.setValue(defaultRulesValue);

        ruleInfoButton.setOnAction(actionEvent -> {
            RulesDialog rulesAlert = new RulesDialog(rulesCombobox.getValue());
            rulesAlert.getDialog().showAndWait();
        });

        //3. Boutons sur le footer du menu
        this.setButtonForWindowNavigation(goHomeButton, false, UiSceneRole.STARTING_SCENE);
        this.setButtonForWindowNavigation(processNextStepButton, true, UiSceneRole.GAMES_CHOICE_SCENE);
    }

    @Override
    public boolean isViewValid() {
        String file = selectedFilePathTextField.getText();
        GameRules gameMode = this.rulesCombobox.getValue();
        getModel().getStatsSheetsUserModel().setYatzyFilePath(file);
        getModel().getStatsSheetsUserModel().setChosenRules(gameMode);

        //TODO : Dialogues d exception à afficher
        try {
            getModel().getStatsSheetsUserModel().loadSheetAnalysis();
            return true;
        } catch (RulesNotLoadedException exception) {
            Alert alert = this.createErrorAlert("Mode de jeu non pris en compte", "Le mode de jeu demandé n'a pas été pris en compte",
                    "Le fichier de règles pour le mode de jeu est inexistant ou n'a pas été trouvé sur le disque.");
            alert.showAndWait();
            return false;
        } catch (FileNotLoadedException fexception) {
            Alert alert = this.createErrorAlert("Erreur d'ouverture du fichier", fexception.getMainReason(),
                    fexception.getMessage());
            alert.showAndWait();
            return false;
        }
    }

    @Override
    public Scene getViewScene() {
        this.initComponents();
        return new Scene(gridPane, super.getStage().getMinWidth(), super.getStage().getMinHeight());
    }

    private void setBrowseButton(Stage stage, String defaultDirectoryPath) {
        final String initialDirectoryPath = defaultDirectoryPath != null ? defaultDirectoryPath : System.getProperty("user.home");
        final FileChooser fileChooser = new FileChooser();
        selectFileButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        fileChooser.setTitle("Veuillez choisir un fichier Excel");
                        fileChooser.setInitialDirectory(
                                new File(initialDirectoryPath)
                        );
                        if (fileChooser.getExtensionFilters().isEmpty()) {
                            fileChooser.getExtensionFilters().addAll(
                                    new FileChooser.ExtensionFilter("All Files", "*.*"),
                                    new FileChooser.ExtensionFilter("XLS", "*.xls"),
                                    new FileChooser.ExtensionFilter("XLSX", "*.xlsx")
                            );
                        }
                        File file = fileChooser.showOpenDialog(stage);
                        if (file != null) {
                            selectedFilePathTextField.setText(file.getAbsolutePath());
                        }
                    }
                });
    }

}
