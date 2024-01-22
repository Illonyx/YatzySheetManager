package com.bigbeard.yatzystats.ui.scenes.common;

import com.bigbeard.yatzystats.core.config.GameRulesLoader;
import com.bigbeard.yatzystats.core.exceptions.RulesNotLoadedException;
import com.bigbeard.yatzystats.core.model.rules.GameRules;
import com.bigbeard.yatzystats.core.model.rules.SheetRulesIdentifiers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.stream.Collectors;

public class RulesDialog {

    Dialog dialog;
    GameRules rules;

    public RulesDialog(SheetRulesIdentifiers identifiers) {
        this.dialog = new Dialog<>();
        try {
            GameRulesLoader loader = new GameRulesLoader(identifiers);
            this.rules = loader.getGameRules();
            this.dialog.setTitle(rules.getFormatName());
            this.initDialog();
        } catch (RulesNotLoadedException e) {
            throw new RuntimeException(e);
        }
    }

    private void initDialog() {
        // Create a TableView with two columns
        TableView<CombinationData> tableView = new TableView<>();
        TableColumn<CombinationData, String> combinationsColumn = new TableColumn<>("Combinaisons");
        TableColumn<CombinationData, String> valeursColumn = new TableColumn<>("Valeurs");

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
        StringBuffer buff = new StringBuffer();
        buff.append("Le bonus est d'une valeur de ");
        buff.append(rules.getBonusVal());
        buff.append(" et est accordé si la somme des premières colonnes est supérieure ou égale à ");
        buff.append(rules.getBonusCond());
        dialog.setHeaderText(buff.toString());

        // Set the content of the dialog to the TableView
        dialog.getDialogPane().setContent(tableView);

        // Add OK button to close the dialog
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(okButton);
    }

    private List<CombinationData> getCombinationsData() {
        return this.rules.getColumnsList().stream().filter(columnDescription ->
                !columnDescription.getTechColumnId().equals("partialSum") && !columnDescription.getTechColumnId().equals("finalSum")
        ).map(columnDescription -> {
            String combinationLabel = columnDescription.getColumnLabel();
            String adaptedFixedValueLabel = columnDescription.isFixedValue() ? "" : "Jusqu'à ";
            String valueLabel = adaptedFixedValueLabel.concat(columnDescription.getMaxValue().toString());
            return new CombinationData(combinationLabel, valueLabel);
        }).collect(Collectors.toList());
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
