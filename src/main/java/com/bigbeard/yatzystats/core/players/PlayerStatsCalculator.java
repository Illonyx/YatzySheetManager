package com.bigbeard.yatzystats.core.players;

import java.util.List;

public class PlayerStatsCalculator {

    public PlayerStatsCalculator(){

    }

    public String getYatzyRate(List<PlayerResult> prs) {
        int yatzyNumber=0;
        int evaluableGames = 0;
        for(PlayerResult p : prs)
        {
            evaluableGames++;
            if(p.isHasYatzy()) yatzyNumber++;
        }
        double yatzyRate=100.0*yatzyNumber/evaluableGames;
        String str= String.valueOf(yatzyRate) + "% (" + yatzyNumber + "/" + evaluableGames + ")";
        return str;
    }

    public String getMean(List<PlayerResult> prs){
        double sum=0;
        for(PlayerResult pr : prs)
        {
            sum += pr.getScore();
        }
        double mean = sum/prs.size();
        return String.valueOf(mean);
    }

    public String getHighestScore(List<PlayerResult> prs)
    {
        double highest = 0;
        for(PlayerResult pr : prs)
        {
            if(pr.getScore() > highest) highest = pr.getScore();
        }
        return String.valueOf(highest);
    }

    public String getLowestScore(List<PlayerResult> prs){
        double lowest = 420;
        for(PlayerResult pr : prs)
        {
            if(pr.getScore() < lowest) lowest = pr.getScore();
        }
        return String.valueOf(lowest);
    }

    public String getWinRate(List<PlayerResult> prs){
        double sum=0;
        for(PlayerResult pr : prs)
        {
            if(pr.isWinner()) sum++;
        }
        double yatzyRate=100.0*sum/prs.size();
        String str= String.valueOf(yatzyRate) + "% (" + sum + "/" + prs.size() + ")";
        return str;
    }

    public String getBonusRate(List<PlayerResult> prs){
        double sum=0;
        int evaluableGames = 0;
        for(PlayerResult pr : prs)
        {
            evaluableGames++;
            if(pr.isHasBonus()){
                sum++;
            }
        }
        double yatzyRate=100.0*sum/prs.size();
        String str= String.valueOf(yatzyRate) + "% évalué sur " + evaluableGames + " parties";
        return str;
    }

    public String getStandardDeviation(List<PlayerResult> prs){
        double sum=0;

        for(PlayerResult pr : prs)
        {
            sum += pr.getScore();
        }
        double mean = sum/prs.size();

        //Calcul de l'écart moyen
        double num=0.0;
        for(PlayerResult pr : prs)
        {
            num += Math.abs(pr.getScore() - mean);
        }
        double stdDeviation=num/prs.size();
        double min = mean - stdDeviation;
        double max = mean + stdDeviation;
        return ("[" + String.valueOf(min) + ";" + String.valueOf(max) +"]");
    }

    //-------------------------------------------------------------------
    // Autres stats
    //-------------------------------------------------------------------

// FIXME : A traiter avec les sheetdto
//    public String getBestScoreMean(List<PlayerResult> prs){
//        double sum=0;
//        for(PlayerResult pr : prs)
//        {
//            sum += pr.getBestScore();
//        }
//        double mean = sum/prs.size();
//        return String.valueOf(mean);
//    }
}