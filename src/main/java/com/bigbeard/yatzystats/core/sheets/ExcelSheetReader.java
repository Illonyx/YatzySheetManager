package com.bigbeard.yatzystats.core.sheets;

import com.bigbeard.yatzystats.core.rules.GameRules;
import com.bigbeard.yatzystats.exceptions.CellNotFoundException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelSheetReader implements SheetReader {

    private GameRules gameRules;
    private Sheet sheet;
    private FormulaEvaluator evaluator;
    private ExcelSheetFacade facade;

    public ExcelSheetReader(GameRules gameRules, Sheet sheet, FormulaEvaluator evaluator){
        this.gameRules = gameRules;
        this.sheet = sheet;
        this.evaluator = evaluator;
        this.facade = new ExcelSheetFacade();
    }

    @Override
    public List<String> readPlayerNames() throws CellNotFoundException {
        return new ExcelSheetFacade().reachPlayersList(this.sheet);
    }

    @Override
    public Integer readYatzy(String targetName) throws CellNotFoundException {
        final int playerIndex = facade.findPlayerIndex(this.sheet, targetName);
        try {
            Cell cell = facade.readCell(this.sheet, this.gameRules.getYatzyRow(), playerIndex);
            return (int) cell.getNumericCellValue();
        } catch(Exception e){
            throw new CellNotFoundException("yatzy", this.gameRules.getYatzyRow());
        }
    }

    @Override
    public Integer readBonus(String targetName) throws CellNotFoundException {
        final int playerIndex = facade.findPlayerIndex(this.sheet, targetName);
        try {
            Cell cell = facade.readCell(this.sheet, this.gameRules.getBonusRow(), playerIndex);
            return (int) cell.getNumericCellValue();
        } catch(Exception e){
            throw new CellNotFoundException("bonus", this.gameRules.getBonusRow());
        }
    }

    @Override
    public Integer readScore(String targetName) throws CellNotFoundException {
        final int playerIndex = facade.findPlayerIndex(this.sheet, targetName);
        try {
            Cell cell = facade.readCell(this.sheet, this.gameRules.getScoreRow(), playerIndex);
            return (int) cell.getNumericCellValue();
        } catch(Exception e){
            throw new CellNotFoundException("score", this.gameRules.getScoreRow());
        }
    }

}
