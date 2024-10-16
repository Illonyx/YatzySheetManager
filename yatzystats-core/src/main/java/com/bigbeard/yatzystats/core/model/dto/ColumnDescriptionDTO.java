package com.bigbeard.yatzystats.core.model.dto;

import java.util.Optional;

public record ColumnDescriptionDTO(String techColumnId, String columnLabel, Long sheetIndex, Long maxValue, Boolean fixedValue) {

}
