package com.bigbeard.yatzystats.config;

import com.bigbeard.yatzystats.core.rules.GameRules;
import com.bigbeard.yatzystats.core.rules.SheetRulesIdentifiers;
import com.bigbeard.yatzystats.exceptions.RulesNotLoadedException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameRulesLoader {

    private GameRules gameRules;

    public GameRulesLoader(SheetRulesIdentifiers sheetRules) throws RulesNotLoadedException {
       this.gameRules = this.loadGameRules(sheetRules);
    }

    public GameRules getGameRules() {
        return this.gameRules;
    }

    public List<GameRules> findLoadedGameRules() {
        List<GameRules> loadedGameRules = new ArrayList<GameRules>();
        for(SheetRulesIdentifiers identifier : SheetRulesIdentifiers.values()) {
            try {
                GameRules rules = this.loadGameRules(identifier);
                loadedGameRules.add(rules);
            } catch(RulesNotLoadedException exception){
              // DO NOTHING
            }
        }
        return loadedGameRules;
    }

    private GameRules loadGameRules(SheetRulesIdentifiers sheetRules) throws RulesNotLoadedException {
        try {
            String rootPath = Thread.currentThread().getContextClassLoader().getResource("rules").getPath();
            String appConfigPath = rootPath + File.separator + this.getSheetRulesPath(sheetRules);

            FileReader reader = new FileReader(appConfigPath);
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(reader);
            return new GameRules(object);
        } catch(IOException | ParseException ex) {
            throw new RulesNotLoadedException("Règles non chargées", "Exception déclenchée" + ex);
        }
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
