package com.bigbeard.yatzystats.core.criterias;

import com.bigbeard.yatzystats.core.sheets.SheetReader;

public class YatzyCriteria implements Criteria {
	
private SheetReader sheetReader;
	
	public YatzyCriteria(SheetReader sheetReader){
		this.sheetReader = sheetReader;
	}
	
	@Override
	public Object evaluate(String targetName){
		
		double yatzyValueCell = this.sheetReader.readYatzy(targetName);
		if(yatzyValueCell != -1.0) return (yatzyValueCell==50.0);
		else return null;
	}
}
