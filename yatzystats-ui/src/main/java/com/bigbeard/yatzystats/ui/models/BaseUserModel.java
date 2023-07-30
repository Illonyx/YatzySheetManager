package com.bigbeard.yatzystats.ui.models;

import com.bigbeard.yatzystats.core.config.ExcelSheetLoader;
import com.bigbeard.yatzystats.core.config.GameRulesLoader;
import com.bigbeard.yatzystats.core.exceptions.FileNotLoadedException;
import com.bigbeard.yatzystats.core.exceptions.RulesNotLoadedException;
import com.bigbeard.yatzystats.core.model.rules.GameLoader;
import com.bigbeard.yatzystats.core.model.rules.GameRules;
import com.bigbeard.yatzystats.core.model.rules.SheetRulesIdentifiers;
import com.bigbeard.yatzystats.core.model.sheets.SheetDto;

import java.io.IOException;
import java.util.List;

public class BaseUserModel {

    //Infos 1er ecran : Choix feuille/règles
    private String yatzyFilePath;
    private SheetRulesIdentifiers chosenRules;

    //Chargé à partir du 1er ecran
    private GameRules gameRules;
    private ExcelSheetLoader excelSheetLoader;

    //Infos 2eme ecran : Choix feuilles yatzee
    private List<SheetDto> foundSheets;
    private List<String> loadingErrors;

    public String getYatzyFilePath() {
        return yatzyFilePath;
    }

    public void setYatzyFilePath(String yatzyFilePath) {
        this.yatzyFilePath = yatzyFilePath;
    }

    public SheetRulesIdentifiers getChosenRules() {
        return chosenRules;
    }

    public void setChosenRules(SheetRulesIdentifiers chosenRules) {
        this.chosenRules = chosenRules;
    }

    public List<SheetDto> getFoundSheets() {
        return foundSheets;
    }

    // -----------------------------------------------------
    // -- 1ere etape : Validation path/mode de jeu
    // -----------------------------------------------------

    public void loadExcelSheet() throws FileNotLoadedException {
        try {
            this.excelSheetLoader = new ExcelSheetLoader(this.yatzyFilePath);
            GameLoader gameLoader = new GameLoader(gameRules, this.excelSheetLoader.getFormulaEvaluator());
            this.foundSheets = gameLoader.loadGamesFromMode(this.excelSheetLoader.getAllSheets());

            //Fichier illisible, 0 parties trouvées
            this.loadingErrors = gameLoader.getErrors();


        } catch (IOException ex) {
            throw new FileNotLoadedException("Le fichier demandé n'a pas pu être ouvert",
                    "Vérifiez bien si le fichier n'est pas ouvert sous Excel ou si l'extension est bonne (xlsx)");
        }

    }

    public void loadGameRules() throws RulesNotLoadedException {
        GameRulesLoader gameRulesLoader = new GameRulesLoader(this.chosenRules);
        this.gameRules = gameRulesLoader.getGameRules();
    }


}
