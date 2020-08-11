package com.bigbeard.yatzystats.config;

import com.bigbeard.yatzystats.BaseTest;
import com.bigbeard.yatzystats.core.rules.GameLoader;
import com.bigbeard.yatzystats.core.rules.GameRules;
import com.bigbeard.yatzystats.core.rules.SheetRulesIdentifiers;
import com.bigbeard.yatzystats.core.sheets.SheetDto;
import com.bigbeard.yatzystats.exceptions.FileNotLoadedException;
import com.bigbeard.yatzystats.exceptions.RulesNotLoadedException;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;



public class ExcelSheetLoaderTest extends BaseTest {

    public final String SC_YATZY_TEST_PATH = "feuille_calcul_yatzee.xlsx";
    public final String SC_YATZY_RULES_PATH = "rules/scandinavian-yatzy-rules.json";
    private Logger logger = Logger.getLogger(ExcelSheetLoaderTest.class);


    @Test
    @DisplayName("Read Rules")
    public void readRulesJSON() {
        logger.info("TEST READ RULES JSON - GO GO GO");
        URL filePath = ClassLoader.getSystemResource(SC_YATZY_RULES_PATH);
        try {
            FileReader reader = new FileReader(filePath.getPath());
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(reader);
            logger.info("Contenu fichier JSON : "+ object);

            GameRules rules = new GameRules(object);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test JU5")
    public void readExcelSheet() {
        logger.info("Lancement du test");
        URL filePath = ClassLoader.getSystemResource(SC_YATZY_TEST_PATH);
        try {
            ExcelSheetLoader excelSheetLoader = new ExcelSheetLoader(filePath.getPath());
            List<String> listStr = excelSheetLoader.getAllSheetNames(excelSheetLoader.getAllSheets());
            Assertions.assertTrue(listStr.size() != 0);
            logger.debug("" + String.join(",", listStr));

            GameRulesLoader gameRulesLoader = new GameRulesLoader(SheetRulesIdentifiers.YATZY);
            GameRules rules = gameRulesLoader.getGameRules();
            GameLoader gameLoader = new GameLoader(rules, excelSheetLoader.getFormulaEvaluator());
            List<SheetDto> foundSheets = gameLoader.loadGamesFromMode(excelSheetLoader.getAllSheets());
            logger.debug("FSize : " + foundSheets.size());

        } catch(FileNotLoadedException | IOException | RulesNotLoadedException ex) {
            logger.error("Erreur" + ex);
        }
    }

}
