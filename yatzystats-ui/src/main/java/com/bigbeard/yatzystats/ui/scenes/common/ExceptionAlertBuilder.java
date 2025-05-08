package com.bigbeard.yatzystats.ui.scenes.common;

import com.bigbeard.yatzystats.core.exceptions.ErrorCode;
import javafx.scene.control.Alert;

import java.util.ResourceBundle;

public class ExceptionAlertBuilder {

    ResourceBundle resourceBundle;

    public ExceptionAlertBuilder(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public Alert getWarningAlert(UserValidationWarningCode errorCode) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        return switch (errorCode) {
            case SHEET_CREATION_NO_PLAYER_SELECTION -> {
                alert.setTitle(resourceBundle.getString("exception.warning.creation.noplayerselection.title"));
                alert.setContentText(resourceBundle.getString("exception.warning.creation.noplayerselection.content"));
                yield alert;
            }
            case STATS_NO_GAME_SELECTION -> {
                alert.setTitle(resourceBundle.getString("exception.warning.stats.nogameselection.title"));
                alert.setContentText(resourceBundle.getString("exception.warning.stats.nogameselection.content"));
                yield alert;
            }
        };
    }

    public Alert getErrorAlert(ErrorCode errorCode) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        return switch (errorCode) {
            case RULES_UNREACHABLE_PATH -> {
                alert.setTitle(resourceBundle.getString("exception.error.rules.unreachable.title"));
                alert.setHeaderText(resourceBundle.getString("exception.error.rules.unreachable.header"));
                alert.setContentText(resourceBundle.getString("exception.error.rules.unreachable.content"));
                yield alert;
            }
            case RULES_JSON_PROCESSING_ERROR -> {
                alert.setTitle("Jamais tombé dans ce cas");
                yield alert;
            }
            case SHEET_FILE_INVALID_READ_SHEET_OPERATION -> {
                alert.setTitle(resourceBundle.getString("exception.error.sheet.opening.title"));
                alert.setContentText(resourceBundle.getString("exception.error.sheet.opening.content"));
                yield alert;
            }
            case SHEET_FILE_EMPTY_CONTENT -> {
                alert.setTitle(resourceBundle.getString("exception.error.sheet.empty.title"));
                alert.setHeaderText(resourceBundle.getString("exception.error.sheet.empty.header"));
                alert.setContentText(resourceBundle.getString("exception.error.sheet.empty.content"));
                yield alert;
            }
            case SHEET_FILE_INVALID_FILE_FORMAT -> {
                alert.setTitle(resourceBundle.getString("exception.error.sheet.invalidformat.title"));
                alert.setContentText(resourceBundle.getString("exception.error.sheet.invalidformat.content"));
                yield alert;
            }
        };
    }

}
