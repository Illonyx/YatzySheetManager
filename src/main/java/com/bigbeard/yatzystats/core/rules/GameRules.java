package com.bigbeard.yatzystats.core.rules;

import org.json.simple.JSONObject;

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
        this.aces = ColumnDescription.fromJson((JSONObject) values.get("aces"));
        this.twos = ColumnDescription.fromJson((JSONObject) values.get("twos"));
        this.threes = ColumnDescription.fromJson((JSONObject) values.get("threes"));
        this.fours = ColumnDescription.fromJson((JSONObject) values.get("fours"));
        this.fives = ColumnDescription.fromJson((JSONObject) values.get("fives"));
        this.sixes = ColumnDescription.fromJson((JSONObject) values.get("sixes"));
        this.partialSum = ColumnDescription.fromJson((JSONObject) values.get("partialSum"));

        JSONObject combinations = (JSONObject) obj.get("combinations");
        this.pair = ColumnDescription.fromJson((JSONObject) combinations.get("pair"));
        this.doublePair = ColumnDescription.fromJson((JSONObject) combinations.get("doublePair"));
        this.threeOfAKind = ColumnDescription.fromJson((JSONObject) combinations.get("threeOfAKind"));
        this.fourOfAKind = ColumnDescription.fromJson((JSONObject) combinations.get("fourOfAKind"));
        this.fullHouse = ColumnDescription.fromJson((JSONObject) combinations.get("fullHouse"));

        this.smallStraight = ColumnDescription.fromJson((JSONObject) combinations.get("smallStraight"));
        this.largeStraight = ColumnDescription.fromJson((JSONObject) combinations.get("largeStraight"));
        this.yahtzee = ColumnDescription.fromJson((JSONObject) combinations.get("yahtzee"));
        this.chance = ColumnDescription.fromJson((JSONObject) combinations.get("chance"));
        this.finalSum = ColumnDescription.fromJson((JSONObject) combinations.get("finalSum"));
    }

    @Override
    public String toString() {
        String rulesStr = "GameRules : " + System.lineSeparator();
        rulesStr += "BonusRow : " + this.partialSum.getSheetIndex() + System.lineSeparator();
        rulesStr += "BonusVal : " + this.bonusVal + System.lineSeparator();
        return rulesStr;
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
