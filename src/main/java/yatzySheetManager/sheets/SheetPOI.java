package yatzySheetManager.sheets;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public abstract class SheetPOI {
	
	SheetKind kind;
	
	protected org.apache.poi.ss.usermodel.Sheet sheet;
	protected org.apache.poi.ss.usermodel.FormulaEvaluator evaluator;
	protected SheetBasicAnalyzer sba = new SheetBasicAnalyzer();
	
	protected SheetPOI(org.apache.poi.ss.usermodel.Sheet sheet, org.apache.poi.ss.usermodel.FormulaEvaluator evaluator){
		this.sheet=sheet;
		this.evaluator=evaluator;
		this.kind = determineSheetKind();
	}
	
	//Renvoi d'exception à faire
	protected int findPlayerIndex(String playerName){
		return sba.findCellIndex(this.sheet, 0, playerName);
	}
	
	//-------------------------------------------------------------------------------
	// Méthodes abstraites (Contrat)
	//-------------------------------------------------------------------------------
	public abstract double readPlayerScore(String targetName);
	public abstract double readYatzyCell(String targetName);
	public abstract double readBonusCell(String targetName);
	public abstract double readBestScore();
	
	public abstract void writeNewSheet();
	
	public Boolean isFinished(){
		return false;
	}
	
	public SheetKind determineSheetKind(){
		return SheetKind.SCANDINAVIAN_YATZEE;
	}
	
	public ArrayList<String> reachPlayersList(){
		
		ArrayList<String> playersInGame = new ArrayList<String>();
		Row initialRow = this.sheet.getRow(0);
		java.util.Iterator<Cell> it = initialRow.iterator();
		
		
		while(it.hasNext()) {
			Cell currentCell = it.next();
			if(currentCell.getColumnIndex() != 0){
				playersInGame.add(currentCell.getStringCellValue());
			}
		}
		
		return playersInGame;
	}
	
	
	
	/*
	 * 
	//Writers - Creator Part
	public void writeOnes(int scoreToWrite, String playerToNotify);
	public void writeTwos(int scoreToWrite, String playerToNotify);
	public void writeThrees(int scoreToWrite, String playerToNotify);
	public void writeFours(int scoreToWrite, String playerToNotify);
	public void writeFives(int scoreToWrite, String playerToNotify);
	public void writeSixs(int scoreToWrite, String playerToNotify);
	
	//Readers - Analyser Part
	public ArrayList<Integer> readOnes();
	public ArrayList<Integer> readTwos();
	public ArrayList<Integer> readThrees();
	public ArrayList<Integer> readFours();
	public ArrayList<Integer> readFives();
	public ArrayList<Integer> readSixs();
	
	
	 * 
	 * 
	 */
}
