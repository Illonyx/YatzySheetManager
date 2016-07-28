package yatzySheetManager.criterias;

import yatzySheetManager.sheets.Sheet;

//Concrete criteria
public class WinCriteria extends Criteria{
	
	private Sheet sheet;
	
	public WinCriteria(Sheet sheet2){
		super(sheet2.getSheetKind());
		this.sheet = sheet2;
	}
	
	@Override
	public Object evaluate(String targetName){
		return this.sheet.readIsPlayerWinning(targetName);
	}
	
}
