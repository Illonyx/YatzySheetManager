package com.bigbeard.yatzystats.core.config.writers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ExcelCellRange {

    private int startHorizontalIndex, endHorizontalIndex;
    private String currentColumnExcelName = "";

    public ExcelCellRange(int startHorizontalIndex, int endHorizontalIndex) {
        this.startHorizontalIndex = startHorizontalIndex;
        this.endHorizontalIndex = endHorizontalIndex;
    }

    public List<String> processFormulaRowFill(int formulaVerticalStartIndex, int formulaVerticalEndIndex,
                                              Function<FormulaSpec, String> formulaFonc) {
        List<String> rowFormulaValues = new ArrayList<>();
        for (int i = startHorizontalIndex; i<endHorizontalIndex; i++) {
            FormulaSpec formulaSpec = new FormulaSpec(mapCurrentPosWithExcelColumnName(i),
                    formulaVerticalStartIndex, formulaVerticalEndIndex);
            rowFormulaValues.add(formulaFonc.apply(formulaSpec));
        }
        return rowFormulaValues;
    }

    public static String mapCurrentPosWithExcelColumnName(int currentPos) {
        int asciiCode = currentPos + 65;
        return Character.toString ((char) asciiCode);
    }
}
