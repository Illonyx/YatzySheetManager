package com.bigbeard.yatzystats.core.model.stats.subelements;

import java.text.DecimalFormat;

public record DoubleData(String dataName, Double value) {

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat( "###.##" );
        return String.format("%s : %s", dataName, decimalFormat.format(value));
    }
}
