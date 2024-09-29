package com.bigbeard.yatzystats.core.config;

import com.bigbeard.yatzystats.core.config.writers.ExcelSheetWriter;
import com.bigbeard.yatzystats.core.exceptions.RulesNotLoadedException;
import com.bigbeard.yatzystats.core.model.rules.GameRules;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;

public class ExcelSheetWriterTest {

    public final String SC_YATZY_TEST_PATH = "feuille_calcul_yatzee.xlsx";
    public final String SC_YATZY_TEST_NEW_PATH = "feuille_calcul_yatzee_new.xlsx";
    private Logger logger = LogManager.getLogger(ExcelSheetWriterTest.class);

    @Test
    @DisplayName("Ecriture d'un fichier Excel Yatzy")
    public void writeYatzyExcelSheet() {
        logger.info("Lancement du test writeExcelSheet");

        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        String monthFormatted = String.format("%02d", month);
        String dayOfMonthFormatted = String.format("%02d", dayOfMonth);

        logger.info(dayOfMonthFormatted + monthFormatted + year);
        URL filePath = ClassLoader.getSystemResource(SC_YATZY_TEST_PATH);
        String path = filePath.getPath().replace(SC_YATZY_TEST_PATH, SC_YATZY_TEST_NEW_PATH);
        try {

            // Players
            List<String> players = List.of("Alexis", "Arnaud", "Maman", "Papa");

            // Rules
            GameRulesLoaderV2 gameRulesLoaderV2 = new GameRulesLoaderV2();
            GameRules rules = gameRulesLoaderV2.getGameRuleFiles().stream().filter(
                    gameRules -> "ScandinavianYatzy".equals(gameRules.formatId())
            ).findFirst().orElseThrow();

            ExcelSheetWriter writer = new ExcelSheetWriter(path, players);
            writer.writeSheets(2, rules);

        } catch (IOException | RulesNotLoadedException ex) {
            logger.error("Erreur" + ex);
            Assertions.fail();
        }
    }

    @Test
    @DisplayName("Ecriture d'un fichier Excel Maxi Yatzy")
    public void writeMaxiYatzyExcelSheet() {
        logger.info("Lancement du test writeExcelSheet");
        URL filePath = ClassLoader.getSystemResource(SC_YATZY_TEST_PATH);
        String path = filePath.getPath().replace(SC_YATZY_TEST_PATH, SC_YATZY_TEST_NEW_PATH);
        try {

            // Players
            List<String> players = List.of("Alexis", "Arnaud", "Maman", "Papa");

            // Rules
            GameRulesLoaderV2 gameRulesLoaderV2 = new GameRulesLoaderV2();
            GameRules rules = gameRulesLoaderV2.getGameRuleFiles().stream().filter(
                    gameRules -> "ScandinavianYatzy".equals(gameRules.formatId())
            ).findFirst().orElseThrow();

            ExcelSheetWriter writer = new ExcelSheetWriter(path, players);
            writer.writeSheets(2, rules);

        } catch (IOException | RulesNotLoadedException ex) {
            logger.error("Erreur" + ex);
            Assertions.fail();
        }
    }


}
