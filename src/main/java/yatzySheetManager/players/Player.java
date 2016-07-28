package yatzySheetManager.players;

import java.util.ArrayList;

public class Player {
	
	private String name;
	private ArrayList<PlayerResults> results;
	
	public Player(String playerName){
		this.name = playerName;
		this.results=new ArrayList<PlayerResults>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<PlayerResults> getResults() {
		return this.results;
	}
	
	public void updateResults(PlayerResults newResults){
		this.results.add(newResults);
	}
	
	//-----------------------------------------------------
	// Statistics
	//-----------------------------------------------------
	
	public void printStats(){
		PlayerStatsCalculator psc = new PlayerStatsCalculator();
		System.out.println("------------------------------------------------");
		System.out.println(this.name);
		System.out.println("------------------------------------------------");
		System.out.println("Winrate : " + psc.getWinRate(this.results));
		System.out.println("Mean : " + psc.getMean(this.results));
		System.out.println("High : " + psc.getHighestScore(this.results));
		System.out.println("Low : " + psc.getLowestScore(this.results));
		System.out.println("YatzyRate : " + psc.getYatzyRate(this.results));
		System.out.println("BonusRate : " + psc.getBonusRate(this.results));
		System.out.println("Standard Deviation : " + psc.getStandardDeviation(this.results));
		System.out.println("Moyenne du plus haut score : " + psc.getBestScoreMean(this.results));
	}
}
