package com.bigbeard.yatzystats.ui.models;

import com.bigbeard.yatzystats.core.exceptions.FileNotLoadedException;
import com.bigbeard.yatzystats.core.exceptions.RulesNotLoadedException;
import com.bigbeard.yatzystats.core.model.players.ConfrontationDTO;
import com.bigbeard.yatzystats.core.model.players.PlayerResult;
import com.bigbeard.yatzystats.core.model.rules.GameLoadingStatus;
import com.bigbeard.yatzystats.core.model.rules.GameRules;
import com.bigbeard.yatzystats.core.model.rules.SheetRulesIdentifiers;
import com.bigbeard.yatzystats.core.model.sheets.SheetDto;
import com.bigbeard.yatzystats.ui.GlobalController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class StatsSheetsUserModel {

    private final Logger logger = LogManager.getLogger(StatsSheetsUserModel.class);
    private final GlobalController globalController = new GlobalController();

    // Infos 1er écran
    private SheetRulesIdentifiers chosenRules;
    private String yatzyFilePath;

    //Infos 2eme ecran : Choix feuilles yatzee
    private List<SheetDto> sheets;
    private List<String> loadingErrors;

    //Infos 3eme ecran : Choix des joueurs à analyser
    private List<String> playerNames;
    private List<SheetDto> selectedSheets;
    private Map<String, List<PlayerResult>> resultsPerPlayers;

    // -------------------------------------------------
    // -- 1ère étape : Chargement des données de jeu
    // -------------------------------------------------

    public void loadSheetAnalysis() throws RulesNotLoadedException, FileNotLoadedException {
        GameRules gameRules = this.globalController.loadGameRules(this.chosenRules);
        GameLoadingStatus status = this.globalController.loadExcelSheet(this.yatzyFilePath, gameRules);
        this.sheets = status.getSheetDtoList();
        this.loadingErrors = status.getLoadingErrors();
    }

    // -----------------------------------------------------
    // -- 2eme etape : Choix des feuilles à analyser
    // -----------------------------------------------------

    public void loadStats() {
        List<PlayerResult> playerResults = selectedSheets.stream()
                .map(SheetDto::getPlayerList)
                .flatMap(List::stream)
                .toList();
        List<String> playerNames = playerResults.stream()
                .map(PlayerResult::getPlayerName)
                .distinct()
                .collect(Collectors.toList());
        this.playerNames = playerNames;
        this.resultsPerPlayers = new HashMap<>();
        playerNames.forEach(playerName -> {
            resultsPerPlayers.put(playerName, playerResults.stream()
                    .filter(playerResult -> playerResult.getPlayerName().equals(playerName))
                    .collect(Collectors.toList()));
        });
    }

    public List<ConfrontationDTO> makeConfrontations(List<SheetDto> selectedSheets, String currentPlayerName, String opponentPlayerName) {
        List<ConfrontationDTO> confrontations = selectedSheets.stream()
                .map(sheetDto -> sheetDto.findConfrontation(currentPlayerName, opponentPlayerName))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return confrontations;
    }

    // -----------------------------------------------------
    // -- Gettlers / Settlers
    // -----------------------------------------------------

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

    public void setChosenRules(SheetRulesIdentifiers sheetRulesIdentifiers) {
        this.chosenRules = sheetRulesIdentifiers;
    }

    public void setYatzyFilePath(String file) {
        this.yatzyFilePath = file;
    }

    public List<SheetDto> getSheets() {
        return this.sheets;
    }

    public List<String> getLoadingErrors() {
        return loadingErrors;
    }
}


