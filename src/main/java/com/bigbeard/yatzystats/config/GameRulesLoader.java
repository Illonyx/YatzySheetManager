package com.bigbeard.yatzystats.config;

import com.bigbeard.yatzystats.core.rules.GameRules;
import com.bigbeard.yatzystats.core.rules.SheetRulesIdentifiers;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Properties;

public class GameRulesLoader {

    private GameRules gameRules;

    public GameRulesLoader(SheetRulesIdentifiers sheetRules) throws IOException, ParseException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("rules").getPath();
        String appConfigPath = rootPath + File.separator + this.getSheetRulesPath(sheetRules);

        FileReader reader = new FileReader(appConfigPath);
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(reader);
        this.gameRules = new GameRules(object);
    }

    public GameRules getGameRules() {
        return this.gameRules;
    }

    private String getSheetRulesPath(SheetRulesIdentifiers sheetRules){
        String extension = "";
        switch(sheetRules) {
            case YATZY:
                extension = "scandinavian-yatzy-rules.json";
                break;
            default:
                //DO NOTHING
                break;
        }
        return extension;
    }

}
