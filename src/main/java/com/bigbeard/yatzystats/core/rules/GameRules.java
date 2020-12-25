package com.bigbeard.yatzystats.core.rules;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GameRules {

    private String formatName;

    private Long bonusCond;
    private Long bonusVal;

    // Values
    private List<ColumnDescription> values = new ArrayList<>();

    // Combinations
    private List<ColumnDescription> combinations = new ArrayList<>();

    public GameRules() {
    }

    public GameRules(JSONObject obj) {
        // General infos
        this.formatName = (String) obj.get("formatName");
        this.bonusCond = (Long) ((JSONObject) obj.get("bonus")).get("bonusCond");
        this.bonusVal = (Long) ((JSONObject) obj.get("bonus")).get("bonusValue");

        // Column description
        JSONArray valuesArray = (JSONArray) obj.get("values");
        for (Object value : valuesArray) {
            JSONObject valueObj = (JSONObject) value;
            values.add(ColumnDescription.fromJson(valueObj));
        }

        // Column description
        JSONArray combinationsArray = (JSONArray) obj.get("combinations");
        for (Object o : combinationsArray) {
            JSONObject valueObj = (JSONObject) o;
            combinations.add(ColumnDescription.fromJson(valueObj));
        }
    }

    @Override
    public String toString() {
        String rulesStr = "GameRules : " + System.lineSeparator();
        rulesStr += "BonusRow : " + this.getPartialSum().getSheetIndex() + System.lineSeparator();
        rulesStr += "BonusVal : " + this.bonusVal + System.lineSeparator();
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
        return bonusCond;
    }

    public Long getBonusVal() {
        return bonusVal;
    }

    public ColumnDescription getAces() {
        return values.stream()
                .filter(columnDescription -> GameRulesEnum.ACES.getValue().equals(columnDescription.getTechColumnLabel()))
                .findFirst()
                .orElse(null);
    }

    public ColumnDescription getSixes() {
        return values.stream()
                .filter(columnDescription -> GameRulesEnum.SIXES.getValue().equals(columnDescription.getTechColumnLabel()))
                .findFirst()
                .orElse(null);
    }

    public ColumnDescription getPartialSum() {
        return values.stream()
                .filter(columnDescription -> GameRulesEnum.PARTIAL_SUM.getValue().equals(columnDescription.getTechColumnLabel()))
                .findFirst()
                .orElse(null);
    }

    public ColumnDescription getYahtzee() {
        return combinations.stream()
                .filter(columnDescription -> GameRulesEnum.YAHTZEE.getValue().equals(columnDescription.getTechColumnLabel()))
                .findFirst()
                .orElse(null);
    }

    public ColumnDescription getFinalSum() {
        return combinations.stream()
                .filter(columnDescription -> GameRulesEnum.FINAL_SUM.getValue().equals(columnDescription.getTechColumnLabel()))
                .findFirst()
                .orElse(null);
    }

}
