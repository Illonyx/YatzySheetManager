package com.bigbeard.yatzystats.core.model.rules;


import com.bigbeard.yatzystats.core.exceptions.CellNotFoundException;
import com.bigbeard.yatzystats.core.exceptions.FileNotLoadedException;
import com.bigbeard.yatzystats.core.model.players.PlayerResult;
import com.bigbeard.yatzystats.core.model.sheets.ExcelSheetReader;
import com.bigbeard.yatzystats.core.model.sheets.SheetDto;
import com.bigbeard.yatzystats.core.model.sheets.SheetReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * La responsabilité de cet objet est de charger les bonnes règles selon les règles du jeu
 */
public class GameLoader {

    FormulaEvaluator evaluator;
    GameRules rules;
    List<String> errors = new ArrayList<String>();
    private final Logger logger = LogManager.getLogger(GameLoader.class);

    public GameLoader(GameRules rules, FormulaEvaluator formulaEvaluator) {
        this.rules = rules;
        this.evaluator = formulaEvaluator;
    }

    public List<SheetDto> loadGamesFromMode(List<Sheet> excelSheets) throws FileNotLoadedException {
        logger.info("Chargement des parties avec les règles suivantes :" + this.rules);

        List<SheetDto> sheetDtoList = new ArrayList<SheetDto>();
        for (Sheet s : excelSheets) {
            SheetReader sheetReader = new ExcelSheetReader(this.rules, s, this.evaluator);

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
                }).collect(Collectors.toList());

                SheetDto sheetDto = new SheetDto(s.getSheetName(), playerResults, bestScore);
                sheetDtoList.add(sheetDto);

            } catch (CellNotFoundException ex) {
                String error = "Feuille non chargée : " + s.getSheetName() + " - raison :" + ex.getCellLabel();
                logger.error(error);
                this.errors.add(error);
            } catch (Exception ex) {
                logger.error("Error" + ex);
                throw new FileNotLoadedException("Une erreur est survenue lors de la lecture du fichier", ex.getMessage());
            }

        }

        logger.info("Nombre de parties chargées : " + sheetDtoList.size() + "/" + excelSheets.size());
        if (sheetDtoList.isEmpty())
            throw new FileNotLoadedException("Le nombre de parties chargées est de 0", "Vérifiez bien si le format des parties enregistrées dans le fichier Excel est cohérent avec celui des règles utilisées");
        return sheetDtoList;
    }

    public List<String> getErrors() {
        return errors;
    }

}


