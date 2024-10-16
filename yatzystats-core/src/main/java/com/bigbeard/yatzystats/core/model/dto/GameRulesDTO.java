package com.bigbeard.yatzystats.core.model.dto;

import java.util.List;

public record GameRulesDTO(String formatId, String formatDescription, Bonus bonus,
                           List<ColumnDescriptionDTO> values, List<ColumnDescriptionDTO> combinations) {
}
