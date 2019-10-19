package yatzySheetManager.agent;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import yatzySheetManager.players.Player;
import yatzySheetManager.sheets.Sheet;
import yatzySheetManager.sheets.SheetKind;
import yatzySheetManager.utils.MainAgentUtils;

public class WriterAgent {
	
	//Players who will be registered in the sheet
	private Sheet targetSheet;
	private ArrayList<Player> gamePlayers;
	private String gameId;
	private int TEST_GAME_ID = 0;
	private SheetKind sheetKind;
	
	//For a new Game
	public WriterAgent(ArrayList<Player> players, SheetKind kind){
		this.gamePlayers = players;
		this.gameId = createGameId();
		this.sheetKind = kind;
	}
	
	//For a game which is not finished
	public WriterAgent(String gameId){
		this.gamePlayers=null;
		this.gameId=gameId;
	}
	
	//Value depending of date, day, and players
	private String createGameId(){
		return "test" + TEST_GAME_ID;
	}
	
	
	//Peut etre remonter l'exception? 
	public void prepareNewGame(){
		 ArrayList<String> playerNames = MainAgentUtils.accessPlayerNames(this.gamePlayers);
		 SheetContainer sc = new SheetContainer(playerNames, sheetKind);
		 try
		 {
			 sc.init();
			 Sheet newlyCreatedSheet = sc.createNewSheet(gameId);
			 newlyCreatedSheet.prepareNewSheet();
			 
		 } catch(IOException e) {
			 System.out.println("Error found IO" + e);
			 
		 }
	}
}
