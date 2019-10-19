package com.bigbeard.yatzystats.core.criterias;

import com.bigbeard.yatzystats.core.rules.GameRules;
import com.bigbeard.yatzystats.core.sheets.SheetReader;

import java.util.ArrayList;
import java.util.List;


public class CriteriaFiller {

	private SheetReader sheetReader;
	private GameRules gameRules;
	
	public CriteriaFiller(SheetReader sheetReader, GameRules gameRules){
		this.sheetReader = sheetReader;
		this.gameRules = gameRules;
	}
	
	public List<Criteria> generate(){
		List<Criteria> crits = new ArrayList<Criteria>();

		if(gameRules.getYatzyRow() != null){
			crits.add(new YatzyCriteria(this.sheetReader));
		}

		if(gameRules.getBonusRow() != null){
			crits.add(new BonusCriteria(this.sheetReader));
		}

		if(gameRules.getScoreRow() != null){
			crits.add(new ScoreCriteria(this.sheetReader));
			crits.add(new WinCriteria(this.sheetReader));
			crits.add(new BestScoreCriteria(this.sheetReader));
		}

		return crits;
	}
}
