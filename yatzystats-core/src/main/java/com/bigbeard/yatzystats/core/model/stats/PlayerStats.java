package com.bigbeard.yatzystats.core.model.stats;

public record PlayerStats(ScoreStats scoreStats, CombinationsStats combinationsStats) {

    @Override
    public String toString() {
        return scoreStats + System.lineSeparator() + combinationsStats;
    }
}
