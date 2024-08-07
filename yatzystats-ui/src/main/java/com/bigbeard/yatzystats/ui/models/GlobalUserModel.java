package com.bigbeard.yatzystats.ui.models;

import com.bigbeard.yatzystats.core.config.properties.UserPropertiesService;
import com.bigbeard.yatzystats.core.model.players.UserProperties;

public class GlobalUserModel {

    private final UserPropertiesService userPropertiesService;
    private final StatsSheetsUserModel statsSheetsUserModel;
    private final CreateSheetsUserModel createSheetsUserModel;

    public GlobalUserModel() {
        this.userPropertiesService = new UserPropertiesService();
        this.statsSheetsUserModel = new StatsSheetsUserModel();
        this.createSheetsUserModel = new CreateSheetsUserModel();
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

}
