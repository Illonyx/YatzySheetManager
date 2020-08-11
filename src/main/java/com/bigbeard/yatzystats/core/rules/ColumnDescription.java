package com.bigbeard.yatzystats.core.rules;

import org.json.simple.JSONObject;

public class ColumnDescription {
    private String columnLabel;
    private Long sheetIndex, maxValue;
    private boolean fixedValue = false;

    public ColumnDescription(String columnLabel, Long sheetIndex, Long maxValue) {
        this.columnLabel = columnLabel;
        this.sheetIndex = sheetIndex;
        this.maxValue = maxValue;
    }

    public static ColumnDescription fromJson(JSONObject obj) {
        String columnLabel = (String) obj.get("columnLabel");
        Long sheetIndex = (Long) obj.get("sheetIndex") - 1;
        Long maxValue = (Long) obj.get("maxValue");
        ColumnDescription columnToReturn =
                new ColumnDescription(columnLabel, sheetIndex, maxValue);
        if (obj.get("fixedValue") != null) columnToReturn.setFixedValue(true);
        return columnToReturn;
    }

    public String getColumnLabel() {
        return columnLabel;
    }

    public void setColumnLabel(String columnLabel) {
        this.columnLabel = columnLabel;
    }

    public Long getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(Long sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public Long getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Long maxValue) {
        this.maxValue = maxValue;
    }

    public boolean isFixedValue() {
        return fixedValue;
    }

    public void setFixedValue(boolean fixedValue) {
        this.fixedValue = fixedValue;
    }

}
