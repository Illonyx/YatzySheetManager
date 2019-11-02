package com.bigbeard.yatzystats.config;

import com.bigbeard.yatzystats.core.rules.GameLoader;
import com.bigbeard.yatzystats.core.rules.GameRules;
import com.bigbeard.yatzystats.core.rules.SheetRulesIdentifiers;
import com.bigbeard.yatzystats.core.sheets.SheetDto;

import com.bigbeard.yatzystats.exceptions.FileNotLoadedException;
import com.bigbeard.yatzystats.exceptions.RulesNotLoadedException;

import java.io.IOException;
import java.util.List;

public class UserConfigurationModel {

    //Infos 1er ecran : Choix feuille/règles
    private String yatzyFilePath;
    private SheetRulesIdentifiers chosenRules;

    //Chargé à partir du 1er ecran
    private GameRules gameRules;
    private ExcelSheetLoader excelSheetLoader;

    //Infos 2eme ecran : Choix feuilles yatzee
    private List<SheetDto> foundSheets;
    private List<String> loadingErrors;
    private List<String> selectedSheets;

    //Infos 3eme ecran : Choix des joueurs à analyser
    private List<String> selectedPlayers;

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

    // -----------------------------------------------------
    // -- 1ere etape : Validation path/mode de jeu
    // -----------------------------------------------------

    public void loadExcelSheet() throws FileNotLoadedException {
        try {
            this.excelSheetLoader = new ExcelSheetLoader(this.yatzyFilePath);
            GameLoader gameLoader = new GameLoader(gameRules, this.excelSheetLoader.getFormulaEvaluator());
            this.foundSheets = gameLoader.loadGamesFromMode(this.excelSheetLoader.getAllSheets());
            this.loadingErrors = gameLoader.getErrors();

        } catch(IOException ex) {
            System.err.println("IO Exc : " + ex);
            throw new FileNotLoadedException();
        }

    }

    public void loadGameRules() throws RulesNotLoadedException {
        try {
            GameRulesLoader gameRulesLoader = new GameRulesLoader(this.chosenRules);
            this.gameRules = gameRulesLoader.getGameRules();
        } catch (IOException ex){
            System.err.println("Erreur chargement gameRules : " + ex);
            throw new RulesNotLoadedException();
        }

    }

    public List<SheetDto> getFoundSheets() {
        return foundSheets;
    }
}


