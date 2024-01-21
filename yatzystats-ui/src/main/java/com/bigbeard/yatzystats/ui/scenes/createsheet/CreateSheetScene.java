package com.bigbeard.yatzystats.ui.scenes.createsheet;

import com.bigbeard.yatzystats.core.model.rules.SheetRulesIdentifiers;
import com.bigbeard.yatzystats.ui.UiScene;
import com.bigbeard.yatzystats.ui.UiSceneRole;
import com.bigbeard.yatzystats.ui.WindowNavigation;
import com.bigbeard.yatzystats.ui.models.CreateSheetsUserModel;
import com.bigbeard.yatzystats.ui.theming.UIButtonTheming;
import com.bigbeard.yatzystats.ui.theming.UITheming;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CreateSheetScene extends UiScene {
    private List<String> players;
    @FXML Text mainSheetCreationText;
    @FXML GridPane gridPane;
    @FXML Button startSheetCreationButton;

    @FXML Text folderCreationText;
    @FXML TextField directoryTextfield;
    @FXML Button browseDirectoryButton;

    @FXML Text sheetNumberText;
    @FXML ComboBox<Integer> sheetNumberCombobox;

    @FXML Text playersText;
    @FXML TextField playerTextfield;
    @FXML Button addPlayerButton;
    @FXML ListView<String> playersListView;

    @FXML Text rulesText;
    @FXML ComboBox<String> rulesCombobox;

    public CreateSheetScene(WindowNavigation navigation) {
        super(navigation, UiSceneRole.CREATE_SHEET_SCENE);
    }

    private void initComponents() {

        // Chargement du logo principal
        gridPane.setBackground(new Background(this.getBackgroundImage()));
        UIButtonTheming theming = new UIButtonTheming();

        // Labels
        UITheming.getInstance().applyTextTheming(rulesText, theming);
        UITheming.getInstance().applyTextTheming(folderCreationText, theming);
//        folderCreationText.setStyle("-fx-background-image: url('../icons/find.png');" +
//                "    -fx-background-repeat: no-repeat;" +
//                "    -fx-background-position: right center;");
        UITheming.getInstance().applyTextTheming(sheetNumberText, theming);
        UITheming.getInstance().applyTextTheming(mainSheetCreationText, theming);
        UITheming.getInstance().applyTextTheming(playersText, theming);

        // Folder
        this.setDirectoryChooserHandler(super.getStage());

        // Sheet number
        ObservableList<Integer> numberOptions =
                FXCollections.observableList(
                        List.of(1,2,5,10,20)
                );
        sheetNumberCombobox.setItems(numberOptions);
        sheetNumberCombobox.setValue(2);

        // Players
        List<String> playersList = new ArrayList<>();
        ObservableList<String> playerOptions = FXCollections.observableList(playersList);
        playersListView.setItems(playerOptions);
        addPlayerButton.setOnAction(e -> {
            String val = playerTextfield.getText();
            if(!val.isEmpty() && !playerOptions.contains(val)) {
                playersListView.getItems().add(val);
            }
        });

        // Rules
        ObservableList<String> options =
                FXCollections.observableList(
                        Arrays.stream(SheetRulesIdentifiers.values())
                                .map(SheetRulesIdentifiers::getValue)
                                .collect(Collectors.toList())
                );
        rulesCombobox.setItems(options);
        rulesCombobox.setValue(SheetRulesIdentifiers.YATZY.getValue());

        // Button action
        startSheetCreationButton.setOnAction(actionEvent -> {
            if(isViewValid()) {
                System.out.println("ON A REUSSI");
            }
        });
    }

    @Override
    public Scene getViewScene() {
        this.initComponents();
        return new Scene(this.getParent(), super.getStage().getMinWidth(), super.getStage().getMinHeight());
    }

    @Override
    public boolean isViewValid() {
        // Check
        if(playersListView.getItems().isEmpty()) {
            Alert alert = super.createErrorAlert("Pas de choix de joueurs", "", "Aucune joueur n'a été sélectionné.");
            alert.showAndWait();
            return false;
        }

        // Set Data
        getSheetCreationModel().setPlayers(playersListView.getItems());
        getSheetCreationModel().setSheetNumber(sheetNumberCombobox.getValue());
        getSheetCreationModel().setChosenRules(SheetRulesIdentifiers.fromValue(rulesCombobox.getValue()));
        getSheetCreationModel().confirmFinalWritingPath(directoryTextfield.getText());

        getSheetCreationModel().writeExcelSheet();
        return true;
    }

    private void setDirectoryChooserHandler(Stage stage){
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
