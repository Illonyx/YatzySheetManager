package com.bigbeard.yatzystats.core.sheets;

import com.bigbeard.yatzystats.core.rules.GameRules;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

public class ExcelSheetReader implements SheetReader {

    private GameRules gameRules;
    private Sheet sheet;
    private FormulaEvaluator evaluator;
    private ExcelSheetFacade facade;
    public final double UNEVALUABLE_CRITERIA = -1.0;

    public ExcelSheetReader(GameRules gameRules, Sheet sheet, FormulaEvaluator evaluator){
        this.gameRules = gameRules;
        this.sheet = sheet;
        this.evaluator = evaluator;
        this.facade = new ExcelSheetFacade();
    }

    @Override
    public double readYatzy(String targetName) {
        final int playerIndex = facade.findPlayerIndex(this.sheet, targetName);
        Cell cell = facade.readCell(this.sheet, this.gameRules.getYatzyRow(), playerIndex);
        return (cell != null) ? cell.getNumericCellValue() : UNEVALUABLE_CRITERIA;
    }

    @Override
    public double readBonus(String targetName) {
        final int playerIndex = facade.findPlayerIndex(this.sheet, targetName);
        Cell cell = facade.readCell(this.sheet, this.gameRules.getBonusRow(), playerIndex);
        return (cell != null) ? cell.getNumericCellValue() : UNEVALUABLE_CRITERIA;
    }

    @Override
    public double readScore(String targetName) {
        final int playerIndex = facade.findPlayerIndex(this.sheet, targetName);
        Cell cell = facade.readCell(this.sheet, this.gameRules.getScoreRow(), playerIndex);
        return (cell != null) ? cell.getNumericCellValue() : UNEVALUABLE_CRITERIA;
    }

    //FIXME : Pas de gestion du cas à null dans méthode readBestScore
    @Override
    public double readBestScore() {
        double bestScore = 0.0;

        List<Cell> scoreCells = facade.readCells(this.sheet, this.gameRules.getScoreRow());
        for(Cell c : scoreCells){
            double valueEvaluated = this.evaluator.evaluate(c).getNumberValue();
            bestScore = Math.max(bestScore, valueEvaluated);
        }

        return bestScore;
    }

    @Override
    public Boolean readIsPlayerWinning(String targetName) {
        return this.readScore(targetName) == this.readBestScore();
    }
}
