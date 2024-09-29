package com.bigbeard.yatzystats.core.model.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public record GameRules(String formatId, String formatDescription, Bonus bonus,
                              List<ColumnDescription> values, List<ColumnDescription> combinations) {

    @Override
    public String toString() {
        return this.formatDescription;
    }

    public Long getBonusCond() {
        return this.bonus.bonusCond();
    }

    public Long getBonusVal() {
        return this.bonus.bonusValue();
    }

    public List<ColumnDescription> getColumnsList() {
        List<ColumnDescription> descriptions = new ArrayList<>();
        descriptions.addAll(values);
        descriptions.addAll(combinations);
        return descriptions.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public ColumnDescription getAces() {
        return values.stream()
                .filter(columnDescription -> GameRulesEnum.ACES.getValue().equals(columnDescription.getTechColumnId()))
                .findFirst()
                .orElse(null);
    }

    public ColumnDescription getSixes() {
        return values.stream()
                .filter(columnDescription -> GameRulesEnum.SIXES.getValue().equals(columnDescription.getTechColumnId()))
                .findFirst()
                .orElse(null);
    }

    public ColumnDescription getPartialSum() {
        return values.stream()
                .filter(columnDescription -> GameRulesEnum.PARTIAL_SUM.getValue().equals(columnDescription.getTechColumnId()))
                .findFirst()
                .orElse(null);
    }

    public ColumnDescription getYahtzee() {
        return combinations.stream()
                .filter(columnDescription -> GameRulesEnum.YAHTZEE.getValue().equals(columnDescription.getTechColumnId()))
                .findFirst()
                .orElse(null);
    }

    public ColumnDescription getFinalSum() {
        return combinations.stream()
                .filter(columnDescription -> GameRulesEnum.FINAL_SUM.getValue().equals(columnDescription.getTechColumnId()))
                .findFirst()
                .orElse(null);
    }

}
