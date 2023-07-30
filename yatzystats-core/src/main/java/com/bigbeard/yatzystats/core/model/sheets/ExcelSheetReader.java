package com.bigbeard.yatzystats.core.model.sheets;

import com.bigbeard.yatzystats.core.exceptions.CellNotFoundException;
import com.bigbeard.yatzystats.core.model.rules.GameRules;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

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
            Cell cell = facade.readCell(this.sheet, this.gameRules.getYahtzee().getSheetIndex().intValue(), playerIndex);
            int yatzyVal = (int) cell.getNumericCellValue();

            //On s'assure que la ligne du yathzee est bien valuée correctement, soit à 0, soit à la yatzy_value
            if(yatzyVal != 0 && yatzyVal != this.gameRules.getYahtzee().getMaxValue().intValue()) throw new Exception();
            return yatzyVal;
        } catch(Exception e){
            throw new CellNotFoundException("yatzy", this.gameRules.getYahtzee().getSheetIndex().intValue());
        }
    }

    @Override
    public Integer readBonus(String targetName) throws CellNotFoundException {
        final int playerIndex = facade.findPlayerIndex(this.sheet, targetName);
        try {
            Cell cell = facade.readCell(this.sheet, this.gameRules.getPartialSum().getSheetIndex().intValue(), playerIndex);
            return (int) cell.getNumericCellValue();
        } catch(Exception e){
            throw new CellNotFoundException("bonus", this.gameRules.getPartialSum().getSheetIndex().intValue());
        }
    }

    @Override
    public Integer readScore(String targetName) throws CellNotFoundException {
        final int playerIndex = facade.findPlayerIndex(this.sheet, targetName);
        try {
            Cell cell = facade.readCell(this.sheet, this.gameRules.getFinalSum().getSheetIndex().intValue(), playerIndex);
            return (int) cell.getNumericCellValue();
        } catch(Exception e){
            throw new CellNotFoundException("score", this.gameRules.getFinalSum().getSheetIndex().intValue());
        }
    }

}
