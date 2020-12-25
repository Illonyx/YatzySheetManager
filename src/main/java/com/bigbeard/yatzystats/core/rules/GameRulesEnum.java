package com.bigbeard.yatzystats.core.rules;

public enum GameRulesEnum {
    DEFAULT_COL(""),
    ACES("aces"),
    TWOS("twos"),
    THREES("threes"),
    FOURS("fours"),
    FIVES("fives"),
    SIXES("sixes"),
    PARTIAL_SUM("partialSum"),
    PAIR("pair"),
    DOUBLE_PAIR("doublePair"),
    THREE_OF_A_KIND("threeOfAKind"),
    FOUR_OF_A_KIND("fourOfAKind"),
    FULL_HOUSE("fullHouse"),
    SMALL_STRAIGHT("smallStraight"),
    LARGE_STRAIGHT("largeStraight"),
    YAHTZEE("yahtzee"),
    CHANCE("chance"),

    THREE_PAIR("threePair"),
    FIVE_OF_A_KIND("fiveOfAKind"),
    FULL_STRAIGHT("fullStraight"),
    CABIN("cabin"),
    TOWER("tower"),
    FINAL_SUM("finalSum");

    private String value;

    GameRulesEnum(String str) {
        this.value = str;
    }

    public String getValue() {
        return value;
    }

    public static GameRulesEnum fromValue(String str) {
        GameRulesEnum enumToReturn = GameRulesEnum.DEFAULT_COL;
        for (GameRulesEnum value : GameRulesEnum.values()) {
            if(value.value.equals(str)) {
                enumToReturn = value;
                break;
            }
        }
        return enumToReturn;
    }

}
