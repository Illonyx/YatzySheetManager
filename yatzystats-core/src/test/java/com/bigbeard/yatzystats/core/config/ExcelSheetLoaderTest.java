package com.bigbeard.yatzystats.core.config;

import com.bigbeard.yatzystats.core.config.loaders.excel.ExcelSheetLoader;
import com.bigbeard.yatzystats.core.exceptions.FileNotLoadedException;
import com.bigbeard.yatzystats.core.exceptions.RulesNotLoadedException;
import com.bigbeard.yatzystats.core.model.rules.GameLoader;
import com.bigbeard.yatzystats.core.model.rules.GameRules;
import com.bigbeard.yatzystats.core.model.rules.SheetRulesIdentifiers;
import com.bigbeard.yatzystats.core.model.sheets.SheetDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class ExcelSheetLoaderTest {

    public final String SC_YATZY_TEST_PATH = "feuille_calcul_yatzee.xlsx";
    public final String SC_YATZY_RULES_PATH = "rules/scandinavian-yatzy-rules.json";
    Logger logger = LogManager.getLogger(ExcelSheetLoaderTest.class);

    @Test
    @DisplayName("Read Rules")
    public void readRulesJSON() {
        logger.debug("TEST READ RULES JSON - GO GO GO");
        URL filePath = ClassLoader.getSystemResource(SC_YATZY_RULES_PATH);
        try {
            FileReader reader = new FileReader(filePath.getPath());
            final Gson gson = new GsonBuilder().create();

            GameRules object = gson.fromJson(reader, GameRules.class);
            logger.debug("Contenu fichier JSON : "+ object);

        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @DisplayName("Test JU5")
    public void readExcelSheet() {
        logger.debug("Lancement du test readExcelSheet");
        URL filePath = ClassLoader.getSystemResource(SC_YATZY_TEST_PATH);
        try {
            ExcelSheetLoader excelSheetLoader = new ExcelSheetLoader(filePath.getPath());
            List<String> listStr = excelSheetLoader.getAllSheetNames(excelSheetLoader.getAllSheets());
            Assertions.assertFalse(listStr.isEmpty());
            logger.debug(String.join(",", listStr));

            GameRulesLoader gameRulesLoader = new GameRulesLoader(SheetRulesIdentifiers.YATZY);
            GameRules rules = gameRulesLoader.getGameRules();
            GameLoader gameLoader = new GameLoader(rules, excelSheetLoader);
            List<SheetDto> foundSheets = gameLoader.loadGamesFromMode();
            logger.debug("FSize : " + foundSheets.size());

        } catch(FileNotLoadedException | IOException | RulesNotLoadedException ex) {
            logger.error("Erreur" + ex);
        }
    }

}
