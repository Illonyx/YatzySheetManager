package com.bigbeard.yatzystats.core.config.writers;

import com.bigbeard.yatzystats.core.model.dto.ColumnDescription;
import com.bigbeard.yatzystats.core.model.rules.GameRules;
import com.bigbeard.yatzystats.core.model.rules.GameRulesEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.Function;

public class ExcelSheetWriter {

    private Workbook workbook;
    private List<String> playerNames;
    private File destFile;
    private Logger logger = LogManager.getLogger(ExcelSheetWriter.class);

    public ExcelSheetWriter(String filePath, List<String> playerNames) {
        this.destFile = new File(filePath);
        this.workbook = new XSSFWorkbook();
        this.playerNames = playerNames;
    }

    public void writeSheets(int sheetNumber, GameRules rules) throws IOException {

        for(int i=1;i<=sheetNumber;i++) {
            String sheetName = this.getSheetName(rules.formatId(), i);
            writeSheet(sheetName,rules);
        }
        workbook.close();
    }

    private String getSheetName(String formatId, int number) {
        String sheetName = formatId.trim();
        sheetName += this.getFormattedTime();
        sheetName += "(" + number + ")";
        return sheetName;
    }

    private String getFormattedTime() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        String monthFormatted = String.format("%02d", month);
        String dayOfMonthFormatted = String.format("%02d", dayOfMonth);

        return dayOfMonthFormatted + monthFormatted + year;
    }

    protected void writeSheet(String sheetName, GameRules rules) throws IOException {
        XSSFSheet sheet = (XSSFSheet) this.workbook.createSheet(sheetName);
        final int maxSizeSheet = this.playerNames.size() + 1;

        // Create players row
        List<String> playersRowData = new ArrayList<>(List.of("Combinaisons"));
        playersRowData.addAll(this.playerNames);
        this.createRowFromValues(sheet, 0, false, playersRowData);

        for (ColumnDescription columnDescription : rules.getColumnDescriptionsFromMap()) {
            List<String> rowData = new ArrayList<>(List.of(columnDescription.columnLabel()));
            boolean isFormula = false;
            GameRulesEnum rulesEnum = GameRulesEnum.fromValue(columnDescription.techColumnId());

            switch (rulesEnum) {
                case PARTIAL_SUM:
                    isFormula=true;
                    ExcelCellRange range = new ExcelCellRange(1, maxSizeSheet);
                    int startVerticalIndexP = rules.getColumnWithIdentfier(GameRulesEnum.ACES).sheetIndex().intValue() + 1;
                    int endVerticalIndexP = rules.getColumnWithIdentfier(GameRulesEnum.SIXES).sheetIndex().intValue() + 1;

                    Function<RangeFormulaSpec, String> formulaFunc = formulaSpec -> {
                        String sumFormula = this.writeSumFormula(formulaSpec);
                        return String.format("IF(%s>%d,%s+%d,%s)",
                                sumFormula,
                                rules.bonusCond() - 1,
                                sumFormula,
                                rules.bonusVal(),
                                sumFormula);
                    };
                    rowData.addAll(
                            range.processFormulaRowFill(startVerticalIndexP, endVerticalIndexP, formulaFunc)
                    );
                    break;

                case FINAL_SUM:
                    isFormula=true;
                    ExcelCellRange rangeS = new ExcelCellRange(1, maxSizeSheet);
                    int startVerticalIndexS = rules.getColumnWithIdentfier(GameRulesEnum.PARTIAL_SUM).sheetIndex().intValue() + 1;
                    int endVerticalIndexS = rules.getColumnWithIdentfier(GameRulesEnum.FINAL_SUM).sheetIndex().intValue();

                    Function<RangeFormulaSpec, String> formulaFuncS = this::writeSumFormula;
                    rowData.addAll(rangeS.
                            processFormulaRowFill(startVerticalIndexS, endVerticalIndexS, formulaFuncS));
                    break;

                default:
                    break;
            }
            this.createRowFromValues(sheet, columnDescription.sheetIndex().intValue(),
                    isFormula, rowData);
        }

        FileOutputStream fos = new FileOutputStream(this.destFile);
        workbook.write(fos);
    }

    public String writeSumFormula(RangeFormulaSpec spec) {
        return spec.defineFormulaFromSpec(ExcelFunctions.SUM.name());
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
