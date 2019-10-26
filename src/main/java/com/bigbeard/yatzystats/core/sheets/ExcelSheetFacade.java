package com.bigbeard.yatzystats.core.sheets;

import com.bigbeard.yatzystats.exceptions.CellNotFoundException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;

public class ExcelSheetFacade {

	public ExcelSheetFacade(){
		
	}

	//Renvoi d'exception à faire
	public int findPlayerIndex(Sheet sheet, String playerName){
		return this.findCellIndex(sheet, 0, playerName);
	}

	public List<String> reachPlayersList(org.apache.poi.ss.usermodel.Sheet sheet) throws CellNotFoundException {

		List<String> playersInGame = new ArrayList<String>();
		Row initialRow = sheet.getRow(0);
		if(initialRow == null) throw new CellNotFoundException("player", 0);
		java.util.Iterator<Cell> it = initialRow.iterator();


		while(it.hasNext()) {
			Cell currentCell = it.next();
			if(currentCell != null && currentCell.getColumnIndex() != 0){
				playersInGame.add(currentCell.getStringCellValue());
			}
		}

		return playersInGame;
	}


	//Simple Readers
	public Cell readCell(org.apache.poi.ss.usermodel.Sheet sheet, int row, int column)
	{
		Cell cellToReturn = null;
		Row yatzyRow = sheet.getRow(row);
		java.util.Iterator<Cell> itYatzy = yatzyRow.iterator();
		while(itYatzy.hasNext()) {
			Cell currentCell = itYatzy.next();
			if(currentCell.getColumnIndex() == column)
			{
				cellToReturn=currentCell;
				break;
			}
		}
		return cellToReturn;
	}
	
	public List<Cell> readCells(org.apache.poi.ss.usermodel.Sheet sheet, int row)
	{
		List<Cell> cellsToReturn = new ArrayList<Cell>();
		Row yatzyRow = sheet.getRow(row);
		java.util.Iterator<Cell> itYatzy = yatzyRow.iterator();
		while(itYatzy.hasNext()) {
			Cell currentCell = itYatzy.next();
			cellsToReturn.add(currentCell);
		}
		return cellsToReturn;
	}
	
	public int findCellIndex(org.apache.poi.ss.usermodel.Sheet sheet, int row, Object item)
	{
		int searchedItemIndex = 0;
		Row initialRow = sheet.getRow(row);
		java.util.Iterator<Cell> it = initialRow.iterator();
		
		while(it.hasNext()) {
			Cell currentCell = it.next();
			if(item.equals(currentCell.getStringCellValue())){ 
				searchedItemIndex = currentCell.getColumnIndex();
				break;
			}
		}
		return searchedItemIndex;
	}

}
