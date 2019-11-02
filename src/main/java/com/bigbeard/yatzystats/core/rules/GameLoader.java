package com.bigbeard.yatzystats.core.rules;


import com.bigbeard.yatzystats.core.players.PlayerResult;
import com.bigbeard.yatzystats.core.sheets.ExcelSheetReader;
import com.bigbeard.yatzystats.core.sheets.SheetDto;
import com.bigbeard.yatzystats.core.sheets.SheetReader;
import com.bigbeard.yatzystats.exceptions.CellNotFoundException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * La responsabilité de cet objet est de charger les bonnes règles selon les règles du jeu
 *
 */
public class GameLoader {

    FormulaEvaluator evaluator;
    GameRules rules;
    List<String> errors = new ArrayList<String>();

    public GameLoader(GameRules rules, FormulaEvaluator formulaEvaluator){
        this.rules = rules;
        this.evaluator = formulaEvaluator;
    }

    public List<SheetDto> loadGamesFromMode(List<Sheet> excelSheets){
        List<SheetDto> sheetDtoList = new ArrayList<SheetDto>();
        for(Sheet s : excelSheets){
            SheetReader sheetReader = new ExcelSheetReader(this.rules, s, this.evaluator);

            try {
                List<String> players = sheetReader.readPlayerNames();
                List<PlayerResult> playerResults = new ArrayList<PlayerResult>();

                for(String playerName : players){
                    PlayerResult playerResult = new PlayerResult();
                    playerResult.setPlayerName(playerName);

                    if(rules.getScoreRow() != null){
                        int playerScore = sheetReader.readScore(playerName);
                        playerResult.setScore(playerScore);
                    }

                    if(rules.getYatzyRow() != null){
                        playerResult.setHasYatzy(sheetReader.readYatzy(playerName) == this.rules.getYatzyValue());
                    }

                    if(rules.getBonusRow() != null){
                        playerResult.setHasBonus(sheetReader.readBonus(playerName) == this.rules.getBonusValue());
                    }
                    //Ne pas ajouter la partie d'un joueur qui a 0 dans son score
                    if(playerResult.getScore() > 0)
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
                System.err.println(error);
                this.errors.add(error);
            }

        }
        return sheetDtoList;
    }

    public List<String> getErrors() {
        return errors;
    }

    //    //FIXME : Where to do this?
//    public Map<String, Integer> getPlayersByGameNumber(List<Sheet> sheets) {
//        Map<String, Integer> playersByGameNumber = new HashMap<>();
//        for(Sheet s : sheets){
//            List<String> players = new ExcelSheetFacade().reachPlayersList(s);
//            for(String player : players){
//                if(playersByGameNumber.get(player) == null) playersByGameNumber.put(player, 1);
//                else {
//                    int nbGames = playersByGameNumber.get(player);
//                    playersByGameNumber.put(player, ++nbGames);
//                }
//            }
//        }
//        return playersByGameNumber;
//    }




}
