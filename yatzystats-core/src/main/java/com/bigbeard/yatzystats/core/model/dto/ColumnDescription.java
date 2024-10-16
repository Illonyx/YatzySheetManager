package com.bigbeard.yatzystats.core.model.dto;

import java.util.Optional;

public record ColumnDescription(String techColumnId, String columnLabel, Long sheetIndex, Long maxValue, Boolean fixedValue) {
    public ColumnDescription(ColumnDescriptionDTO columnDescriptionDTO) {
        this(columnDescriptionDTO.techColumnId(),
                columnDescriptionDTO.columnLabel(),
                columnDescriptionDTO.sheetIndex(),
                columnDescriptionDTO.maxValue(),
                columnDescriptionDTO.fixedValue() != null);
    }
}
