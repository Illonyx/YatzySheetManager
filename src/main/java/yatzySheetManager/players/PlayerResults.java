package yatzySheetManager.players;

import java.util.ArrayList;

import yatzySheetManager.criterias.BestScoreCriteria;
import yatzySheetManager.criterias.BonusCriteria;
import yatzySheetManager.criterias.Criteria;
import yatzySheetManager.criterias.ScoreCriteria;
import yatzySheetManager.criterias.WinCriteria;
import yatzySheetManager.criterias.YatzyCriteria;

public class PlayerResults {
	
	//Et probablement d'autres données..
	
	
	//Données d'entrée
	private ArrayList<Criteria> criterias;
	private String targetName;
	
	//Données générées
	public Boolean isYatzy = null;
	public Boolean hasWon = null;
	public Boolean hasBonus = null;
	public double bestScore = -1.0;
	public double score = -1.0;

	
	
	public PlayerResults(String targetName, ArrayList<Criteria> criterias){
		this.criterias = criterias;
		this.targetName = targetName;
	}
	
	public void evaluate(){
		for(Criteria c : this.criterias){
			
			if(c instanceof YatzyCriteria) this.isYatzy = (Boolean) c.evaluate(this.targetName);
			else if (c instanceof ScoreCriteria) this.score = (double) c.evaluate(this.targetName);
			else if (c instanceof WinCriteria) this.hasWon = (Boolean) c.evaluate(this.targetName);
			else if (c instanceof BonusCriteria) this.hasBonus = (Boolean) c.evaluate(this.targetName);
			else if (c instanceof BestScoreCriteria) this.bestScore = (double) c.evaluate(this.targetName);
			
		}
	}
	
}
