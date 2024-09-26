package com.bigbeard.yatzystats.core.config.writers;

public record RangeFormulaSpec(String formulaStartIndex, String formulaEndIndex) {

    public RangeFormulaSpec(String columnName, int formulaVerticalStartIndex, int formulaVerticalEndIndex) {
        this(columnName + formulaVerticalStartIndex, columnName + formulaVerticalEndIndex);
    }

    public String defineFormulaFromSpec(String formulaName) {
        return formulaName + "(" + formulaStartIndex + ":" + formulaEndIndex + ")";
    }
}
