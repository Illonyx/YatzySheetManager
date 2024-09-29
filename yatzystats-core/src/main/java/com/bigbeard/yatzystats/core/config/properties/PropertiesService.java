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
        this.propertiesFilePath = AppSupportFilePathUtil.getFilePath(propertiesFileName);
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
}
