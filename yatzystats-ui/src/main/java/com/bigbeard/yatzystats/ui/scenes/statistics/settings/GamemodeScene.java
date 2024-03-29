package com.bigbeard.yatzystats.ui.scenes.statistics.settings;

import com.bigbeard.yatzystats.core.exceptions.FileNotLoadedException;
import com.bigbeard.yatzystats.core.exceptions.RulesNotLoadedException;
import com.bigbeard.yatzystats.core.model.rules.SheetRulesIdentifiers;
import com.bigbeard.yatzystats.ui.UiScene;
import com.bigbeard.yatzystats.ui.models.StatsSheetsUserModel;
import com.bigbeard.yatzystats.ui.UiSceneRole;
import com.bigbeard.yatzystats.ui.WindowNavigation;
import com.bigbeard.yatzystats.ui.theming.UIButtonTheming;
import com.bigbeard.yatzystats.ui.theming.UITheming;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * User has to select two items :
 * - The concerned files
 * - The concerned Gamemode
 */
public class GamemodeScene extends UiScene {

    private GridPane gridPane;
    private Stage stage;
    private StatsSheetsUserModel model;

    private ComboBox gameModeComboBox;
    private TextField selectedFilePathTextField;

    public GamemodeScene(WindowNavigation navigation){
        super(navigation, UiSceneRole.GAME_MODE_SCENE);
        this.initComponents();
    }

    private void initComponents() {
        this.gridPane = this.getDefaultGridPaneConfig();

        // Chargement du logo principal
        this.gridPane.setBackground(new Background(this.getBackgroundImage()));

        UIButtonTheming theming = new UIButtonTheming();
        Text mainLabel = new Text("Etape 1 : Sélection du fichier de parties et du mode de jeu");
        UITheming.getInstance().applyTextTheming(mainLabel, theming);
        this.gridPane.add(mainLabel, 0,0,3,1);

        //1. Choix du fichier
        Text chooseFileLabel = new Text("Fichier de parties : ");
        UITheming.getInstance().applyTextTheming(chooseFileLabel, theming);
        this.gridPane.add(chooseFileLabel, 1,2, 2,1);

        this.selectedFilePathTextField = new TextField();
        this.selectedFilePathTextField.setPrefSize(300,30);
        this.gridPane.add(this.selectedFilePathTextField, 3,2, 5,1);

        Button selectFileButton = this.getBrowseButton();
        this.gridPane.add(selectFileButton, 8, 2, 1 ,1);

        //2. Choix du mode de jeu
        Text selectGamemodeLabel = new Text("Choix du mode de jeu :");
        UITheming.getInstance().applyTextTheming(selectGamemodeLabel, theming);
        this.gridPane.add(selectGamemodeLabel, 1,4, 2,1);

        ObservableList<String> options =
                FXCollections.observableList(
                        Arrays.stream(SheetRulesIdentifiers.values())
                                .map(SheetRulesIdentifiers::getValue)
                                .collect(Collectors.toList())
                );
        this.gameModeComboBox = new ComboBox(options);
        this.gameModeComboBox.setValue(SheetRulesIdentifiers.YATZY.getValue());
        this.gameModeComboBox.setPrefSize(300,30);
        this.gridPane.add(this.gameModeComboBox,3,4, 5,1);

        //3. Boutons sur le footer du menu
        this.gridPane.add(this.getNextSceneButton(), 8, 10,1,1);
    }

    @Override
    public boolean isViewValid() {
        String file = this.selectedFilePathTextField.getText();
        String gameMode = (String) this.gameModeComboBox.getValue();
        super.getModel().setYatzyFilePath(file);
        super.getModel().setChosenRules(SheetRulesIdentifiers.fromValue(gameMode));

        //TODO : Dialogues d exception à afficher
        try {
            getModel().loadGameRules();
            getModel().loadExcelSheet();
            return true;
        } catch(RulesNotLoadedException exception) {
            Alert alert = this.createErrorAlert("Mode de jeu non pris en compte", "Le mode de jeu demandé n'a pas été pris en compte",
                    "Le fichier de règles pour le mode de jeu est inexistant ou n'a pas été trouvé sur le disque.");
            alert.showAndWait();
            return false;
        } catch(FileNotLoadedException fexception) {
            Alert alert = this.createErrorAlert("Erreur d'ouverture du fichier", fexception.getMainReason(),
                    fexception.getMessage());
            alert.showAndWait();
            return false;
        }
    }

    @Override
    public Scene getViewScene() {
        return new Scene(gridPane, super.getStage().getMinWidth(),super.getStage().getMinHeight());
    }

    private Button getBrowseButton(){
        Button selectFileButton = new Button("Parcourir");
        final FileChooser fileChooser = new FileChooser();
        selectFileButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        fileChooser.setTitle("Tu choisis une partie enculé?");
                        fileChooser.setInitialDirectory(
                                new File(System.getProperty("user.home"))
                        );
                        if(fileChooser.getExtensionFilters().isEmpty()){
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
        return selectFileButton;
    }

}
