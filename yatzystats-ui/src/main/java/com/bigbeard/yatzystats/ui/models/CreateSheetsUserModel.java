package com.bigbeard.yatzystats.ui.models;

import com.bigbeard.yatzystats.core.config.GameRulesLoader;
import com.bigbeard.yatzystats.core.config.writers.ExcelSheetWriter;
import com.bigbeard.yatzystats.core.exceptions.RulesNotLoadedException;
import com.bigbeard.yatzystats.core.model.rules.GameRules;
import com.bigbeard.yatzystats.core.model.rules.SheetRulesIdentifiers;

import java.io.IOException;
import java.util.List;

public class CreateSheetsUserModel {

    private String yatzyFilePath;

    public void setChosenRules(SheetRulesIdentifiers chosenRules) {
        this.chosenRules = chosenRules;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public void setSheetNumber(Integer sheetNumber) {
        this.sheetNumber = sheetNumber;
    }

    private SheetRulesIdentifiers chosenRules;
    private List<String> players;
    private Integer sheetNumber;
    public final String YATZY_NEW_FILE = "feuille_calcul_yatzee_new.xlsx";

    public void confirmFinalWritingPath(String yatzyDirectoryPath) {
        yatzyFilePath = yatzyDirectoryPath + YATZY_NEW_FILE;
    }

    public void writeExcelSheet() {
        try {
            // Rules
            GameRulesLoader gameRulesLoader = new GameRulesLoader(chosenRules);
            GameRules rules = gameRulesLoader.getGameRules();

            ExcelSheetWriter excelSheetWriter = new ExcelSheetWriter(yatzyFilePath, players);
            excelSheetWriter.writeSheets(sheetNumber, rules);
        } catch (RulesNotLoadedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
