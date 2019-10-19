package yatzySheetManager.agent;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import yatzySheetManager.criterias.Criteria;
import yatzySheetManager.criterias.CriteriaFiller;
import yatzySheetManager.players.Player;
import yatzySheetManager.players.PlayerResults;
import yatzySheetManager.sheets.Sheet;
import yatzySheetManager.sheets.SheetKind;

//Agent qui va collecter des données sur les parties des joueurs
public class CollectDataAgent {
	
	private Boolean isSoloRequest;
	
	private Player target;
	//private ArrayList<Player> targets;
	
	//Deux modes de prise d'informations
	public CollectDataAgent(Player aTarget){
		this.isSoloRequest=true;
		this.target=aTarget;
	}
	
	private ArrayList<Criteria> initSheetCriterias(Sheet s){
		CriteriaFiller cf = new CriteriaFiller(s);
		return cf.generate();
	}
	
	//Collection Methods
	
	public void collect(){
		
		//Données à initialiser
		ArrayList<String> targetNames = new ArrayList<String>();
		String targetName = this.target.getName();
		targetNames.add(targetName);
		
		ArrayList<String> sheetsToImport = MainAgent.getInstance().initGamesToConsider();
		SheetKind kind = MainAgent.getInstance().getKind();
		
		SheetContainer sc = new SheetContainer(targetNames, kind);
		
		try 
		{
			sc.init();
			ArrayList<Sheet> sheetsToBrowse = sc.initSheetList(sheetsToImport);
			
			for(Sheet s : sheetsToBrowse)
			{
				//Pour tous les joueurs
				ArrayList<Criteria> criterias = initSheetCriterias(s);
				
				PlayerResults results = new PlayerResults(targetName, criterias); 
				results.evaluate();
				this.target.updateResults(results);
			}
			
		} catch (IOException e) {
			System.out.println("Ici la");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
