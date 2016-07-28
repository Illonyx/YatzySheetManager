package yatzySheetManager.criterias;

import yatzySheetManager.sheets.Sheet;

public class BonusCriteria extends Criteria {
	private Sheet sheet;
	
	public BonusCriteria(Sheet sheet){
		super(sheet.getSheetKind());
		this.sheet = sheet;
	}
	
	@Override
	public Object evaluate(String targetName){
		
		double totalSumValue = this.sheet.readBonus(targetName);
		if(totalSumValue!=-1.0) return (totalSumValue>62);
		else return null;
		
	}
}
