package com.bigbeard.yatzystats.core.model.players;

public class PlayerResult {

    private String playerName;
    private boolean hasYatzy;
    private boolean hasBonus;
    private int score;
    private boolean isWinner;

    public PlayerResult(){

    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public boolean isHasYatzy() {
        return hasYatzy;
    }

    public void setHasYatzy(boolean hasYatzy) {
        this.hasYatzy = hasYatzy;
    }

    public boolean isHasBonus() {
        return hasBonus;
    }

    public void setHasBonus(boolean hasBonus) {
        this.hasBonus = hasBonus;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }

}
