package com.bigbeard.yatzystats.config;

import com.bigbeard.yatzystats.BaseTest;
import com.bigbeard.yatzystats.config.writers.ExcelSheetWriter;
import com.bigbeard.yatzystats.core.rules.GameLoader;
import com.bigbeard.yatzystats.core.rules.GameRules;
import com.bigbeard.yatzystats.core.rules.SheetRulesIdentifiers;
import com.bigbeard.yatzystats.core.sheets.SheetDto;
import com.bigbeard.yatzystats.exceptions.FileNotLoadedException;
import com.bigbeard.yatzystats.exceptions.RulesNotLoadedException;
import org.apache.commons.compress.utils.Lists;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.fail;

public class ExcelSheetWriterTest extends BaseTest {

    public final String SC_YATZY_TEST_PATH = "feuille_calcul_yatzee.xlsx";
    public final String SC_YATZY_TEST_NEW_PATH = "feuille_calcul_yatzee_new.xlsx";
    public final String SC_YATZY_RULES_PATH = "rules/scandinavian-yatzy-rules.json";
    private Logger logger = Logger.getLogger(ExcelSheetWriterTest.class);

    @Test
    @DisplayName("Ecriture d'un fichier Excel")
    public void writeExcelSheet() {
        logger.info("Lancement du test writeExcelSheet");
        URL filePath = ClassLoader.getSystemResource(SC_YATZY_TEST_PATH);
        String path = filePath.getPath().replace(SC_YATZY_TEST_PATH, SC_YATZY_TEST_NEW_PATH);
        System.out.println("d" + path);
        try {

            // Players
            List<String> players = List.of("Alexis","Arnaud","Maman","Papa");

            // Rules
            GameRulesLoader gameRulesLoader = new GameRulesLoader(SheetRulesIdentifiers.YATZY);
            GameRules rules = gameRulesLoader.getGameRules();

            ExcelSheetWriter writer = new ExcelSheetWriter(path, players);
            writer.writeSheets(2, rules);

        } catch(IOException | RulesNotLoadedException ex) {
            logger.error("Erreur" + ex);
            fail();
        }
    }





}
