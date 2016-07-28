package yatzySheetManager.utils;

import java.util.ArrayList;

import yatzySheetManager.players.Player;

public class MainAgentUtils {
	
	public static ArrayList<String> accessPlayerNames(ArrayList<Player> players){
		ArrayList<String> playerNames = new ArrayList<String>();
		for(Player p : players) playerNames.add(p.getName());
		return playerNames;
	}
	
	
}
