package com.bigbeard.yatzystats.core.rules;

public class GameRules {

    //Rows
    private Integer bonusRow;
    private Integer yatzyRow;
    private Integer scoreRow;

    //Scores
    private Integer yatzyValue;
    private Integer bonusValue;

    public GameRules() {
    }

    public Integer getBonusRow() {
        return bonusRow;
    }

    public void setBonusRow(Integer bonusRow) {
        this.bonusRow = bonusRow;
    }

    public Integer getYatzyRow() {
        return yatzyRow;
    }

    public void setYatzyRow(Integer yatzyRow) {
        this.yatzyRow = yatzyRow;
    }

    public Integer getScoreRow() {
        return scoreRow;
    }

    public void setScoreRow(Integer scoreRow) {
        this.scoreRow = scoreRow;
    }


    public Integer getYatzyValue() {
        return yatzyValue;
    }

    public void setYatzyValue(Integer yatzyValue) {
        this.yatzyValue = yatzyValue;
    }

    public Integer getBonusValue() {
        return bonusValue;
    }

    public void setBonusValue(Integer bonusValue) {
        this.bonusValue = bonusValue;
    }

    @Override
    public String toString() {
        String rulesStr = "GameRules : " + System.lineSeparator();
        rulesStr += "BonusRow : " + this.bonusRow + System.lineSeparator();
        rulesStr += "BonusVal : " + this.bonusValue + System.lineSeparator();
        return rulesStr;
    }
}
