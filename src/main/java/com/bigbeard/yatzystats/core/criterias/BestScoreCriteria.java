package com.bigbeard.yatzystats.core.criterias;

import com.bigbeard.yatzystats.core.sheets.SheetReader;

public class BestScoreCriteria implements Criteria {
	private SheetReader sheetReader;
	
	public BestScoreCriteria(SheetReader sheetReader){
		this.sheetReader = sheetReader;
	}
	
	@Override
	public Object evaluate(String targetName){
		return this.sheetReader.readBestScore();
	}
}
