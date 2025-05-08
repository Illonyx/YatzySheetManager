package com.bigbeard.yatzystats.core.stats;

import com.bigbeard.yatzystats.core.model.players.PlayerResult;
import com.bigbeard.yatzystats.core.model.stats.subelements.DoubleData;
import com.bigbeard.yatzystats.core.model.stats.subelements.IntegerData;
import com.bigbeard.yatzystats.core.model.stats.subelements.IntervalData;
import com.bigbeard.yatzystats.core.model.stats.subelements.PercentageData;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import java.util.Comparator;
import java.util.List;

//http://commons.apache.org/proper/commons-math/userguide/stat.html
public class StatsModule {

    private static StatsModule instance = new StatsModule();

    private StatsModule(){}

    public static StatsModule getInstance(){
        return instance;
    }


    // -------------------------------------
    // -- Common
    // -------------------------------------

    public DoubleData getMean(String dataName, List<PlayerResult> playerResults){
        double mean = playerResults.stream()
                .mapToDouble(PlayerResult::score)
                .average()
                .getAsDouble();
        return new DoubleData(dataName, mean);
    }

    // ----------------------------------
    // -- Score
    // ----------------------------------

    public IntegerData getHighestScore(String dataName, List<PlayerResult> playerResults)
    {
        return new IntegerData(dataName, playerResults.stream()
                .map(PlayerResult::score)
                .max(Integer::compare).get());
    }

    public IntervalData getHighestScores(String dataName, int limit, List<PlayerResult> playerResults)
    {
        List<String> highestScores = playerResults.stream()
                .map(PlayerResult::score)
                .sorted(Comparator.reverseOrder())
                .limit(limit)
                .map(Object::toString)
                .toList();
        return new IntervalData(dataName, highestScores);
    }

    public IntegerData getLowestScore(String dataName, List<PlayerResult> playerResults){
        return new IntegerData(dataName, playerResults.stream()
                .map(PlayerResult::score)
                .min(Integer::compare).get());
    }

    // -------------------------------------
    // Rates
    // -------------------------------------

    public PercentageData getYatzyRate(String dataName, List<PlayerResult> playerResults) {
        long yatzyNum = playerResults.stream()
                .map(PlayerResult::hasYatzy)
                .filter(aBoolean -> aBoolean.equals(true))
                .count();
        return new PercentageData(dataName, (int) yatzyNum, playerResults.size());
    }

    public PercentageData getWinRate(String dataName, List<PlayerResult> playerResults) {
        long winNum = playerResults.stream()
                .map(PlayerResult::isWinner)
                .filter(aBoolean -> aBoolean.equals(true))
                .count();
        return new PercentageData(dataName, (int) winNum, playerResults.size());
    }

    public PercentageData get300Rate(String dataName, List<PlayerResult> playerResults) {
        long superior300 = playerResults.stream()
                .map(PlayerResult::score)
                .filter(score -> score >= 300)
                .count();
        return new PercentageData(dataName, (int) superior300, playerResults.size());
    }

    public PercentageData getUnder200Rate(String dataName, List<PlayerResult> playerResults) {
        long under200Rate = playerResults.stream()
                .map(PlayerResult::score)
                .filter(score -> score < 200)
                .count();
        return new PercentageData(dataName, (int) under200Rate, playerResults.size());
    }

    public PercentageData getBonusRate(String dataName, List<PlayerResult> playerResults) {
        long bonusRate = playerResults.stream()
                .map(PlayerResult::hasBonus)
                .filter(aBoolean -> aBoolean.equals(true))
                .count();
        return new PercentageData(dataName, (int) bonusRate, playerResults.size());
    }

    // --------------------------------------
    // Statistics
    // --------------------------------------

    public IntervalData getStandardInterval(String dataName, List<PlayerResult> playerResults) {
        double mean = this.getMean("", playerResults).value();
        double standardDeviation = this.getStandardDeviation(playerResults);

        double minInterval = mean - standardDeviation;
        double maxInterval = mean + standardDeviation;

        return new IntervalData(dataName, List.of(
                String.format("%.2f", minInterval),
                String.format("%.2f", maxInterval)
        ));
    }

    private double getStandardDeviation(List<PlayerResult> playerResults) {
        SummaryStatistics stats = new SummaryStatistics();
        playerResults.stream()
                .map(PlayerResult::score)
                .map(Integer::doubleValue)
                .forEach(stats::addValue);
        return Math.round(stats.getStandardDeviation());
    }

}