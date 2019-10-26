package com.bigbeard.yatzystats.core.players;

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

//    public void printStats(){
//        PlayerStatsCalculator psc = new PlayerStatsCalculator();
//        System.out.println("------------------------------------------------");
//        System.out.println(this.name);
//        System.out.println("------------------------------------------------");
//        System.out.println("Winrate : " + psc.getWinRate(this.results));
//        System.out.println("Mean : " + psc.getMean(this.results));
//        System.out.println("High : " + psc.getHighestScore(this.results));
//        System.out.println("Low : " + psc.getLowestScore(this.results));
//        System.out.println("YatzyRate : " + psc.getYatzyRate(this.results));
//        System.out.println("BonusRate : " + psc.getBonusRate(this.results));
//        System.out.println("Standard Deviation : " + psc.getStandardDeviation(this.results));
//        System.out.println("Moyenne du plus haut score : " + psc.getBestScoreMean(this.results));
//    }

}
