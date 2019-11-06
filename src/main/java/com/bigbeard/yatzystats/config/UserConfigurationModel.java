package com.bigbeard.yatzystats.config;

import com.bigbeard.yatzystats.core.players.PlayerResult;
import com.bigbeard.yatzystats.core.rules.GameLoader;
import com.bigbeard.yatzystats.core.rules.GameRules;
import com.bigbeard.yatzystats.core.rules.SheetRulesIdentifiers;
import com.bigbeard.yatzystats.core.sheets.SheetDto;

import com.bigbeard.yatzystats.exceptions.FileNotLoadedException;
import com.bigbeard.yatzystats.exceptions.RulesNotLoadedException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    //Infos 3eme ecran : Choix des joueurs à analyser
    private List<String> selectedPlayers;

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

    // -----------------------------------------------------
    // -- 2eme etape : Choix des feuilles à analyser
    // -----------------------------------------------------

    public void loadStats(List<SheetDto> selectedSheets){
        List<PlayerResult> playerResults = selectedSheets.stream()
                .map(SheetDto::getPlayerList)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        List<String> playerNames = playerResults.stream()
                .map(PlayerResult::getPlayerName)
                .distinct()
                .collect(Collectors.toList());
        System.out.println("dd" + String.join(",", playerNames));

        Map<String, List<PlayerResult>> resultsPerPlayers = new HashMap<>();
        playerNames.stream().forEach(playerName -> {
            resultsPerPlayers.put(playerName, playerResults.stream()
                    .filter(playerResult -> playerResult.getPlayerName().equals(playerName))
                    .collect(Collectors.toList()));
        });

    }

    // -----------------------------------------------------
    // -- Gettlers / Settlers
    // -----------------------------------------------------

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


}


