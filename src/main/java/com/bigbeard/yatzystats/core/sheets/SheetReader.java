package com.bigbeard.yatzystats.core.sheets;

import com.bigbeard.yatzystats.core.rules.GameRules;

public interface SheetReader {

    public double readYatzy(String targetName);
    public double readBonus(String targetName);

    public double readScore(String targetName);
    public double readBestScore();
    public Boolean readIsPlayerWinning(String targetName);


}
