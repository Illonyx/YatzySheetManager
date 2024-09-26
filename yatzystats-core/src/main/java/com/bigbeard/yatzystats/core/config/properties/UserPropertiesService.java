package com.bigbeard.yatzystats.core.config.properties;

import com.bigbeard.yatzystats.core.model.players.UserProperties;

public class UserPropertiesService extends PropertiesService {

    public UserPropertiesService() {
        super("user-preferences.properties");
    }

    public UserProperties readUserProperties() {
        return new UserProperties(
                readUserCreationPath(),
                readApplicationLanguage(),
                readSheetDefaultRule()
        );
    }

    public void writeUserProperties(UserProperties userProperties) {
        this.writeUserCreationPath(userProperties.sheetCreationPath());
        this.writeApplicationLanguage(userProperties.applicationLanguage());
        this.writeSheetDefaultRule(userProperties.defaultRulesFile());
    }

    private String readUserCreationPath() {
        return super.getProperty("sheet.creation.path");
    }

    private String readApplicationLanguage() {
        return super.getProperty("application.language");
    }

    private String readSheetDefaultRule() {
        return super.getProperty("sheet.default.rule");
    }

    private void writeUserCreationPath(String userCreationPath) {
        super.setProperty("sheet.creation.path", userCreationPath);
    }

    private void writeApplicationLanguage(String applicationLanguage) {
        super.setProperty("application.language", applicationLanguage);
    }

    private void writeSheetDefaultRule(String sheetDefaultRule) {
        super.setProperty("sheet.default.rule", sheetDefaultRule);
    }
}
