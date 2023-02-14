package com.bigbeard.yatzystats.core.rules;

import org.json.simple.JSONObject;

public class ColumnDescription {
    private String techColumnId;
    private String columnLabel;
    private Long sheetIndex, maxValue;
    private boolean fixedValue = false;

    public ColumnDescription(String techColumnId, String columnLabel, Long sheetIndex, Long maxValue) {
        this.techColumnId = techColumnId;
        this.columnLabel = columnLabel;
        this.sheetIndex = sheetIndex;
        this.maxValue = maxValue;
    }

    public static ColumnDescription fromJson(JSONObject obj) {
        String techColumnId = (String) obj.get("techColumnId");
        String columnLabel = (String) obj.get("columnLabel");
        Long sheetIndex = (Long) obj.get("sheetIndex") - 1;
        Long maxValue = (Long) obj.get("maxValue");
        ColumnDescription columnToReturn =
                new ColumnDescription(techColumnId, columnLabel, sheetIndex, maxValue);
        if (obj.get("fixedValue") != null) columnToReturn.setFixedValue(true);
        return columnToReturn;
    }

    public String getTechColumnId() {
        return techColumnId;
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
