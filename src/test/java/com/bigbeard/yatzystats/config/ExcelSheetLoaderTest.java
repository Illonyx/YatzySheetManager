package com.bigbeard.yatzystats.config;

import com.bigbeard.yatzystats.BaseTest;
import com.bigbeard.yatzystats.core.rules.GameLoader;
import com.bigbeard.yatzystats.core.rules.GameRules;
import com.bigbeard.yatzystats.core.rules.SheetRulesIdentifiers;
import com.bigbeard.yatzystats.core.sheets.SheetDto;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ExcelSheetLoaderTest extends BaseTest {

    private Logger logger = Logger.getLogger(ExcelSheetLoaderTest.class);


    @Test
    @DisplayName("Test JU5")
    public void readExcelSheet() {
        logger.info("Lancement du test");
        URL filePath = ClassLoader.getSystemResource("feuille_calcul_yatzee.xlsx");
        try{
            ExcelSheetLoader excelSheetLoader = new ExcelSheetLoader(filePath.getPath());
            List<String> listStr = excelSheetLoader.getAllSheetNames(excelSheetLoader.getAllSheets());
            Assertions.assertTrue(listStr.size() != 0);
            logger.debug("" + String.join(",", listStr));

            GameRulesLoader gameRulesLoader = new GameRulesLoader(SheetRulesIdentifiers.YATZY);
            GameRules rules = gameRulesLoader.getGameRules();
            GameLoader gameLoader = new GameLoader(rules, excelSheetLoader.getFormulaEvaluator());
            List<SheetDto> foundSheets = gameLoader.loadGamesFromMode(excelSheetLoader.getAllSheets());
            logger.debug("FSize : " + foundSheets.size());

        } catch(IOException ex) {
            logger.error("Erreur" + ex);
        }
    }

}
