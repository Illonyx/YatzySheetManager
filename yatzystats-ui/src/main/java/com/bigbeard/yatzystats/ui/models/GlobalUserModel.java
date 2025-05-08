package com.bigbeard.yatzystats.ui.models;

import com.bigbeard.yatzystats.core.config.properties.UserPropertiesService;
import com.bigbeard.yatzystats.core.exceptions.RulesNotLoadedException;
import com.bigbeard.yatzystats.core.model.players.UserProperties;
import com.bigbeard.yatzystats.core.model.dto.GameRulesDTO;
import com.bigbeard.yatzystats.core.model.rules.GameRules;
import com.bigbeard.yatzystats.ui.GlobalController;
import com.bigbeard.yatzystats.ui.scenes.common.ExceptionAlertBuilder;
import com.bigbeard.yatzystats.ui.utils.LocaleUtils;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class GlobalUserModel {

    private final UserPropertiesService userPropertiesService;
    private final StatsSheetsUserModel statsSheetsUserModel;
    private final CreateSheetsUserModel createSheetsUserModel;
    private final ExceptionAlertBuilder exceptionAlertBuilder;
    private List<GameRules> availableGameRules;

    public GlobalUserModel() {
        this.userPropertiesService = new UserPropertiesService();
        this.statsSheetsUserModel = new StatsSheetsUserModel();
        this.createSheetsUserModel = new CreateSheetsUserModel();
        this.exceptionAlertBuilder = new ExceptionAlertBuilder(getResourceBundle());
        this.loadAllGameRules();
    }

    public ResourceBundle getResourceBundle() {
        String applicationLanguage = this.getUserProperties().applicationLanguage();
        Locale locale = LocaleUtils.resolveLocale(applicationLanguage);
        return ResourceBundle.getBundle("i18n.yatzycompanion", locale);
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

    public ExceptionAlertBuilder getExceptionAlertBuilder() {return this.exceptionAlertBuilder;}

    public void setUserProperties(UserProperties userProperties) {
        this.userPropertiesService.writeUserProperties(userProperties);
    }

    public List<GameRules> getAvailableGameRules() {
        return availableGameRules;
    }

}
