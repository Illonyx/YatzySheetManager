package yatzySheetManager.players;

import java.util.ArrayList;

public class PlayerStatsCalculator {
	
	public PlayerStatsCalculator(){
		
	}

	
	public String getYatzyRate(ArrayList<PlayerResults> pr) {
		int yatzyNumber=0;
		int evaluableGames = 0;
		for(PlayerResults p : pr)
		{
			if(p.isYatzy != null){
				evaluableGames++;
				if(p.isYatzy.equals(true)) yatzyNumber++;
			}
			
		}
		double yatzyRate=100.0*yatzyNumber/evaluableGames;
		String str= String.valueOf(yatzyRate) + "% (" + yatzyNumber + "/" + evaluableGames + ")";
		return str;
	}
	
	public String getMean(ArrayList<PlayerResults> pr){
		double sum=0;
		for(PlayerResults y : pr)
		{
			sum += y.score;
		}
		double mean = sum/pr.size();
		return String.valueOf(mean);
	}
	
	public String getHighestScore(ArrayList<PlayerResults> pr)
	{
		double highest = 0;
		for(PlayerResults y : pr)
		{
			if(y.score>highest) highest=y.score;
		}
		return String.valueOf(highest);
	}
	
	public String getLowestScore(ArrayList<PlayerResults> pr){
		double lowest = 420;
		for(PlayerResults y : pr)
		{
			if(y.score<lowest) lowest=y.score;
		}
		return String.valueOf(lowest);
	}
	
	public String getWinRate(ArrayList<PlayerResults> pr){
		double sum=0;
		for(PlayerResults y : pr)
		{
			if(y.hasWon) sum++;
		}
		double yatzyRate=100.0*sum/pr.size();
		String str= String.valueOf(yatzyRate) + "% (" + sum + "/" + pr.size() + ")";
		return str;
	}
	
	public String getBonusRate(ArrayList<PlayerResults> pr){
		double sum=0;
		int evaluableGames = 0;
		for(PlayerResults y : pr)
		{
			if(y.hasBonus != null){
				evaluableGames++;
				if(y.hasBonus) sum++;
			}
			
		}
		double yatzyRate=100.0*sum/pr.size();
		String str= String.valueOf(yatzyRate) + "% évalué sur " + evaluableGames + " parties";
		return str;
	}
	
	public String getStandardDeviation(ArrayList<PlayerResults> pr){
		double sum=0;
		
		for(PlayerResults y : pr)
		{
			sum += y.score;
		}
		double mean = sum/pr.size();
		
		//Calcul de l'écart moyen
		double num=0.0;
		for(PlayerResults y : pr)
		{
			num += Math.abs(y.score - mean);
		}
		double stdDeviation=num/pr.size();
		double min = mean - stdDeviation;
		double max = mean + stdDeviation;
		return ("[" + String.valueOf(min) + ";" + String.valueOf(max) +"]");
	}
	
	//-------------------------------------------------------------------
	// Autres stats
	//-------------------------------------------------------------------
	
	public String getBestScoreMean(ArrayList<PlayerResults> pr){
		double sum=0;
		for(PlayerResults y : pr)
		{
			sum += y.bestScore;
		}
		double mean = sum/pr.size();
		return String.valueOf(mean);
	}
}
