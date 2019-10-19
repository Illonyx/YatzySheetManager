package yatzySheetManager.sheets;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;

public class ScandinavianSheetPOI extends SheetPOI {
	
	private int SC_SCORE_ROW = 20;
	private int SC_YATZY_ROW = 17;
	private int SC_BONUS_ROW = 8;
	private double UNEVALUABLE_CRITERIA = -1.0;
	
	public ScandinavianSheetPOI(org.apache.poi.ss.usermodel.Sheet sheet, org.apache.poi.ss.usermodel.FormulaEvaluator evaluator){
		super(sheet,evaluator);
	}
	
	public double readPlayerScore(String targetName){
		Cell cellToReturn = this.sba.readCell(this.sheet, this.SC_SCORE_ROW, this.findPlayerIndex(targetName));
		if(cellToReturn != null) return cellToReturn.getNumericCellValue();
		else return UNEVALUABLE_CRITERIA;
	}
	
	public double readYatzyCell(String targetName){
		Cell cellToReturn = this.sba.readCell(this.sheet, this.SC_YATZY_ROW, this.findPlayerIndex(targetName));
		if(cellToReturn != null) return cellToReturn.getNumericCellValue();
		else return UNEVALUABLE_CRITERIA;
	}
	
	public double readBonusCell(String targetName){
		Cell cellToReturn = this.sba.readCell(this.sheet, this.SC_BONUS_ROW, this.findPlayerIndex(targetName));
		if(cellToReturn != null) return cellToReturn.getNumericCellValue();
		else return UNEVALUABLE_CRITERIA;
	}

	@Override
	public double readBestScore() {
		double bestScore = 0.0;
		
		ArrayList<Cell> scoreCells = sba.readCells(this.sheet, SC_SCORE_ROW);
		for(Cell c : scoreCells){
			double valueEvaluated = this.evaluator.evaluate(c).getNumberValue();
			bestScore = Math.max(bestScore, valueEvaluated);
		}
		
		return bestScore;
	}

	@Override
	public void writeNewSheet() {
		
		
		
	}
}
