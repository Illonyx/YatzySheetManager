package yatzySheetManager.criterias;

import yatzySheetManager.sheets.Sheet;

public class YatzyCriteria extends Criteria{
	
private Sheet sheet;
	
	public YatzyCriteria(Sheet sheet2){
		super(sheet2.getSheetKind());
		this.sheet = sheet2;
	}
	
	//Gérer le probleme quand une des valeurs est à null
	
	@Override
	public Object evaluate(String targetName){
		
		double yatzyValueCell = this.sheet.readYatzy(targetName);
		if(yatzyValueCell != -1.0) return (yatzyValueCell==50.0);
		else return null;
	}
}
