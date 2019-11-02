package com.bigbeard.yatzystats.ui.settings;

import com.bigbeard.yatzystats.config.UserConfigurationModel;
import com.bigbeard.yatzystats.core.rules.SheetRulesIdentifiers;
import com.bigbeard.yatzystats.exceptions.FileNotLoadedException;
import com.bigbeard.yatzystats.exceptions.RulesNotLoadedException;
import com.bigbeard.yatzystats.ui.UiScene;
import com.bigbeard.yatzystats.ui.UiSceneRole;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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
    private UserConfigurationModel model;

    public GamemodeScene(Stage stage, UserConfigurationModel model){
        super(stage, model, UiSceneRole.GAME_MODE_SCENE);
        this.initComponents();
    }

    private void initComponents() {
        this.gridPane = new GridPane();
        this.gridPane.setMinSize(600,300);
        this.gridPane.setPadding(new Insets(20));
        this.gridPane.setHgap(25);
        this.gridPane.setVgap(15);

        Text mainLabel = new Text("Etape 1 : Sélection du fichier de parties et du mode de jeu");
        this.gridPane.add(mainLabel, 0,0,3,1);

        //1. Choix du fichier
        Text chooseFileLabel = new Text("Fichier de parties : ");
        this.gridPane.add(chooseFileLabel, 1,2, 2,1);

        TextField selectedFilePathTextField = new TextField();
        selectedFilePathTextField.setPrefSize(300,30);
        this.gridPane.add(selectedFilePathTextField, 3,2, 5,1);

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
                            System.out.println("Fichier" + file.toString());
                        }
                    }
                });
        this.gridPane.add(selectFileButton, 8, 2, 1 ,1);

        //2. Choix du mode de jeu
        Text selectGamemodeLabel = new Text("Choix du mode de jeu :");
        this.gridPane.add(selectGamemodeLabel, 1,4, 2,1);

        ObservableList<String> options =
                FXCollections.observableList(
                        Arrays.stream(SheetRulesIdentifiers.values())
                                .map(SheetRulesIdentifiers::getValue)
                                .collect(Collectors.toList())
                );
        ComboBox gameModeComboBox = new ComboBox(options);
        gameModeComboBox.setValue(SheetRulesIdentifiers.YATZY.getValue());
        gameModeComboBox.setPrefSize(300,30);
        this.gridPane.add(gameModeComboBox,3,4, 5,1);

        //3. Boutons sur le footer du menu
        Button nextSceneButton = new Button("Suivant");
        UserConfigurationModel model1 = model;
        nextSceneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                nextSc(selectedFilePathTextField.getText(), (String) gameModeComboBox.getValue());
            }
        });
        this.gridPane.add(nextSceneButton, 8, 10,1,1);


    }

    private void nextSc(String file, String gameMode) {

        super.getModel().setYatzyFilePath(file);
        super.getModel().setChosenRules(SheetRulesIdentifiers.fromValue(gameMode));

        //TODO : Dialogues d exception à afficher
        try {
            getModel().loadGameRules();
            getModel().loadExcelSheet();
            UiSceneRole nextScene = GamemodeScene.super.getNextScene();
            GamemodeScene.super.loadScene(nextScene);
        } catch(RulesNotLoadedException exception) {
            Alert alert = this.createErrorAlert("Mode de jeu non pris en compte", "Le mode de jeu demandé n'a pas été pris en compte",
                    "Le fichier de règles pour le mode de jeu est inexistant ou n'a pas été trouvé sur le disque.");
            alert.showAndWait();
        } catch(FileNotLoadedException fexception) {
            Alert alert = this.createErrorAlert("Erreur d'ouverture du fichier", "Le fichier demandé n'a pas pu être ouvert",
                    "Vérifiez bien si le fichier n'est pas ouvert sous Excel ou si l'extension est bonne");
            alert.showAndWait();
        }
    }

    @Override
    public Scene getViewScene() {
        return new Scene(gridPane, 600,300);
    }


}
