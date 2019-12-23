package com.bigbeard.yatzystats.config;

import com.bigbeard.yatzystats.core.rules.GameRules;
import com.bigbeard.yatzystats.core.rules.SheetRulesIdentifiers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class GameRulesLoader {

    private GameRules gameRules;

    public GameRulesLoader(SheetRulesIdentifiers sheetRules) throws FileNotFoundException, IOException {

        String rootPath = Thread.currentThread().getContextClassLoader().getResource("rules").getPath();
        String appConfigPath = rootPath + File.separator + this.getSheetRulesPath(sheetRules);

        Properties props = new Properties();
        props.load(new FileInputStream(appConfigPath));

        //Read sheet identifier properties
        Integer bonusRow = Integer.parseInt(props.getProperty("BONUS_ROW"));
        Integer yatzyRow = Integer.parseInt(props.getProperty("YATZY_ROW"));
        Integer scoreRow = Integer.parseInt(props.getProperty("SCORE_ROW"));

        Integer yatzyValue = Integer.parseInt(props.getProperty("YATZY_VALUE"));
        Integer bonusValue = Integer.parseInt(props.getProperty("BONUS_VALUE"));

        //Initialize game rules
        this.gameRules = new GameRules();
        this.gameRules.setBonusRow(bonusRow);
        this.gameRules.setScoreRow(scoreRow);
        this.gameRules.setYatzyRow(yatzyRow);

        this.gameRules.setYatzyValue(yatzyValue);
        this.gameRules.setBonusValue(bonusValue);


    }

    public GameRules getGameRules() {
        return this.gameRules;
    }

    private String getSheetRulesPath(SheetRulesIdentifiers sheetRules){
        String extension = "";
        switch(sheetRules) {
            case YATZY:
                extension = "scandinavian-yatzy-rules.properties";
                break;
            default:
                //DO NOTHING
                break;
        }
        return extension;
    }

}
