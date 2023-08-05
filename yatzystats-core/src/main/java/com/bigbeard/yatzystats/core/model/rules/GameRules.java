package com.bigbeard.yatzystats.core.model.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class Bonus {
    Long bonusCond;
    Long bonusValue;
}

public class GameRules {

    private String formatName;

    //Bonus
    private Bonus bonus;

    // Values
    private List<ColumnDescription> values = new ArrayList<>();

    // Combinations
    private List<ColumnDescription> combinations = new ArrayList<>();

    public GameRules() {
    }

    @Override
    public String toString() {
        String rulesStr = "GameRules : " + System.lineSeparator();
        rulesStr += "BonusRow : " + this.getPartialSum().getSheetIndex() + System.lineSeparator();
        rulesStr += "BonusCond : " + this.bonus.bonusCond + System.lineSeparator();
        rulesStr += "BonusVal : " + this.bonus.bonusValue + System.lineSeparator();
        rulesStr += "Sixes TechColumnId : " + this.getSixes().getTechColumnId() + System.lineSeparator();
        return rulesStr;
    }

    public List<ColumnDescription> getColumnsList() {
        List<ColumnDescription> descriptions = new ArrayList<>();
        descriptions.addAll(values);
        descriptions.addAll(combinations);
        return descriptions.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public String getFormatName() {
        return formatName;
    }

    public Long getBonusCond() {
        return this.bonus.bonusCond;
    }

    public Long getBonusVal() {
        return this.bonus.bonusValue;
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
