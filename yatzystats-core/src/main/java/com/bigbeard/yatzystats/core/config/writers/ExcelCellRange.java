package com.bigbeard.yatzystats.core.config.writers;

import org.apache.poi.ss.util.CellReference;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public class ExcelCellRange {

    private final int startHorizontalIndex;
    private final int endHorizontalIndex;

    public ExcelCellRange(int startHorizontalIndex, int endHorizontalIndex) {
        this.startHorizontalIndex = startHorizontalIndex;
        this.endHorizontalIndex = endHorizontalIndex;
    }

    public List<String> processFormulaRowFill(int formulaVerticalStartIndex, int formulaVerticalEndIndex,
                                              Function<RangeFormulaSpec, String> formulaFonc) {
        return IntStream.range(startHorizontalIndex, endHorizontalIndex).mapToObj(i -> {
            var formulaSpec = new RangeFormulaSpec(CellReference.convertNumToColString(i),
                    formulaVerticalStartIndex, formulaVerticalEndIndex);
            return formulaFonc.apply(formulaSpec);
        }).toList();
    }
}
