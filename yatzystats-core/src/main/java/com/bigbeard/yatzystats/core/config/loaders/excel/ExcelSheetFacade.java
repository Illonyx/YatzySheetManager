package com.bigbeard.yatzystats.core.config.loaders.excel;

import com.bigbeard.yatzystats.core.exceptions.CellNotFoundException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;
import java.util.stream.StreamSupport;

public class ExcelSheetFacade {

    private static final int PLAYER_COLUMN_INDEX = 0;

    public ExcelSheetFacade() {

    }

    public int findPlayerIndex(Sheet sheet, String playerName) throws CellNotFoundException {
        return this.findCellIndex(sheet, PLAYER_COLUMN_INDEX, playerName);
    }

    public List<String> getPlayersList(org.apache.poi.ss.usermodel.Sheet sheet) throws CellNotFoundException {
        Row initialRow = getRowOrThrow(sheet, PLAYER_COLUMN_INDEX);
        return StreamSupport.stream(initialRow.spliterator(), false)
                .filter(cell -> cell.getColumnIndex() != PLAYER_COLUMN_INDEX)
                .map(Cell::getStringCellValue)
                .toList();
    }


    //Simple Readers
    public Cell readCell(org.apache.poi.ss.usermodel.Sheet sheet, int row, int column) throws CellNotFoundException {
        Row yatzyRow = getRowOrThrow(sheet, --row);

        return StreamSupport.stream(yatzyRow.spliterator(), false)
                .filter(cell -> cell.getColumnIndex() == column)
                .findFirst().orElse(null);
    }

    public Integer findCellIndex(org.apache.poi.ss.usermodel.Sheet sheet, int row, String item) throws CellNotFoundException {
        Row initialRow = getRowOrThrow(sheet, row);

        return StreamSupport.stream(initialRow.spliterator(), false)
                .filter(cell -> item.equals(cell.getStringCellValue()))
                .findFirst()
                .map(Cell::getColumnIndex).orElse(null);
    }

    private Row getRowOrThrow(Sheet sheet, int rowIndex) throws CellNotFoundException {
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            throw new CellNotFoundException("Row " + rowIndex + " not found.", rowIndex);
        }
        return row;
    }

}
