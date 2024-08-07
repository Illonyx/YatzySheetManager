package com.bigbeard.yatzystats.core.config.properties;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesService {

    private final Properties properties = new Properties();
    private final Path propertiesFilePath;

    public PropertiesService(String propertiesFileName) {
        this.propertiesFilePath = getAppSupportFilePath(propertiesFileName);
        loadProperties();
    }

    // Load properties from the file
    private void loadProperties() {
        if (Files.exists(propertiesFilePath)) {
            try (var input = Files.newInputStream(propertiesFilePath)) {
                properties.load(input);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Properties file does not exist: " + propertiesFilePath);
        }
    }

    // Get a property value by key
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    // Set a property value by key
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
        saveProperties();
    }

    // Save properties to the file
    private void saveProperties() {
        try {
            // Ensure the directory exists
            Files.createDirectories(propertiesFilePath.getParent());
            try (var output = Files.newOutputStream(propertiesFilePath)) {
                properties.store(output, null);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Utility method to get the file path in the Application Support directory
    private Path getAppSupportFilePath(String fileName) {
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
