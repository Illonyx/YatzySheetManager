package com.bigbeard.yatzystats.ui.models;

import com.bigbeard.yatzystats.core.config.writers.ExcelSheetWriter;
import com.bigbeard.yatzystats.core.model.dto.GameRulesDTO;
import com.bigbeard.yatzystats.core.model.rules.GameRules;

import java.io.IOException;
import java.util.List;

public class CreateSheetsUserModel {
    private String yatzyFilePath;

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public void setSheetNumber(Integer sheetNumber) {
        this.sheetNumber = sheetNumber;
    }
    public void setChosenRules(GameRules chosenRules) {
        this.chosenRules = chosenRules;
    }

    private GameRules chosenRules;
    private List<String> players;
    private Integer sheetNumber;
    public final String YATZY_NEW_FILE = "feuille_calcul_yatzee_new.xlsx";

    public void confirmFinalWritingPath(String yatzyDirectoryPath) {
        yatzyFilePath = yatzyDirectoryPath + YATZY_NEW_FILE;
    }

    public void writeExcelSheet() {
        try {
            ExcelSheetWriter excelSheetWriter = new ExcelSheetWriter(yatzyFilePath, players);
            excelSheetWriter.writeSheets(sheetNumber, chosenRules);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
