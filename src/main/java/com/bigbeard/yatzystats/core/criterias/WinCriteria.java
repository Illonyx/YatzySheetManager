package com.bigbeard.yatzystats.core.criterias;

import com.bigbeard.yatzystats.core.sheets.SheetReader;

//Concrete criteria
public class WinCriteria implements Criteria {
	
	private SheetReader sheetReader;
	
	public WinCriteria(SheetReader sheetReader){
		this.sheetReader = sheetReader;
	}
	
	@Override
	public Object evaluate(String targetName){
		return this.sheetReader.readIsPlayerWinning(targetName);
	}
	
}
