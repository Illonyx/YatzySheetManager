package yatzySheetManager.agent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import yatzySheetManager.players.Player;
import yatzySheetManager.sheets.SheetKind;

//Contiendra les différentes données afférentes à l'application - Classe Singleton

public class MainAgent {
	
	private ArrayList<Player> registeredPlayers;
	private SheetKind kind;
	public final File file = new File("src/test/resources/feuille_calcul_yatzee.xlsx");
	public Path preferedSheets = Paths.get("src/test/resources/testPrefered.txt");
	
	private MainAgent() {
		this.registeredPlayers = initDefaultPlayers();
		this.kind = initDefaultKind();
	}

	private static class MainAgentHolder {
		/** Instance unique non préinitialisée */
		private final static MainAgent instance = new MainAgent();
	}

	/** Point d'accès pour l'instance unique du singleton */
	public static MainAgent getInstance() {
		return MainAgentHolder.instance;
	}
	
	//Default Construction
	
	private ArrayList<Player> initDefaultPlayers(){
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player("Arnaud"));
		players.add(new Player("Maman"));
		players.add(new Player("Alexis"));
		players.add(new Player("Papa"));
		return players;
	}
	
	private SheetKind initDefaultKind(){
		return SheetKind.SCANDINAVIAN_YATZEE;
	}
	
	public ArrayList<String> initGamesToConsider(){
		
		try {
			return (ArrayList<String>) Files.readAllLines(preferedSheets);
		} catch (IOException e) {
			System.err.println("Je me suis planté");
			e.printStackTrace();
			return null;
		}
	}
	
	//Gettlers - settlers
	
	public ArrayList<Player> getRegisteredPlayers() {
		return registeredPlayers;
	}

	public void setRegisteredPlayers(ArrayList<Player> registeredPlayers) {
		this.registeredPlayers = registeredPlayers;
	}

	public SheetKind getKind() {
		return kind;
	}

	public void setKind(SheetKind kind) {
		this.kind = kind;
	}
	
}
