package com.bigbeard.yatzystats.core.model.sheets;

import java.util.List;
import com.bigbeard.yatzystats.core.exceptions.CellNotFoundException;

public interface SheetReader {

    public String getSheetName();
    public List<String> readPlayerNames() throws CellNotFoundException;
    public Integer readYatzy(String targetName) throws CellNotFoundException;
    public Integer readBonus(String targetName) throws CellNotFoundException;
    public Integer readScore(String targetName) throws CellNotFoundException;

}
