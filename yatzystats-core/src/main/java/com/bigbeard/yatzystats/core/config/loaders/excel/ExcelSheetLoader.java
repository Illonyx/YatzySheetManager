package com.bigbeard.yatzystats.core.config.loaders.excel;

import com.bigbeard.yatzystats.core.model.rules.GameRules;
import com.bigbeard.yatzystats.core.model.sheets.SheetLoader;
import com.bigbeard.yatzystats.core.model.sheets.SheetReader;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExcelSheetLoader implements SheetLoader {

    private final List<Sheet> allSheets;
    private final FormulaEvaluator formulaEvaluator;

    public ExcelSheetLoader(String filePath) throws IOException {
        try (Workbook workbook = WorkbookFactory.create(new File(filePath))) {
            this.allSheets = new ArrayList<>();
            workbook.sheetIterator().forEachRemaining(allSheets::add);
            this.formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
        }
    }

    public FormulaEvaluator getFormulaEvaluator(){
        return this.formulaEvaluator;
    }

    /**
     * Allows to return sheet names from a sheet list
     * @param sheets
     * @return sheet names
     */
    public List<String> getAllSheetNames(List<Sheet> sheets) {
        return sheets.stream().map(Sheet::getSheetName).toList();
    }

    /**
     * Returns a list of sheet objects found by the loader
     * @return sheets
     */
    public List<Sheet> getAllSheets(){
        return this.allSheets;
    }

    @Override
    public List<SheetReader> createSheetReaders(GameRules gameRules) {
        return this.allSheets.stream().map(sheet -> new ExcelSheetReader(gameRules, sheet, this.getFormulaEvaluator())).collect(Collectors.toList());
    }

    @Override
    public Integer getSheetNumber(){
        return this.allSheets.size();
    }
}
