package com.bigbeard.yatzystats.core.model.players;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import java.util.Comparator;
import java.util.List;

public class StatsModule {

    private static StatsModule instance = new StatsModule();

    private StatsModule(){}

    public static StatsModule getInstance(){
        return instance;
    }

    //http://commons.apache.org/proper/commons-math/userguide/stat.html
    // -------------------------------------
    // -- Common
    // -------------------------------------

    public String givePercentageOf(int numbersOf, int numbersAll){
        double percentage = 100.0*numbersOf/numbersAll;
        return String.valueOf(Math.ceil(percentage)) + "% (" + numbersOf + "/" + numbersAll + ")";
    }

    public double getMean(List<PlayerResult> playerResults){
        return playerResults.stream()
                .mapToDouble(PlayerResult::getScore)
                .average()
                .getAsDouble();
    }

    public String getInterval(double min, double max){
        return "[" + min + ";" + max + "]";
    }

    // ----------------------------------
    // -- Score
    // ----------------------------------

    public int getHighestScore(List<PlayerResult> playerResults)
    {
        return playerResults.stream()
                .map(PlayerResult::getScore)
                .max(Integer::compare).get();
    }

    public List<String> getHighestScores(int limit, List<PlayerResult> playerResults)
    {
        return playerResults.stream()
                .map(PlayerResult::getScore)
                .sorted(Comparator.reverseOrder())
                .limit(limit)
                .map(Object::toString)
                .toList();
    }

    public int getLowestScore(List<PlayerResult> playerResults){
        return playerResults.stream()
                .map(PlayerResult::getScore)
                .min(Integer::compare).get();
    }

    // -------------------------------------
    // Rates
    // -------------------------------------

    public String getYatzyRate(List<PlayerResult> playerResults) {
        long yatzyNum = playerResults.stream()
                .map(PlayerResult::isHasYatzy)
                .filter(aBoolean -> aBoolean.equals(true))
                .count();
        return this.givePercentageOf((int)yatzyNum, playerResults.size());
    }

    public String getWinRate(List<PlayerResult> playerResults) {
        long yatzyNum = playerResults.stream()
                .map(PlayerResult::isWinner)
                .filter(aBoolean -> aBoolean.equals(true))
                .count();
        return this.givePercentageOf((int)yatzyNum, playerResults.size());
    }

    public String getBonusRate(List<PlayerResult> playerResults) {
        long yatzyNum = playerResults.stream()
                .map(PlayerResult::isHasBonus)
                .filter(aBoolean -> aBoolean.equals(true))
                .count();
        return this.givePercentageOf((int)yatzyNum, playerResults.size());
    }

    // --------------------------------------
    // Statistics
    // --------------------------------------

    public double getStandardDeviation(List<PlayerResult> playerResults) {
        SummaryStatistics stats = new SummaryStatistics();
        double mean = this.getMean(playerResults);
        playerResults.stream()
                .map(PlayerResult::getScore)
                .map(Integer::doubleValue)
                .forEach(stats::addValue);
        return Math.round(stats.getStandardDeviation());
    }

}