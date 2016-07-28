package yatzySheetManager.criterias;

import yatzySheetManager.sheets.Sheet;


public class ScoreCriteria extends Criteria {
	private Sheet sheet;
		
		public ScoreCriteria(Sheet sheet){
			super(sheet.getSheetKind());
			this.sheet = sheet;
		}
		
		//Gérer le probleme quand une des valeurs est à null
		
		@Override
		public Object evaluate(String targetName){
			return this.sheet.readScore(targetName);
		}
}
