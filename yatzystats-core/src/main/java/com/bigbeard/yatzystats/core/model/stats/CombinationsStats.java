package com.bigbeard.yatzystats.core.model.stats;

import com.bigbeard.yatzystats.core.model.stats.subelements.PercentageData;

public record CombinationsStats(String sectionTitle, PercentageData winRate, PercentageData yatzyRate, PercentageData bonusRate) {
    @Override
    public String toString() {
        return sectionTitle + System.lineSeparator() +
                winRate + System.lineSeparator() +
                yatzyRate + System.lineSeparator() +
                bonusRate;
    }
}
