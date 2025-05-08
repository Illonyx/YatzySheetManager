package com.bigbeard.yatzystats.ui.scenes.common;

import com.bigbeard.yatzystats.core.model.rules.GameRules;
import com.bigbeard.yatzystats.core.model.rules.GameRulesEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.ResourceBundle;

public class RulesDialog {
    Dialog dialog;
    GameRules rules;
    ResourceBundle resourceBundle;

    String untilValueLabel;
    String bonusConditionLabel;
    String combinationsColumnLabel;
    String valuesColumnLabel;

    public RulesDialog(GameRules gameRules, ResourceBundle resourceBundle) {
        this.dialog = new Dialog<>();
        this.rules = gameRules;
        this.resourceBundle = resourceBundle;
        this.dialog.setTitle(rules.formatDescription());
        this.initI18N();
        this.initDialog();
    }

    private void initI18N() {
        this.untilValueLabel = this.resourceBundle.getString("dialog.rules.untilvalue");
        this.bonusConditionLabel = this.resourceBundle.getString("dialog.rules.bonuscond");
        this.combinationsColumnLabel = this.resourceBundle.getString("dialog.rules.combinationscol");
        this.valuesColumnLabel = this.resourceBundle.getString("dialog.rules.valuescol");
    }

    private void initDialog() {
        // Create a TableView with two columns
        TableView<CombinationData> tableView = new TableView<>();
        TableColumn<CombinationData, String> combinationsColumn = new TableColumn<>(combinationsColumnLabel);
        TableColumn<CombinationData, String> valeursColumn = new TableColumn<>(valuesColumnLabel);

        // Set the cell value factories for the columns
        combinationsColumn.setCellValueFactory(new PropertyValueFactory<>("combinations"));
        valeursColumn.setCellValueFactory(new PropertyValueFactory<>("valeurs"));

        // Add columns to the TableView
        tableView.getColumns().addAll(combinationsColumn, valeursColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Create sample data
        ObservableList<CombinationData> data = FXCollections.observableList(this.getCombinationsData());

        // Set the data to the TableView
        tableView.setItems(data);

        // Create and configure a dialog
        String bonusConditionString = String.format(bonusConditionLabel, rules.bonusVal(), rules.bonusCond());
        dialog.setHeaderText(bonusConditionString);

        // Set the content of the dialog to the TableView
        dialog.getDialogPane().setContent(tableView);

        // Add OK button to close the dialog
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(okButton);
    }

    private List<CombinationData> getCombinationsData() {
        List<String> combinationsNotDisplayed = List.of(GameRulesEnum.PARTIAL_SUM.getValue(), GameRulesEnum.FINAL_SUM.getValue());
        return this.rules.getColumnDescriptionsFromMap().stream().filter(columnDescription ->
                !combinationsNotDisplayed.contains(columnDescription.techColumnId())
        ).map(columnDescription -> {
            String combinationLabel = columnDescription.columnLabel();
            String adaptedFixedValueLabel = columnDescription.fixedValue() ? "" : untilValueLabel + " ";
            String valueLabel = adaptedFixedValueLabel.concat(columnDescription.maxValue().toString());
            return new CombinationData(combinationLabel, valueLabel);
        }).toList();
    }

    public Dialog getDialog() {
        return dialog;
    }

    public static class CombinationData {
        private String combinations;
        private String valeurs;

        public CombinationData(String combinations, String valeurs) {
            this.combinations = combinations;
            this.valeurs = valeurs;
        }

        public String getCombinations() {
            return combinations;
        }

        public String getValeurs() {
            return valeurs;
        }
    }

}
