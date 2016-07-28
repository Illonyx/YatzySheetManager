package yatzySheetManager.criterias;

import yatzySheetManager.sheets.Sheet;

public class BestScoreCriteria extends Criteria {
	private Sheet sheet;
	
	public BestScoreCriteria(Sheet sheet){
		super(sheet.getSheetKind());
		this.sheet = sheet;
	}
	
	//Gérer le probleme quand une des valeurs est à null
	
	@Override
	public Object evaluate(String targetName){
		return this.sheet.readBestScore();
	}
}
