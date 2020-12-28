package com.bigbeard.yatzystats.config.writers;

public class FormulaSpec {
    String formulaColumnName;
    int formulaVerticalStartIndex;
    int formulaVerticalEndIndex;

    public FormulaSpec(String formulaColumnName, int formulaVerticalStartIndex, int formulaVerticalEndIndex) {
        this.formulaColumnName = formulaColumnName;
        this.formulaVerticalStartIndex = formulaVerticalStartIndex;
        this.formulaVerticalEndIndex = formulaVerticalEndIndex;
    }

    public String toRangeString() {
        return this.formulaColumnName + this.formulaVerticalStartIndex +
                ":" + this.formulaColumnName + this.formulaVerticalEndIndex;
    }
}
