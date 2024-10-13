package com.bigbeard.yatzystats.core.config.loaders;

import com.bigbeard.yatzystats.core.exceptions.CellNotFoundException;
import com.bigbeard.yatzystats.core.exceptions.FileNotLoadedException;
import com.bigbeard.yatzystats.core.model.players.PlayerResult;
import com.bigbeard.yatzystats.core.model.rules.GameRules;
import com.bigbeard.yatzystats.core.model.sheets.SheetDto;
import com.bigbeard.yatzystats.core.model.sheets.SheetLoader;
import com.bigbeard.yatzystats.core.model.sheets.SheetReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * La responsabilité de cet objet est de charger les bonnes règles selon les règles du jeu
 */
public class GameLoader {

    SheetLoader sheetLoader;
    GameRules rules;
    List<String> errors = new ArrayList<>();
    private final Logger logger = LogManager.getLogger(GameLoader.class);

    public GameLoader(GameRules rules, SheetLoader sheetLoader) {
        this.rules = rules;
        this.sheetLoader = sheetLoader;
    }

    public List<SheetDto> loadGamesFromMode() throws FileNotLoadedException {
        logger.info("Chargement des parties avec les règles suivantes :" + this.rules);

        List<SheetDto> sheetDtoList = new ArrayList<>();
        for (SheetReader sheetReader : this.sheetLoader.createSheetReaders(this.rules)) {
            try {
                List<String> players = sheetReader.readPlayerNames();
                List<PlayerResult> playerResults = new ArrayList<>();

                for (String playerName : players) {

                    // Dice values
                    int acesScore = sheetReader.readAces(playerName);
                    int twosScore = sheetReader.readTwos(playerName);
                    int threesScore = sheetReader.readThrees(playerName);
                    int foursScore = sheetReader.readFours(playerName);
                    int fivesScore = sheetReader.readFives(playerName);
                    int sixesScore = sheetReader.readSixes(playerName);

                    int playerScore = (rules.getFinalSum().getSheetIndex() != null) ? sheetReader.readScore(playerName) : 0;

                    boolean hasYatzy = rules.getYahtzee() != null && rules.getYahtzee().getSheetIndex() != null && sheetReader.readYatzy(playerName) == rules.getYahtzee().getMaxValue().intValue();

                    boolean hasBonus = rules.getPartialSum().getSheetIndex() != null && sheetReader.readBonus(playerName) >= rules.getBonusCond();

                    if (playerScore > 0) {
                        playerResults.add(new PlayerResult(playerName, playerScore, acesScore, twosScore, threesScore, foursScore, fivesScore, sixesScore,
                                hasYatzy, hasBonus, false));
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
        if (sheetDtoList.isEmpty())
            throw new FileNotLoadedException("Le nombre de parties chargées est de 0", "Vérifiez bien si le format des parties enregistrées dans le fichier Excel est cohérent avec celui des règles utilisées");
        checkForDuplicates(sheetDtoList);
        return sheetDtoList;
    }

    public List<String> getErrors() {
        return errors;
    }

    // Method to find duplicates and return a mapping of PlayerResult to sheet names
    public static Map<PlayerResult, List<String>> listDuplicateUsingSet(List<SheetDto> sheetDtoList) {
        Map<PlayerResult, String> playerToSheetMap = new HashMap<>(); // Maps PlayerResult to the first sheet it appears in
        Map<PlayerResult, List<String>> duplicatesMap = new HashMap<>(); // Maps PlayerResult to all sheets where it appears

        for (SheetDto sheet : sheetDtoList) {
            String sheetName = sheet.sheetName();

            for (PlayerResult playerResult : sheet.playerList()) {
                // If playerResult is already in the map, we have a duplicate
                if (playerToSheetMap.containsKey(playerResult)) {
                    // Add the current sheet name to the duplicates map
                    duplicatesMap.computeIfAbsent(playerResult, k -> new ArrayList<>())
                            .add(playerToSheetMap.get(playerResult)); // Add the original sheet
                    duplicatesMap.get(playerResult).add(sheetName); // Add the current sheet
                } else {
                    // Map this playerResult to the current sheet name
                    playerToSheetMap.put(playerResult, sheetName);
                }
            }
        }

        return duplicatesMap; // Return all duplicates found
    }

    public void checkForDuplicates(List<SheetDto> sheetDtoList) {
        Map<PlayerResult, List<String>> duplicates = listDuplicateUsingSet(sheetDtoList);

        if (!duplicates.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Duplications présentes dans les feuilles:\n");

            // Build the error message for logging
            for (Map.Entry<PlayerResult, List<String>> entry : duplicates.entrySet()) {
                PlayerResult playerResult = entry.getKey();
                List<String> sheets = entry.getValue();
                errorMessage.append("Player: ").append(playerResult.playerName())
                        .append(" found in sheets: ").append(String.join(", ", sheets))
                        .append("\n");
            }

            // Log the error with details of duplicates
            logger.error(errorMessage.toString());

            // Optionally throw an exception or handle error as needed
            // throw new YourCustomException("Duplicate PlayerResult found: " + errorMessage);
        }
    }


}


