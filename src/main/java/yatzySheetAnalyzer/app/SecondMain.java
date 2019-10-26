package yatzySheetAnalyzer.app;

import java.nio.file.Paths;

import com.bigbeard.yatzystats.config.ExcelSheetLoader;
import com.bigbeard.yatzystats.config.GameRulesLoader;
import com.bigbeard.yatzystats.core.rules.GameRules;
import com.bigbeard.yatzystats.core.rules.SheetRulesIdentifiers;
import org.apache.poi.util.StringUtil;
import yatzySheetManager.agent.CollectDataAgent;
import yatzySheetManager.agent.MainAgent;
import yatzySheetManager.players.Player;

public class SecondMain {
	
	public static void main(String[] args){
		try {
			GameRulesLoader loader = new GameRulesLoader(SheetRulesIdentifiers.YATZY);
			GameRules rules = loader.getGameRules();
			System.out.println("scoree " + rules.getScoreRow());

			ExcelSheetLoader excelSheetLoader = new ExcelSheetLoader("src/test/resources/feuille_calcul_yatzee.xlsx");
			//System.out.println("da" + StringUtil.join(excelSheetLoader.getAllSheetNames().toArray(), ","));

		} catch (Exception ex) {
			System.err.println("Oops! " + ex.getMessage());
		}

		MainAgent.getInstance().preferedSheets = Paths.get("src/main/resources/games-data/preferedSheets");
		printPlayer("Alexis");
		printPlayer("Papa");
		printPlayer("Maman");
		printPlayer("Arnaud");
	}

	//TODO : Fichier inaccessible
	public static void printPlayer(String str){
		Player p = new Player(str);
		CollectDataAgent cda = new CollectDataAgent(p);
		cda.collect();
		p.printStats();
	}
	
	
	
	
}
