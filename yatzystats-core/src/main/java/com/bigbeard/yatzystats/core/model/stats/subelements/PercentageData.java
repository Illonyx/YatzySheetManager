package com.bigbeard.yatzystats.core.model.stats.subelements;

import java.text.NumberFormat;

public record PercentageData(String dataName, Integer numbersOf, Integer numbersAll) {

    @Override
    public String toString() {
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        percentFormat.setMaximumFractionDigits(1);// Set the number of decimal places (0 in this case)
        double per = (double) numbersOf / numbersAll;
        String formattedPercentage = percentFormat.format(per);
        return String.format("%s : %s (%d/%d)", dataName, formattedPercentage, numbersOf, numbersAll);
    }
}
