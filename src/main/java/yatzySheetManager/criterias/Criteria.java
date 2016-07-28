package yatzySheetManager.criterias;

import yatzySheetManager.sheets.SheetKind;

public abstract class Criteria {
	
	SheetKind evaluationMode;
	
	//Un critère correspond à un calcul donné qui sera évalué
	public Criteria(SheetKind mode){
		this.evaluationMode = mode;
	}
	
	public Object evaluate(String targetName){
		return null;
		
	}
	
}
