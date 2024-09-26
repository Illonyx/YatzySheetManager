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
                    int playerScore = (rules.getFinalSum().getSheetIndex() != null) ? sheetReader.readScore(playerName) : 0;

                    boolean hasYatzy = rules.getYahtzee() != null && rules.getYahtzee().getSheetIndex() != null && sheetReader.readYatzy(playerName) == rules.getYahtzee().getMaxValue().intValue();

                    boolean hasBonus = rules.getPartialSum().getSheetIndex() != null && sheetReader.readBonus(playerName) >= rules.getBonusCond();

                    if (playerScore > 0) {
                        playerResults.add(new PlayerResult(playerName, playerScore, hasYatzy, hasBonus, false));
                    }
                }

                int bestScore = playerResults.stream()
                        .mapToInt(PlayerResult::score)
                        .max()
                        .orElse(0);

                // Rebuild the list of player results with winner information in one pass
                playerResults = playerResults.stream()
                        .map(playerResult -> playerResult.withWinner(playerResult.score() == bestScore))
                        .sorted(Comparator.comparingInt(PlayerResult::score).reversed())
                        .toList();

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


