package com.bigbeard.yatzystats.config.writers;

import com.bigbeard.yatzystats.core.rules.ColumnDescription;
import com.bigbeard.yatzystats.core.rules.GameRules;
import com.bigbeard.yatzystats.core.rules.GameRulesEnum;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ExcelSheetWriter {

    private Workbook workbook;
    private List<String> playerNames;
    private File destFile;
    private Logger logger = Logger.getLogger(ExcelSheetWriter.class);

    public ExcelSheetWriter(String filePath, List<String> playerNames) {
        this.destFile = new File(filePath);
        this.workbook = new XSSFWorkbook();
        this.playerNames = playerNames;
    }

    public void writeSheets(int sheetNumber, GameRules rules) throws IOException {

        for(int i=0;i<sheetNumber;i++) {
            String sheetName = rules.getFormatName().trim() + "(" + (i+1) + ")";
            writeSheet(sheetName,rules);
        }
        workbook.close();
    }

    protected void writeSheet(String sheetName, GameRules rules) throws IOException {
        XSSFSheet sheet = (XSSFSheet) this.workbook.createSheet(sheetName);
        final int maxSizeSheet = this.playerNames.size() + 1;

        // Create players row
        List<String> playersRowData = new ArrayList<>(List.of("Combinaisons"));
        playersRowData.addAll(this.playerNames);
        this.createRowFromValues(sheet, 0, false, playersRowData);

        for (ColumnDescription columnDescription : rules.getColumnsList()) {
            List<String> rowData = new ArrayList<>(List.of(columnDescription.getColumnLabel()));
            boolean isFormula = false;
            GameRulesEnum rulesEnum = GameRulesEnum.fromValue(columnDescription.getTechColumnLabel());

            switch (rulesEnum) {
                case PARTIAL_SUM:
                    isFormula=true;
                    ExcelCellRange range = new ExcelCellRange(1, maxSizeSheet);
                    int startVerticalIndexP = rules.getAces().getSheetIndex().intValue() + 1;
                    int endVerticalIndexP = rules.getSixes().getSheetIndex().intValue() + 1;

                    Function<FormulaSpec, String> formulaFunc = formulaSpec -> {
                        String sumFormula = this.writeSumFormula(formulaSpec);
                        String totalFormula = "IF("+ sumFormula + ">" + (rules.getBonusCond() - 1) + ",";
                        totalFormula += sumFormula + "+" + rules.getBonusVal() + ",";
                        totalFormula += sumFormula;
                        totalFormula += ")";
                        return totalFormula;
                    };
                    rowData.addAll(
                            range.processFormulaRowFill(startVerticalIndexP, endVerticalIndexP, formulaFunc)
                    );
                    break;

                case FINAL_SUM:
                    isFormula=true;
                    ExcelCellRange rangeS = new ExcelCellRange(1, maxSizeSheet);
                    int startVerticalIndexS = rules.getPartialSum().getSheetIndex().intValue() + 1;
                    int endVerticalIndexS = rules.getFinalSum().getSheetIndex().intValue();

                    Function<FormulaSpec, String> formulaFuncS = this::writeSumFormula;
                    rowData.addAll(rangeS.
                            processFormulaRowFill(startVerticalIndexS, endVerticalIndexS, formulaFuncS));
                    break;

                default:
                    break;
            }
            this.createRowFromValues(sheet, columnDescription.getSheetIndex().intValue(),
                    isFormula, rowData);
        }

        FileOutputStream fos = new FileOutputStream(this.destFile);
        workbook.write(fos);
    }

    public String writeSumFormula(FormulaSpec spec) {
        return "SUM(" + spec.toRangeString() + ")";
    }

    private XSSFSheet createRowFromValues(XSSFSheet sheet, int rowIndex, boolean isFormulaRow, List<String> strings) {
        Row playersRow = sheet.createRow(rowIndex);
        int cellIndex = 0;
        for(String str: strings) {
            Cell cell = playersRow.createCell(cellIndex);
            if(cellIndex == 0 || !isFormulaRow){
                cell.setCellValue(str);
            } else {
                cell.setCellFormula(str);
            }
            cellIndex++;
        }
        return sheet;
    }

}
