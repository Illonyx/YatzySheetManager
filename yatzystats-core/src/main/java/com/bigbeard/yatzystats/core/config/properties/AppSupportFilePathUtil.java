package com.bigbeard.yatzystats.core.config.properties;

import java.nio.file.Path;
import java.nio.file.Paths;

public class AppSupportFilePathUtil {
    // Utility method to get the file path in the Application Support directory
    public static Path getFilePath(String fileName) {
        String osName = System.getProperty("os.name").toLowerCase();
        String userHome = System.getProperty("user.home");
        String appConfigFolder = "YatzyCompanion";
        Path appSupportDir;

        if (osName.contains("mac")) {
            appSupportDir = Paths.get(userHome, "Library", "Application Support", appConfigFolder);
        } else if (osName.contains("win")) {
            String appData = System.getenv("APPDATA");
            if (appData == null) {
                throw new IllegalStateException("APPDATA environment variable is not set.");
            }
            appSupportDir = Paths.get(appData, appConfigFolder);
        } else {
            throw new UnsupportedOperationException("Unsupported OS: " + osName);
        }

        return appSupportDir.resolve(fileName);
    }
}
