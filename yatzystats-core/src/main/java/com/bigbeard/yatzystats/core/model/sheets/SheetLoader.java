package com.bigbeard.yatzystats.core.model.sheets;

import com.bigbeard.yatzystats.core.model.rules.GameRules;

import java.util.List;

public interface SheetLoader {

    Integer getSheetNumber();
    List<SheetReader> createSheetReaders(GameRules gameRules);
}
