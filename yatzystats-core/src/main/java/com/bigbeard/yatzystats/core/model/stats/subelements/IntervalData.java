package com.bigbeard.yatzystats.core.model.stats.subelements;

import java.util.List;

public record IntervalData(String dataName, List<String> intervalItems) {

    @Override
    public String toString() {
        String joinedItems = String.join(" ; ", intervalItems);
        return String.format("%s : [ %s ]", dataName, joinedItems);
    }
}
