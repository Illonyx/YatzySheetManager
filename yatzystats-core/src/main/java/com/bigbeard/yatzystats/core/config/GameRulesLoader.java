package com.bigbeard.yatzystats.core.config;

import com.bigbeard.yatzystats.core.exceptions.RulesNotLoadedException;
import com.bigbeard.yatzystats.core.model.rules.GameRules;
import com.bigbeard.yatzystats.core.model.rules.SheetRulesIdentifiers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameRulesLoader {

    private GameRules gameRules;

    private Logger logger = LogManager.getLogger(GameRulesLoader.class);

    public GameRulesLoader(SheetRulesIdentifiers sheetRules) throws RulesNotLoadedException {
       this.gameRules = this.loadGameRules(sheetRules);
    }

    public GameRules getGameRules() {
        return this.gameRules;
    }

    public List<GameRules> findLoadedGameRules() {
        List<GameRules> loadedGameRules = new ArrayList<>();
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
        final Gson gson = new GsonBuilder().create();
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(this.getSheetRulesPath(sheetRules));
        assert inputStream != null;
        Reader reader = new InputStreamReader(inputStream);
        try {
            return gson.fromJson(reader, GameRules.class);
        } catch(JsonSyntaxException | JsonIOException ex) {
            throw new RulesNotLoadedException("Règles non chargées", "Exception déclenchée" + ex);
        } catch (Exception ex) {
            throw new RulesNotLoadedException("Le chemin vers les fichiers de configuration des règles est incorrect.", "Exception déclenchée" + ex);
        }
    }

    private String getSheetRulesPath(SheetRulesIdentifiers sheetRules){
        return "rules" + File.separator + sheetRules.getPath();
    }

}
