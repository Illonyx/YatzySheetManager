package com.bigbeard.yatzystats.core.sheets;

import com.bigbeard.yatzystats.core.rules.GameRules;
import com.bigbeard.yatzystats.exceptions.CellNotFoundException;

import java.util.List;

public interface SheetReader {

    public List<String> readPlayerNames() throws CellNotFoundException;

    public Integer readYatzy(String targetName) throws CellNotFoundException;
    public Integer readBonus(String targetName) throws CellNotFoundException;
    public Integer readScore(String targetName) throws CellNotFoundException;


}
