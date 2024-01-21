package com.bigbeard.yatzystats.core.model.rules;

import com.bigbeard.yatzystats.core.exceptions.CellNotFoundException;
import com.bigbeard.yatzystats.core.exceptions.FileNotLoadedException;
import com.bigbeard.yatzystats.core.model.players.PlayerResult;
import com.bigbeard.yatzystats.core.model.sheets.SheetDto;
import com.bigbeard.yatzystats.core.model.sheets.SheetLoader;
import com.bigbeard.yatzystats.core.model.sheets.SheetReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * La responsabilité de cet objet est de charger les bonnes règles selon les règles du jeu
 */
public class GameLoader {

    SheetLoader sheetLoader;
    GameRules rules;
    List<String> errors = new ArrayList<String>();
    private final Logger logger = LogManager.getLogger(GameLoader.class);

    public GameLoader(GameRules rules, SheetLoader sheetLoader) {
        this.rules = rules;
        this.sheetLoader = sheetLoader;
    }

    public List<SheetDto> loadGamesFromMode() throws FileNotLoadedException {
        logger.info("Chargement des parties avec les règles suivantes :" + this.rules);

        List<SheetDto> sheetDtoList = new ArrayList<SheetDto>();
        for (SheetReader sheetReader : this.sheetLoader.createSheetReaders(this.rules)) {
            try {
                List<String> players = sheetReader.readPlayerNames();
                List<PlayerResult> playerResults = new ArrayList<PlayerResult>();

                for (String playerName : players) {
                    PlayerResult playerResult = new PlayerResult();
                    playerResult.setPlayerName(playerName);

                    if (rules.getFinalSum().getSheetIndex() != null) {
                        int playerScore = sheetReader.readScore(playerName);
                        playerResult.setScore(playerScore);
                    }

                    if (rules.getYahtzee() != null && rules.getYahtzee().getSheetIndex() != null) {
                        playerResult.setHasYatzy(sheetReader.readYatzy(playerName) == this.rules.getYahtzee().getMaxValue().intValue());
                    }

                    if (rules.getPartialSum().getSheetIndex() != null) {
                        playerResult.setHasBonus(sheetReader.readBonus(playerName) >= this.rules.getBonusCond());
                    }
                    //Ne pas ajouter la partie d'un joueur qui a 0 dans son score
                    if (playerResult.getScore() > 0)
                        playerResults.add(playerResult);

                }

                int bestScore = playerResults.stream().map(PlayerResult::getScore).max(Integer::compareTo).orElse(0);
                playerResults = playerResults.stream().map(playerResult -> {
                    int score = playerResult.getScore();
                    playerResult.setWinner(score == bestScore);
                    return playerResult;
                }).sorted(Comparator.comparingInt(PlayerResult::getScore).reversed()).collect(Collectors.toList());

                SheetDto sheetDto = new SheetDto(sheetReader.getSheetName(), playerResults, bestScore);
                sheetDtoList.add(sheetDto);

            } catch (CellNotFoundException ex) {
                String error = "Feuille non chargée : " + sheetReader.getSheetName() + " - raison :" + ex.getCellLabel();
                logger.error(error);
                this.errors.add(error);
            } catch (Exception ex) {
                logger.error("Error" + ex);
                throw new FileNotLoadedException("Une erreur est survenue lors de la lecture du fichier", ex.getMessage());
            }

        }

        logger.info("Nombre de parties chargées : " + sheetDtoList.size() + "/" + this.sheetLoader.getSheetNumber());
        if (sheetDtoList.isEmpty() || !listDuplicateUsingSet(sheetDtoList).isEmpty())
            throw new FileNotLoadedException("Le nombre de parties chargées est de 0", "Vérifiez bien si le format des parties enregistrées dans le fichier Excel est cohérent avec celui des règles utilisées");
        return sheetDtoList;
    }

    public List<String> getErrors() {
        return errors;
    }

    List<PlayerResult> listDuplicateUsingSet(List<SheetDto> list) {
        List<PlayerResult> duplicates = new ArrayList<>();
        Set<PlayerResult> set = new HashSet<>();
        for (SheetDto i : list) {
            for(PlayerResult pr: i.getPlayerList()){
                if (set.contains(pr)) {
                    duplicates.add(pr);
                } else {
                    set.add(pr);
                }
            }
        }
        return duplicates;
    }

}


