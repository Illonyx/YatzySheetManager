package com.bigbeard.yatzystats.ui.models;

import com.bigbeard.yatzystats.core.model.players.ConfrontationDTO;
import com.bigbeard.yatzystats.core.model.players.PlayerResult;
import com.bigbeard.yatzystats.core.model.sheets.SheetDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatsSheetsUserModel extends BaseUserModel {

    private Logger logger = LogManager.getLogger(StatsSheetsUserModel.class);


    //Infos 3eme ecran : Choix des joueurs à analyser
    private List<String> playerNames;
    private List<SheetDto> selectedSheets;
    private Map<String, List<PlayerResult>> resultsPerPlayers;


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

    public List<ConfrontationDTO> makeConfrontations(List<SheetDto> selectedSheets, String currentPlayerName, String opponentPlayerName) {
        List<ConfrontationDTO> confrontations = selectedSheets.stream()
                .map(sheetDto -> sheetDto.findConfrontation(currentPlayerName, opponentPlayerName))
                .filter(confrontation -> confrontation != null)
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
}


