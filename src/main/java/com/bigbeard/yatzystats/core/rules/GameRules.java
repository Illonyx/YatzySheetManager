package com.bigbeard.yatzystats.core.rules;

public class GameRules {

    //Rows
    private Integer bonusRow;
    private Integer yatzyRow;
    private Integer scoreRow;

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
}
