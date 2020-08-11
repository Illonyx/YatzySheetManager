package com.bigbeard.yatzystats.config;

import com.bigbeard.yatzystats.core.players.ConfrontationDTO;
import com.bigbeard.yatzystats.core.players.PlayerResult;
import com.bigbeard.yatzystats.core.rules.GameLoader;
import com.bigbeard.yatzystats.core.rules.GameRules;
import com.bigbeard.yatzystats.core.rules.SheetRulesIdentifiers;
import com.bigbeard.yatzystats.core.sheets.SheetDto;

import com.bigbeard.yatzystats.exceptions.FileNotLoadedException;
import com.bigbeard.yatzystats.exceptions.RulesNotLoadedException;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserConfigurationModel {

    private Logger logger = Logger.getLogger(UserConfigurationModel.class);

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
    private List<String> playerNames;
    private List<SheetDto> selectedSheets;
    private Map<String, List<PlayerResult>> resultsPerPlayers;

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


        } catch(IOException ex) {
            logger.error("IO Exc : " + ex);
            throw new FileNotLoadedException("Le fichier demandé n'a pas pu être ouvert",
                    "Vérifiez bien si le fichier n'est pas ouvert sous Excel ou si l'extension est bonne (xlsx)");
        }

    }

    public void loadGameRules() throws RulesNotLoadedException {
        GameRulesLoader gameRulesLoader = new GameRulesLoader(this.chosenRules);
        this.gameRules = gameRulesLoader.getGameRules();
    }

    // -----------------------------------------------------
    // -- 2eme etape : Choix des feuilles à analyser
    // -----------------------------------------------------

    public void loadStats() {
        List<PlayerResult> playerResults = selectedSheets.stream()
                .map(SheetDto::getPlayerList)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        List<String> playerNames = playerResults.stream()
                .map(PlayerResult::getPlayerName)
                .distinct()
                .collect(Collectors.toList());
        this.playerNames = playerNames;
        this.resultsPerPlayers = new HashMap<>();
        playerNames.stream().forEach(playerName -> {
            resultsPerPlayers.put(playerName, playerResults.stream()
                    .filter(playerResult -> playerResult.getPlayerName().equals(playerName))
                    .collect(Collectors.toList()));
        });

    }

    public List<ConfrontationDTO> makeConfrontations(List<SheetDto> selectedSheets, String currentPlayerName, String opponentPlayerName){
        List<ConfrontationDTO> confrontations = selectedSheets.stream()
                .map(sheetDto -> sheetDto.findConfrontation(currentPlayerName, opponentPlayerName))
                .filter(confrontation -> confrontation != null)
                .collect(Collectors.toList());
        return confrontations;
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

    public List<String> getPlayerNames() {
        return playerNames;
    }

    public Map<String, List<PlayerResult>> getResultsPerPlayers() {
        return resultsPerPlayers;
    }

    public List<SheetDto> getSelectedSheets() {
        return selectedSheets;
    }

    public void setSelectedSheets(List<SheetDto> selectedSheets) {
        this.selectedSheets = selectedSheets;
    }
}


