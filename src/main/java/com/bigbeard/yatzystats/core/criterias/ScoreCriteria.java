package com.bigbeard.yatzystats.core.criterias;

import com.bigbeard.yatzystats.core.sheets.SheetReader;
import yatzySheetManager.sheets.Sheet;


public class ScoreCriteria implements Criteria {
	private SheetReader sheetReader;

		public ScoreCriteria(SheetReader sheetReader){
			this.sheetReader = sheetReader;
		}
		
		//Gérer le probleme quand une des valeurs est à null
		
		@Override
		public Object evaluate(String targetName){
			return this.sheetReader.readScore(targetName);
		}
}
