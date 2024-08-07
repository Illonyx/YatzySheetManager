package com.bigbeard.yatzystats.core.model.players;

public class UserProperties {
    private String sheetCreationPath;

    private String applicationLanguage;

    private String defaultRulesFile;

    public UserProperties(String sheetCreationPath, String applicationLanguage, String defaultRulesFile) {
        this.sheetCreationPath = sheetCreationPath;
        this.applicationLanguage = applicationLanguage;
        this.defaultRulesFile = defaultRulesFile;
    }

    public String getSheetCreationPath() {
        return sheetCreationPath;
    }

    public void setSheetCreationPath(String sheetCreationPath) {
        this.sheetCreationPath = sheetCreationPath;
    }

    public String getApplicationLanguage() {
        return applicationLanguage;
    }

    public void setApplicationLanguage(String applicationLanguage) {
        this.applicationLanguage = applicationLanguage;
    }

    public String getDefaultRulesFile() {
        return defaultRulesFile;
    }

    public void setDefaultRulesFile(String defaultRulesFile) {
        this.defaultRulesFile = defaultRulesFile;
    }
}
