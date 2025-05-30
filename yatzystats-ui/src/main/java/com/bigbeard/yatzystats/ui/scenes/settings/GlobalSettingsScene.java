package com.bigbeard.yatzystats.ui.scenes.settings;

import com.bigbeard.yatzystats.core.model.players.UserProperties;
import com.bigbeard.yatzystats.core.model.rules.GameRules;
import com.bigbeard.yatzystats.ui.UiScene;
import com.bigbeard.yatzystats.ui.UiSceneRole;
import com.bigbeard.yatzystats.ui.WindowNavigation;
import com.bigbeard.yatzystats.ui.scenes.common.RulesDialog;
import com.bigbeard.yatzystats.ui.theming.UIButtonTheming;
import com.bigbeard.yatzystats.ui.theming.UITheming;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class GlobalSettingsScene extends UiScene {

    @FXML
    GridPane gridPane;
    @FXML
    Text folderCreationText;
    @FXML
    TextField directoryTextfield;
    @FXML
    Button browseDirectoryButton;
    @FXML
    Text languageText;
    @FXML
    ComboBox<String> languagesCombobox;
    @FXML
    Text rulesText;
    @FXML
    ComboBox<GameRules> rulesCombobox;
    @FXML
    Button ruleInfoButton;
    @FXML
    Button goHomeButton;
    @FXML Button saveButton;
    @FXML Text mainSheetCreationText;

    public GlobalSettingsScene(WindowNavigation navigation) {
        super(navigation, UiSceneRole.SETTINGS_SCENE);
    }

    @Override
    public Scene getViewScene() {
        this.initComponents();
        return new Scene(this.getParent(), super.getStage().getMinWidth(), super.getStage().getMinHeight());
    }

    @Override
    public boolean isViewValid() {

        String sheetDefaultPath = this.directoryTextfield.getText();
        String applicationLanguage = this.languagesCombobox.getValue();
        GameRules gameRules = this.rulesCombobox.getValue();
        String rulesId = gameRules.formatId();

        UserProperties userProperties = new UserProperties(sheetDefaultPath, applicationLanguage, rulesId);
        getModel().setUserProperties(userProperties);
        return true;
    }

    private void initComponents() {

        // Chargement du logo principal
        gridPane.setBackground(new Background(this.getBackgroundImage()));
        UIButtonTheming theming = new UIButtonTheming();

        // Labels
        UITheming.getInstance().applyTextTheming(mainSheetCreationText, theming);
        UITheming.getInstance().applyTextTheming(rulesText, theming);
        UITheming.getInstance().applyTextTheming(folderCreationText, theming);
        UITheming.getInstance().applyTextTheming(languageText, theming);

        UserProperties userProperties = getModel().getUserProperties();
        // Folder
        this.directoryTextfield.setText(userProperties.sheetCreationPath());
        this.setDirectoryChooserHandler(super.getStage());

        // Language
        String savedApplicationLanguage = userProperties.applicationLanguage();
        ObservableList<String> languageOptions = FXCollections.observableList(List.of("fr", "en"));
        languagesCombobox.setItems(languageOptions);
        languagesCombobox.setValue(savedApplicationLanguage != null ? savedApplicationLanguage : "fr");

        // Rules
        List<GameRules> availableGameRules = getModel().getAvailableGameRules();
        GameRules defaultRulesValue = availableGameRules.stream()
                .filter(gameRules -> gameRules.formatId().equals(userProperties.defaultRulesFile()))
                .findFirst()
                .orElse(availableGameRules.getFirst());
        rulesCombobox.setItems(FXCollections.observableList(availableGameRules));
        rulesCombobox.setValue(defaultRulesValue);

        ruleInfoButton.setOnAction(actionEvent -> {
            RulesDialog rulesAlert = new RulesDialog(rulesCombobox.getValue(), this.getModel().getResourceBundle());
            rulesAlert.getDialog().showAndWait();
        });

        this.setButtonForWindowNavigation(goHomeButton, false, UiSceneRole.STARTING_SCENE);
        this.setButtonForWindowNavigation(saveButton, true, UiSceneRole.STARTING_SCENE);

    }

    private void setDirectoryChooserHandler(Stage stage) {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        browseDirectoryButton.setOnAction(
                e -> {
                    directoryChooser.setTitle("Choix du répertoire de création");
                    directoryChooser.setInitialDirectory(
                            new File(System.getProperty("user.home"))
                    );
                    File file = directoryChooser.showDialog(stage);
                    if (file != null) {
                        directoryTextfield.setText(file.getAbsolutePath());
                    }
                });
    }
}
