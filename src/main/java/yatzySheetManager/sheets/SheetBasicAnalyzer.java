package yatzySheetManager.sheets;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class SheetBasicAnalyzer {
	
	public SheetBasicAnalyzer(){
		
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
	
	public ArrayList<Cell> readCells(org.apache.poi.ss.usermodel.Sheet sheet, int row)
	{
		ArrayList<Cell> cellsToReturn = new ArrayList<Cell>();
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
	
	//Simple Writers
	
	public void createSheetStructure(org.apache.poi.ss.usermodel.Sheet sheet, int rows, int columns){
		//sheet.
	}
}
