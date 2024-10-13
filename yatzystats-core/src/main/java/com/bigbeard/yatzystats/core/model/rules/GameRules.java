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

    private ColumnDescription getColumnValueWithTechColumnId(GameRulesEnum gameRulesEnum) {
        return values.stream()
                .filter(columnDescription -> gameRulesEnum.getValue().equals(columnDescription.getTechColumnId()))
                .findFirst()
                .orElse(null);
    }

    private ColumnDescription getColumnCombinationWithTechColumnId(GameRulesEnum gameRulesEnum) {
        return combinations.stream()
                .filter(columnDescription -> gameRulesEnum.getValue().equals(columnDescription.getTechColumnId()))
                .findFirst()
                .orElse(null);
    }


    // TODO: Use a mapper object to improve readibility and mapping efficience
    public ColumnDescription getAces() {
        return getColumnValueWithTechColumnId(GameRulesEnum.ACES);
    }

    public ColumnDescription getTwos() {
        return getColumnValueWithTechColumnId(GameRulesEnum.TWOS);
    }

    public ColumnDescription getThrees() {
        return getColumnValueWithTechColumnId(GameRulesEnum.THREES);
    }

    public ColumnDescription getFours() {
        return getColumnValueWithTechColumnId(GameRulesEnum.FOURS);
    }

    public ColumnDescription getFives() {
        return getColumnValueWithTechColumnId(GameRulesEnum.FIVES);
    }

    public ColumnDescription getSixes() {
        return getColumnValueWithTechColumnId(GameRulesEnum.SIXES);
    }

    public ColumnDescription getPartialSum() {
        return getColumnValueWithTechColumnId(GameRulesEnum.PARTIAL_SUM);
    }

    public ColumnDescription getYahtzee() {
        return getColumnCombinationWithTechColumnId(GameRulesEnum.YAHTZEE);
    }

    public ColumnDescription getFinalSum() {
        return getColumnCombinationWithTechColumnId(GameRulesEnum.FINAL_SUM);
    }

}
