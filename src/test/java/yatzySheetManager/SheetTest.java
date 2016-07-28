package yatzySheetManager;

import static org.junit.Assert.assertEquals;

import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.Test;

import yatzySheetManager.agent.CollectDataAgent;
import yatzySheetManager.agent.MainAgent;
import yatzySheetManager.agent.WriterAgent;
import yatzySheetManager.players.Player;
import yatzySheetManager.players.PlayerResults;
import yatzySheetManager.sheets.SheetKind;

public class SheetTest {
	
	private final String TEST_SIMPLE_PATH = "src/test/resources/preferedSheets";
	
	@Test
	public void testSheetGoodValue(){
		Player p = new Player("Alexis");
		MainAgent.getInstance().preferedSheets = Paths.get(TEST_SIMPLE_PATH);
		CollectDataAgent c = new CollectDataAgent(p);
		c.collect();
		PlayerResults pr = p.getResults().get(0);
		assertEquals(273.0, pr.score ,0.0);
		
	}
	
	@Test
	public void testSimplePath(){
		ArrayList<Player> players = MainAgent.getInstance().getRegisteredPlayers();
		WriterAgent agent = new WriterAgent(players, SheetKind.SCANDINAVIAN_YATZEE);
		
		
	}
	
	
}
