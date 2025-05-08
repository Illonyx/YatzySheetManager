package com.bigbeard.yatzystats.core.config;

import com.bigbeard.yatzystats.core.config.properties.AppSupportFilePathUtil;
import com.bigbeard.yatzystats.core.exceptions.ErrorCode;
import com.bigbeard.yatzystats.core.exceptions.RulesNotLoadedException;
import com.bigbeard.yatzystats.core.model.dto.GameRulesDTO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GameRulesLoaderV2 {

    private final List<GameRulesDTO> gameRuleFiles;
    private Logger logger = LogManager.getLogger(GameRulesLoaderV2.class);

    public GameRulesLoaderV2() throws IOException, RulesNotLoadedException {
        Path rulesFolderPath = AppSupportFilePathUtil.getFilePath("rules");
        if(!Files.exists(rulesFolderPath)) {
            Files.createDirectory(rulesFolderPath);
            List<String> defaultFiles = List.of("scandinavian-yatzy-rules.json", "scandinavian-maxiyatzy-rules.json");
            defaultFiles.forEach(file -> {
                try {
                    copyFileFromJar("rules/" + file,
                            Path.of(rulesFolderPath.toString(), file));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        this.gameRuleFiles = this.loadRulesInFolder(rulesFolderPath.toString());
    }

    private List<GameRulesDTO> loadRulesInFolder(String folderPath) throws IOException, RulesNotLoadedException {
        try (var filesStream = Files.walk(Path.of(folderPath))) {
            return filesStream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".json"))
                    .map(path -> {
                        try {
                            return loadGameRules(path);
                        } catch (RulesNotLoadedException rex) {
                            throw new RuntimeException(rex);
                        }
                    })
                    .toList();
        } catch (RuntimeException e) {
            if (e.getCause() instanceof RulesNotLoadedException) {
                throw (RulesNotLoadedException) e.getCause();  // Rethrow the original exception
            } else {
                throw e;  // If it's another exception, rethrow as is
            }
        }
    }

    private GameRulesDTO loadGameRules(Path path) throws RulesNotLoadedException {
        try {
            final Gson gson = new GsonBuilder().create();
            InputStream inputStream = Files.newInputStream(Path.of(path.toString()));
            Reader reader = new InputStreamReader(inputStream);
            return gson.fromJson(reader, GameRulesDTO.class);
        } catch(JsonSyntaxException | JsonIOException ex) {
            throw new RulesNotLoadedException(ErrorCode.RULES_JSON_PROCESSING_ERROR, "Exception déclenchée" + ex);
        } catch (Exception ex) {
            throw new RulesNotLoadedException(ErrorCode.RULES_UNREACHABLE_PATH, "Exception déclenchée" + ex);
        }
    }

    private void copyFileFromJar(String resourcePath, Path targetPath) throws IOException {
        // Open the file inside the JAR as an InputStream
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IOException("Resource not found: " + resourcePath);
            }

            // Copy the InputStream to the target file path
            Files.copy(inputStream, targetPath);
        }
    }

    public List<GameRulesDTO> getGameRuleFiles() {
        return gameRuleFiles;
    }
}
