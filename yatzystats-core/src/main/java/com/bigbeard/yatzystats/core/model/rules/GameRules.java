package com.bigbeard.yatzystats.core.model.rules;

import com.bigbeard.yatzystats.core.model.dto.ColumnDescription;
import com.bigbeard.yatzystats.core.model.dto.ColumnDescriptionDTO;
import com.bigbeard.yatzystats.core.model.dto.GameRulesDTO;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record GameRules(String formatId, String formatDescription, Long bonusCond, Long bonusVal,
                        Map<String, ColumnDescription> combinationsByIdentifier) {

    @Override
    public String toString() {
        return formatDescription;
    }

    public GameRules(GameRulesDTO gameRulesDTO) {
        this(gameRulesDTO.formatId(),
                gameRulesDTO.formatDescription(),
                gameRulesDTO.bonus().bonusCond(),
                gameRulesDTO.bonus().bonusValue(),
                createCombinationsByIdentifier(gameRulesDTO.values(), gameRulesDTO.combinations()));
    }

    public ColumnDescription getColumnWithIdentfier(GameRulesEnum gameRulesEnum) {
        return combinationsByIdentifier.get(gameRulesEnum.getValue());
    }

    // Method to get a List<ColumnDescription> from the combinationsByIdentifier Map
    public List<ColumnDescription> getColumnDescriptionsFromMap() {

        // Extract the values from the map and collect them into a List
        return combinationsByIdentifier
                .values()
                .stream()
                .sorted(Comparator.comparingLong(ColumnDescription::sheetIndex))
                .toList();
    }

    // Method to create Map<String, ColumnDescription> from values and combinations
    private static Map<String, ColumnDescription> createCombinationsByIdentifier(
            List<ColumnDescriptionDTO> values,
            List<ColumnDescriptionDTO> combinations) {

        // Combine both lists and collect them into a Map with techColumnId as the key
        return Stream.concat(values.stream(), combinations.stream())
                .filter(Objects::nonNull) // Avoid potential nulls
                .collect(Collectors.toMap(
                        ColumnDescriptionDTO::techColumnId,
                        ColumnDescription::new,
                        (existing, replacement) -> existing)); // Handle duplicate keys (keep the existing)
    }
}

