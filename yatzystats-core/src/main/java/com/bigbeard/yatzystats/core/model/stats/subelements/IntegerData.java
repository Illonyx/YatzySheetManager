package com.bigbeard.yatzystats.core.model.stats.subelements;

public record IntegerData(String dataName, Integer value) {

    @Override
    public String toString() {
        return String.format("%s : %d", dataName, value);
    }
}
