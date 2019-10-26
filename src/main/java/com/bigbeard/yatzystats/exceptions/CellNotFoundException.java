package com.bigbeard.yatzystats.exceptions;

import javafx.scene.control.Cell;

/**
 * Erreur métier désignant le fait qu'une valeur n'ait pas été trouvée dans la lecture du fichier Excel
 */
public class CellNotFoundException extends Exception {

    private String cellLabel;
    private int position;

    public CellNotFoundException(String cellLabel, int position){
        this.cellLabel = cellLabel;
        this.position = position;
    }

    public String getCellLabel() {
        return cellLabel;
    }

    public void setCellLabel(String cellLabel) {
        this.cellLabel = cellLabel;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
