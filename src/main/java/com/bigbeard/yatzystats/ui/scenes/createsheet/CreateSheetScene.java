package com.bigbeard.yatzystats.ui.scenes.createsheet;

import com.bigbeard.yatzystats.core.rules.SheetRulesIdentifiers;
import com.bigbeard.yatzystats.ui.UiScene;
import com.bigbeard.yatzystats.ui.UiSceneRole;
import com.bigbeard.yatzystats.ui.WindowNavigation;
import com.bigbeard.yatzystats.ui.theming.UIButtonTheming;
import com.bigbeard.yatzystats.ui.theming.UITheming;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CreateSheetScene extends UiScene {

    private GridPane gridPane;

    private List<String> players;
    private ComboBox gameModeComboBox;
    private TextField selectedFilePathTextField;

    public CreateSheetScene(WindowNavigation navigation) {
        super(navigation, UiSceneRole.CREATE_SHEET_SCENE);
        this.initComponents();
    }

    private void initComponents() {
        this.gridPane = this.getDefaultGridPaneConfig();

        // Chargement du logo principal
        this.gridPane.setBackground(new Background(this.getBackgroundImage()));

        UIButtonTheming theming = new UIButtonTheming();
        Text mainLabel = new Text("Etape 1 : Fichier de parties et du mode de jeu");
        UITheming.getInstance().applyTextTheming(mainLabel, theming);
        this.gridPane.add(mainLabel, 0,0,3,1);

        //1. Choix du fichier
        Text chooseFileLabel = new Text("Fichier de parties : ");
        UITheming.getInstance().applyTextTheming(chooseFileLabel, theming);
        this.gridPane.add(chooseFileLabel, 1,2, 2,1);

        this.selectedFilePathTextField = new TextField();
        this.selectedFilePathTextField.setPrefSize(300,30);
        this.gridPane.add(this.selectedFilePathTextField, 3,2, 5,1);

        Button selectFileButton = this.getBrowseButton(super.getStage());
        this.gridPane.add(selectFileButton, 8, 2, 1 ,1);

        //2. Texte expliquant le fonctionnement
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

        // Ce qu'il faut pour créer la feuille !

        // Cinématique - L'utilisateur sélectionne sa partie et ses règles

        // 2.1 On transmettra le path - si le fichier n'est pas un XLSX

        // 2.2


        // Le format de la partie

        // La liste des joueurs

        // Utilisation d'une feuille existante pour créer les parties ou création de la feuille à un endroit voulu

        // Pouvoir spécifier le nombre de parties à générer

        // La date de génération (mais pas besoin de la demander)
    }

    @Override
    public Scene getViewScene() {
        return new Scene(this.gridPane, super.getStage().getMinWidth(), super.getStage().getMinHeight());
    }

    @Override
    public boolean isViewValid() {
        return false;
    }

    private Button getBrowseButton(Stage stage){
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
