package com.bigbeard.yatzystats.config.writers;

import com.bigbeard.yatzystats.core.rules.ColumnDescription;
import com.bigbeard.yatzystats.core.rules.GameRules;
import com.bigbeard.yatzystats.core.rules.GameRulesEnum;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

        // Players
        String[] arr = new String[this.playerNames.size() + 1];
        arr[0] = "Combinaisons";
        int index = 0;
        for (String playerName: this.playerNames) {
            arr[++index] = playerName;
        }
        this.createRowFromValues(sheet, 0, false, arr);
        for (ColumnDescription columnDescription : rules.getColumnsList()) {
            GameRulesEnum rulesEnum = GameRulesEnum.fromValue(columnDescription.getTechColumnLabel());
            switch (rulesEnum) {
                case PARTIAL_SUM:
                    String[] partArr = new String[this.playerNames.size() + 1];
                    partArr[0] = columnDescription.getColumnLabel();
                    int indexPart = 1;
                    Integer startIndexP = rules.getAces().getSheetIndex().intValue() + 1;
                    Integer endIndexP = rules.getSixes().getSheetIndex().intValue() + 1;

                    for (String playerName: this.playerNames) {
                        int asciiCode = indexPart + 65;
                        String columnLabel = Character.toString ((char) asciiCode);
                        String sumFormula = this.writeSumFormula(columnLabel,
                                startIndexP.toString(), endIndexP.toString());
                        String totalFormula = "IF("+ sumFormula + ">" + (rules.getBonusCond() - 1) + ",";
                        totalFormula += sumFormula + "+" + rules.getBonusVal() + ",";
                        totalFormula += sumFormula;
                        totalFormula += ")";
                        partArr[indexPart] = totalFormula;
                        indexPart++;
                    }
                    this.createRowFromValues(sheet, columnDescription.getSheetIndex().intValue(), true, partArr);
                    break;

                case FINAL_SUM:
                    String[] totArr = new String[this.playerNames.size() + 1];
                    totArr[0] = columnDescription.getColumnLabel();
                    int indexTot = 1;
                    Integer startIndex = rules.getPartialSum().getSheetIndex().intValue() + 1;
                    Integer endIndex = rules.getFinalSum().getSheetIndex().intValue();

                    for (String playerName: this.playerNames) {
                        int asciiCode = indexTot + 65;
                        String columnLabel = Character.toString ((char) asciiCode);
                        String sumFormula = this.writeSumFormula(columnLabel, startIndex.toString(), endIndex.toString());
                        totArr[indexTot] = sumFormula;
                        indexTot++;
                    }
                    this.createRowFromValues(sheet, columnDescription.getSheetIndex().intValue(), true, totArr);
                    break;

                default:
                    this.createRowFromValues(sheet, columnDescription.getSheetIndex().intValue(), false, columnDescription.getColumnLabel());
                    break;
            }
        }

        FileOutputStream fos = new FileOutputStream(this.destFile);
        workbook.write(fos);
    }

    public String writeSumFormula(String columnName, String begin, String end) {
        String sumCol = "SUM(" + columnName + begin + ":" + columnName + end + ")";
        return sumCol;
    }

    private XSSFSheet createRowFromValues(XSSFSheet sheet, int rowIndex, boolean isFormulaRow, String... strings) {
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
