package com.bigbeard.yatzystats.core.config.loaders.excel;

import com.bigbeard.yatzystats.core.exceptions.CellNotFoundException;
import com.bigbeard.yatzystats.core.model.dto.ColumnDescription;
import com.bigbeard.yatzystats.core.model.rules.GameRules;
import com.bigbeard.yatzystats.core.model.rules.GameRulesEnum;
import com.bigbeard.yatzystats.core.model.sheets.SheetReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

public class ExcelSheetReader implements SheetReader {

    private final GameRules gameRules;
    private final Sheet sheet;
    private final FormulaEvaluator evaluator;
    private ExcelSheetFacade facade;

    private Logger logger = LogManager.getLogger(ExcelSheetReader.class);

    public ExcelSheetReader(GameRules gameRules, Sheet sheet, FormulaEvaluator evaluator){
        this.gameRules = gameRules;
        this.sheet = sheet;
        this.evaluator = evaluator;
        this.facade = new ExcelSheetFacade();
    }

    @Override
    public String getSheetName() {
        return this.sheet.getSheetName();
    }

    @Override
    public List<String> readPlayerNames() throws CellNotFoundException {
        return new ExcelSheetFacade().getPlayersList(this.sheet);
    }

    @Override
    public Integer readRuleScore(String targetName, GameRulesEnum rulesEnum) throws CellNotFoundException {
        return readAnyCellValue(targetName, this.gameRules.getColumnWithIdentfier(rulesEnum));
    }

    private Integer readAnyCellValue(String targetPlayer, ColumnDescription columnDescription) throws CellNotFoundException {
        final int playerIndex = facade.findPlayerIndex(this.sheet, targetPlayer);
        try {
            Cell cell = facade.readCell(this.sheet, columnDescription.sheetIndex().intValue(), playerIndex);
            int cellValue = (int) cell.getNumericCellValue();
            if(!isCellValueValid(cellValue, columnDescription)) throw new Exception();
            return cellValue;
        } catch(Exception e) {
            throw new CellNotFoundException(columnDescription.columnLabel(), columnDescription.sheetIndex().intValue());
        }
    }

    private boolean isCellValueValid(int cellValue, ColumnDescription columnDescription) {
        if(columnDescription.fixedValue()) {
            return cellValue == 0 || cellValue == columnDescription.maxValue();
        } else {
            return cellValue >= 0 && cellValue <= columnDescription.maxValue();
        }
    }
}
