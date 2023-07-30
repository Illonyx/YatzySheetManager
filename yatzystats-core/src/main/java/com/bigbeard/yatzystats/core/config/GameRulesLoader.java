package com.bigbeard.yatzystats.core.config;

import com.bigbeard.yatzystats.core.exceptions.RulesNotLoadedException;
import com.bigbeard.yatzystats.core.model.rules.GameRules;
import com.bigbeard.yatzystats.core.model.rules.SheetRulesIdentifiers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
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
        try {
            URL resourceUrl = Thread.currentThread().getContextClassLoader().getResource("rules");
            String appConfigPath = resourceUrl.getPath() + File.separator + this.getSheetRulesPath(sheetRules);
            final Gson gson = new GsonBuilder().create();

            FileReader reader = new FileReader(appConfigPath);
            return gson.fromJson(reader, GameRules.class);
        } catch(IOException | JsonSyntaxException ex) {
            throw new RulesNotLoadedException("Règles non chargées", "Exception déclenchée" + ex);
        } catch (Exception ex) {
            throw new RulesNotLoadedException("Le chemin vers les fichiers de configuration des règles est incorrect.", "Exception déclenchée" + ex);
        }
    }

    private String getSheetRulesPath(SheetRulesIdentifiers sheetRules){
        String extension = "";
        switch(sheetRules) {
            case YATZY:
                extension = "scandinavian-yatzy-rules.json";
                break;

            case MAXI_YATZY:
                extension = "scandinavian-maxiyatzy-rules.json";
                break;

            default:
                //DO NOTHING
                break;
        }
        return extension;
    }

}
