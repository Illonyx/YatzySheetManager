package com.bigbeard.yatzystats.ui;

import com.bigbeard.yatzystats.core.config.GameRulesLoader;
import com.bigbeard.yatzystats.core.config.loaders.excel.ExcelSheetLoader;
import com.bigbeard.yatzystats.core.exceptions.FileNotLoadedException;
import com.bigbeard.yatzystats.core.exceptions.RulesNotLoadedException;
import com.bigbeard.yatzystats.core.model.rules.GameLoader;
import com.bigbeard.yatzystats.core.model.rules.GameLoadingStatus;
import com.bigbeard.yatzystats.core.model.rules.GameRules;
import com.bigbeard.yatzystats.core.model.rules.SheetRulesIdentifiers;
import com.bigbeard.yatzystats.core.model.sheets.SheetDto;

import java.io.IOException;
import java.util.List;

public class GlobalController {

    public GlobalController() {

    }

    public GameLoadingStatus loadExcelSheet(String yatzyFilePath, GameRules gameRules) throws FileNotLoadedException {
        try {
            ExcelSheetLoader excelSheetLoader = new ExcelSheetLoader(yatzyFilePath);
            GameLoader gameLoader = new GameLoader(gameRules, excelSheetLoader);
            List<SheetDto> foundSheets = gameLoader.loadGamesFromMode();
            List<String> loadingErrors = gameLoader.getErrors();
            return new GameLoadingStatus(loadingErrors, foundSheets);
        } catch (IOException ex) {
            throw new FileNotLoadedException("Le fichier demandé n'a pas pu être ouvert",
                    "Vérifiez bien si le fichier n'est pas ouvert sous Excel ou si l'extension est bonne (xlsx)");
        }
    }

    public GameRules loadGameRules(SheetRulesIdentifiers sheetRules) throws RulesNotLoadedException {
        GameRulesLoader gameRulesLoader = new GameRulesLoader(sheetRules);
        return gameRulesLoader.getGameRules();
    }

}
