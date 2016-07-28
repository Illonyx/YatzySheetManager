package yatzySheetAnalyzer.app;

import java.nio.file.Paths;

import yatzySheetManager.agent.CollectDataAgent;
import yatzySheetManager.agent.MainAgent;
import yatzySheetManager.players.Player;

public class SecondMain {
	
	public static void main(String[] args){
		MainAgent.getInstance().preferedSheets = Paths.get("src/main/resources/games-data/scoresAll");
		printPlayer("Alexis");
		printPlayer("Papa");
		printPlayer("Maman");
		printPlayer("Arnaud");
	}
	
	public static void printPlayer(String str){
		Player p = new Player(str);
		CollectDataAgent cda = new CollectDataAgent(p);
		cda.collect();
		p.printStats();
	}
	
	
	
	
}
