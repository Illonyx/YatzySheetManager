package com.bigbeard.yatzystats.ui.models;

import com.bigbeard.yatzystats.core.config.properties.UserPropertiesService;
import com.bigbeard.yatzystats.core.exceptions.RulesNotLoadedException;
import com.bigbeard.yatzystats.core.model.players.UserProperties;
import com.bigbeard.yatzystats.core.model.rules.GameRules;
import com.bigbeard.yatzystats.ui.GlobalController;

import java.io.IOException;
import java.util.List;

public class GlobalUserModel {

    private final UserPropertiesService userPropertiesService;
    private final StatsSheetsUserModel statsSheetsUserModel;
    private final CreateSheetsUserModel createSheetsUserModel;
    private List<GameRules> availableGameRules;

    public GlobalUserModel() {
        this.userPropertiesService = new UserPropertiesService();
        this.statsSheetsUserModel = new StatsSheetsUserModel();
        this.createSheetsUserModel = new CreateSheetsUserModel();
        this.loadAllGameRules();
    }

    private void loadAllGameRules() {
        GlobalController globalController = new GlobalController();
        try {
            this.availableGameRules = globalController.loadAllGameRules();
        } catch (RulesNotLoadedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public StatsSheetsUserModel getStatsSheetsUserModel() {
        return statsSheetsUserModel;
    }

    public CreateSheetsUserModel getCreateSheetsUserModel() {
        return createSheetsUserModel;
    }

    public UserProperties getUserProperties() {
        return this.userPropertiesService.readUserProperties();
    }

    public void setUserProperties(UserProperties userProperties) {
        this.userPropertiesService.writeUserProperties(userProperties);
    }

    public List<GameRules> getAvailableGameRules() {
        return availableGameRules;
    }

}
