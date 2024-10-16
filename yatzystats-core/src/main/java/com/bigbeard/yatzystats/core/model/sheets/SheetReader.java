package com.bigbeard.yatzystats.core.model.sheets;

import java.util.List;
import com.bigbeard.yatzystats.core.exceptions.CellNotFoundException;
import com.bigbeard.yatzystats.core.model.rules.GameRulesEnum;

public interface SheetReader {
    String getSheetName();
    List<String> readPlayerNames() throws CellNotFoundException;
    Integer readRuleScore(String targetName, GameRulesEnum rulesEnum) throws CellNotFoundException;
}
