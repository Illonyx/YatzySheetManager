package com.bigbeard.yatzystats.config;

import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExcelSheetLoader {

    private List<Sheet> allSheets = new ArrayList<Sheet>();
    private Workbook workbook;

    public ExcelSheetLoader(String filePath) throws IOException {
        File file = new File(filePath);
        this.workbook = WorkbookFactory.create(file);

        for(int i=0; i<this.workbook.getNumberOfSheets(); i++){
            Sheet sheet = this.workbook.getSheetAt(i);
            allSheets.add(sheet);
        }
    }

    public FormulaEvaluator getFormulaEvaluator(){
        return this.workbook.getCreationHelper().createFormulaEvaluator();
    }

    /**
     * Allows to return sheet names from a sheet list
     * @param sheets
     * @return sheet names
     */
    public List<String> getAllSheetNames(List<Sheet> sheets) {
        return sheets.stream().map(Sheet::getSheetName).collect(Collectors.toList());
    }

    /**
     * Returns a list of sheet objects found by the loader
     * @return sheets
     */
    public List<Sheet> getAllSheets(){
        return this.allSheets;
    }

}
