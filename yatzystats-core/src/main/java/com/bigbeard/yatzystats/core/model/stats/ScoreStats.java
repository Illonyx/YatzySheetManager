package com.bigbeard.yatzystats.core.model.stats;

import com.bigbeard.yatzystats.core.model.stats.subelements.DoubleData;
import com.bigbeard.yatzystats.core.model.stats.subelements.IntegerData;
import com.bigbeard.yatzystats.core.model.stats.subelements.IntervalData;
import com.bigbeard.yatzystats.core.model.stats.subelements.PercentageData;

public record ScoreStats(String sectionTitle,
                         DoubleData mean,
                         IntegerData highest,
                         IntegerData lowest,
                         PercentageData above300,
                         PercentageData below200,
                         IntervalData top5Scores,
                         IntervalData standardInterval
                         ) {

    @Override
    public String toString() {
        return sectionTitle + System.lineSeparator()
                + mean + System.lineSeparator()
                + highest + System.lineSeparator()
                + lowest + System.lineSeparator()
                + above300 + System.lineSeparator()
                + below200 + System.lineSeparator()
                + top5Scores + System.lineSeparator()
                + standardInterval + System.lineSeparator();
    }
}
