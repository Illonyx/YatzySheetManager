package yatzySheetManager.criterias;

import java.util.ArrayList;

import yatzySheetManager.sheets.Sheet;
import yatzySheetManager.sheets.SheetKind;

public class CriteriaFiller {
	
	private SheetKind kind;
	private Sheet sheet;
	
	public CriteriaFiller(Sheet sheet){
		this.sheet = sheet;
		this.kind = sheet.getSheetKind();
	}
	
	public ArrayList<Criteria> generate(){
		ArrayList<Criteria> crits = new ArrayList<Criteria>();
		switch(this.kind){
		case SCANDINAVIAN_YATZEE : return generateForScandinavianYatzee();
		case FRENCH_YATZEE : return crits;
		default : return generateForScandinavianYatzee();
		}
		
	}
	
	private ArrayList<Criteria> generateForScandinavianYatzee(){
		ArrayList<Criteria> crits = new ArrayList<Criteria>();
		crits.add(new YatzyCriteria(this.sheet));
		crits.add(new WinCriteria(this.sheet));
		crits.add(new BonusCriteria(this.sheet));
		crits.add(new ScoreCriteria(this.sheet));
		crits.add(new BestScoreCriteria(this.sheet));
		return crits;
	}
}
