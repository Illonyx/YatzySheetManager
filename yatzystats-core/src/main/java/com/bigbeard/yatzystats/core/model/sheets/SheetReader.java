package com.bigbeard.yatzystats.core.model.sheets;

import java.util.List;
import com.bigbeard.yatzystats.core.exceptions.CellNotFoundException;

public interface SheetReader {

    String getSheetName();
    List<String> readPlayerNames() throws CellNotFoundException;
    Integer readAces(String targetName) throws CellNotFoundException;
    Integer readTwos(String targetName) throws CellNotFoundException;
    Integer readThrees(String targetName) throws CellNotFoundException;
    Integer readFours(String targetName) throws CellNotFoundException;
    Integer readFives(String targetName) throws CellNotFoundException;
    Integer readSixes(String targetName) throws CellNotFoundException;
    Integer readYatzy(String targetName) throws CellNotFoundException;
    Integer readBonus(String targetName) throws CellNotFoundException;
    Integer readScore(String targetName) throws CellNotFoundException;

}
