package com.bigbeard.yatzystats.core.rules;

import org.json.simple.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GameRules {

    private String formatName;

    private Long bonusCond;
    private Long bonusVal;

    // Values
    private ColumnDescription aces, twos, threes, fours, fives, sixes, partialSum;

    // Combinations
    private ColumnDescription pair, doublePair, threeOfAKind, fourOfAKind,
    fullHouse, smallStraight, largeStraight, yahtzee, chance, finalSum;


    public GameRules() {
    }

    public GameRules(JSONObject obj) {
        // General infos
        this.formatName = (String) obj.get("formatName");
        this.bonusCond = (Long) ((JSONObject) obj.get("bonus")).get("bonusCond");
        this.bonusVal = (Long) ((JSONObject) obj.get("bonus")).get("bonusValue");

        // Column description
        JSONObject values = (JSONObject) obj.get("values");
        this.aces = ColumnDescription.fromJson(GameRulesEnum.ACES.getValue(), (JSONObject) values.get(GameRulesEnum.ACES.getValue()));
        this.twos = ColumnDescription.fromJson(GameRulesEnum.TWOS.getValue(), (JSONObject) values.get(GameRulesEnum.TWOS.getValue()));
        this.threes = ColumnDescription.fromJson(GameRulesEnum.THREES.getValue(), (JSONObject) values.get(GameRulesEnum.THREES.getValue()));
        this.fours = ColumnDescription.fromJson(GameRulesEnum.FOURS.getValue(), (JSONObject) values.get(GameRulesEnum.FOURS.getValue()));
        this.fives = ColumnDescription.fromJson(GameRulesEnum.FIVES.getValue(), (JSONObject) values.get(GameRulesEnum.FIVES.getValue()));
        this.sixes = ColumnDescription.fromJson(GameRulesEnum.SIXES.getValue(), (JSONObject) values.get(GameRulesEnum.SIXES.getValue()));
        this.partialSum = ColumnDescription.fromJson(GameRulesEnum.PARTIAL_SUM.getValue(), (JSONObject) values.get(GameRulesEnum.PARTIAL_SUM.getValue()));

        JSONObject combinations = (JSONObject) obj.get("combinations");
        this.pair = ColumnDescription.fromJson(GameRulesEnum.PAIR.getValue(),
                (JSONObject) combinations.get(GameRulesEnum.PAIR.getValue()));
        this.doublePair = ColumnDescription.fromJson(GameRulesEnum.DOUBLE_PAIR.getValue(),
                (JSONObject) combinations.get(GameRulesEnum.DOUBLE_PAIR.getValue()));
        this.threeOfAKind = ColumnDescription.fromJson(GameRulesEnum.THREE_OF_A_KIND.getValue(),
                (JSONObject) combinations.get(GameRulesEnum.THREE_OF_A_KIND.getValue()));
        this.fourOfAKind = ColumnDescription.fromJson(GameRulesEnum.FOUR_OF_A_KIND.getValue(),
                (JSONObject) combinations.get(GameRulesEnum.FOUR_OF_A_KIND.getValue()));
        this.fullHouse = ColumnDescription.fromJson(GameRulesEnum.FULL_HOUSE.getValue(),
                (JSONObject) combinations.get(GameRulesEnum.FULL_HOUSE.getValue()));

        this.smallStraight = ColumnDescription.fromJson(GameRulesEnum.SMALL_STRAIGHT.getValue(),
                (JSONObject) combinations.get(GameRulesEnum.SMALL_STRAIGHT.getValue()));
        this.largeStraight = ColumnDescription.fromJson(GameRulesEnum.LARGE_STRAIGHT.getValue(),
                (JSONObject) combinations.get(GameRulesEnum.LARGE_STRAIGHT.getValue()));
        this.yahtzee = ColumnDescription.fromJson(GameRulesEnum.YAHTZEE.getValue(),
                (JSONObject) combinations.get(GameRulesEnum.YAHTZEE.getValue()));
        this.chance = ColumnDescription.fromJson(GameRulesEnum.CHANCE.getValue(),
                (JSONObject) combinations.get(GameRulesEnum.CHANCE.getValue()));
        this.finalSum = ColumnDescription.fromJson(GameRulesEnum.FINAL_SUM.getValue(),
                (JSONObject) combinations.get(GameRulesEnum.FINAL_SUM.getValue()));
    }

    @Override
    public String toString() {
        String rulesStr = "GameRules : " + System.lineSeparator();
        rulesStr += "BonusRow : " + this.partialSum.getSheetIndex() + System.lineSeparator();
        rulesStr += "BonusVal : " + this.bonusVal + System.lineSeparator();
        return rulesStr;
    }

    public List<ColumnDescription> getColumnsList() {
        ColumnDescription[] columnDescriptionsArr = {aces, twos, threes, fours, fives, sixes, partialSum,
        pair, doublePair, threeOfAKind, fourOfAKind, fullHouse, smallStraight, largeStraight, yahtzee,
        chance, finalSum};
        return Arrays.asList(columnDescriptionsArr).stream()
                .filter(col -> col != null)
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
        return aces;
    }

    public ColumnDescription getTwos() {
        return twos;
    }

    public ColumnDescription getThrees() {
        return threes;
    }

    public ColumnDescription getFours() {
        return fours;
    }

    public ColumnDescription getFives() {
        return fives;
    }

    public ColumnDescription getSixes() {
        return sixes;
    }

    public ColumnDescription getPartialSum() {
        return partialSum;
    }

    public ColumnDescription getPair() {
        return pair;
    }

    public ColumnDescription getDoublePair() {
        return doublePair;
    }

    public ColumnDescription getThreeOfAKind() {
        return threeOfAKind;
    }

    public ColumnDescription getFourOfAKind() {
        return fourOfAKind;
    }

    public ColumnDescription getFullHouse() {
        return fullHouse;
    }

    public ColumnDescription getSmallStraight() {
        return smallStraight;
    }

    public ColumnDescription getLargeStraight() {
        return largeStraight;
    }

    public ColumnDescription getYahtzee() {
        return yahtzee;
    }

    public ColumnDescription getChance() {
        return chance;
    }

    public ColumnDescription getFinalSum() {
        return finalSum;
    }

}
