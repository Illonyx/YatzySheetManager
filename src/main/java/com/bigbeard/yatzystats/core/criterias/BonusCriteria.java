package com.bigbeard.yatzystats.core.criterias;

import com.bigbeard.yatzystats.core.sheets.SheetReader;

public class BonusCriteria implements Criteria {
	private SheetReader sheetReader;
	
	public BonusCriteria(SheetReader sheetReader){
		this.sheetReader = sheetReader;
	}
	
	@Override
	public Object evaluate(String targetName){
		double totalSumValue = this.sheetReader.readBonus(targetName);
		if(totalSumValue!=-1.0) return (totalSumValue>62);
		else return null;
	}
}
