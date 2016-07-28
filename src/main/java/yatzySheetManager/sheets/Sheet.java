package yatzySheetManager.sheets;

import java.util.ArrayList;

public class Sheet {
	
	private SheetRecognizer sheetRecognizer;
	private SheetPOI sheetPOI;
	public int ONES, TWOS, THREES, FOURS, FIVES, SIXS;
	public int SCORE;
	
	public Sheet(org.apache.poi.ss.usermodel.Sheet sheet, org.apache.poi.ss.usermodel.FormulaEvaluator evaluator, SheetKind kind){
		this.sheetRecognizer = new SheetRecognizer(sheet,evaluator);
		this.sheetPOI = new ScandinavianSheetPOI(sheet,evaluator);
		//this.getSheetKind();
	}
	
	public Sheet(org.apache.poi.ss.usermodel.Sheet sheet, org.apache.poi.ss.usermodel.FormulaEvaluator evaluator){
		this.sheetRecognizer = new SheetRecognizer(sheet,evaluator);
		this.sheetPOI = new ScandinavianSheetPOI(sheet,evaluator);
	}
	
	//------------------------------------------------------------------------------------------------------
	// Gettlers
	//------------------------------------------------------------------------------------------------------
	
	public SheetKind getSheetKind(){
		return sheetRecognizer.determineSheetKind();
	}
	
	public ArrayList<String> getPlayersList(){
		return sheetPOI.reachPlayersList();
	}
	
	//-------------------------------------------------------------------------------------------------------
	// Readers
	//-------------------------------------------------------------------------------------------------------
	
	public int readOnes(){
		return 1;
	}
	
	public void readTwos(){
		
	}
	
	public void readThrees(){
		
	}
	
	public void readFours(){
		
	}
	
	public void readFives(){
		
	}
	
	public void readSixs(){
		
	}
	
	public double readScore(String targetName){
		return this.sheetPOI.readPlayerScore(targetName);
	}
	
	public double readBonus(String targetName){
		return this.sheetPOI.readBonusCell(targetName);
	}
	
	public double readYatzy(String targetName){
		return this.sheetPOI.readYatzyCell(targetName);
	}
	
	public Boolean readIsPlayerWinning(String targetName){
		return this.sheetPOI.readPlayerScore(targetName) == this.sheetPOI.readBestScore();
	}
	
	public double readBestScore(){
		return this.sheetPOI.readBestScore();
	}
	
	//--------------------------------------------------------------------------------------------------------
	// Writers
	//--------------------------------------------------------------------------------------------------------
	
	public void prepareNewSheet(){
		System.out.println("Sheet is being prepared");
		this.sheetPOI.writeNewSheet();
	}
	
}
