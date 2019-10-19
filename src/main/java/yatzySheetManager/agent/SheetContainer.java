package yatzySheetManager.agent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import yatzySheetManager.sheets.Sheet;
import yatzySheetManager.sheets.SheetKind;

public class SheetContainer {
	
	private ArrayList<String> targetNames;
	private Workbook workbook;
	private FormulaEvaluator evaluator;
	private SheetKind kind;
	
	public SheetContainer(ArrayList<String> targetNames, SheetKind kind){
		this.targetNames = targetNames;
		this.kind = kind;
	}

	//On vérifie bien qu'on arrive bien à récupérer la feuille
	public void init() throws IOException {
		File f = MainAgent.getInstance().file;
		this.workbook = WorkbookFactory.create(f);
		this.evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		
	}
	
	public ArrayList<Sheet> initSheetList(ArrayList<String> sheetNames){
		ArrayList<Sheet> filteredSheets = new ArrayList<Sheet>();
		
		for(int i=0;i<sheetNames.size();i++){
			try {
				org.apache.poi.ss.usermodel.Sheet sheet = this.workbook.getSheet(sheetNames.get(i));
				Sheet targetSheet = new Sheet(sheet,this.evaluator);
				if(isSheetUseful(targetSheet)) filteredSheets.add(targetSheet);
			} catch(NullPointerException e){
				System.err.println(sheetNames.get(i) + " n'a pas été trouvée");
			}
			
		}
		
		return filteredSheets;
	}
	
	// ------------------------------------------------------------------------
	// CollectDataAgent Part
	// ------------------------------------------------------------------------
	
	//A tester et remettre ailleurs
	public Boolean isSheetUseful(Sheet targetSheet){
		Boolean isTheSameKind=false; 
		if(this.kind.equals(targetSheet.getSheetKind())){
			isTheSameKind=true;
			//System.out.println("Bien du même kind");
		}
		
		//Décision arbitraire 
		ArrayList<String> inGame = targetSheet.getPlayersList();
		Boolean isEveryTargetPlaying = inGame.containsAll(this.targetNames);
		//System.out.println(isEveryTargetPlaying);
		
		
		return isTheSameKind && isEveryTargetPlaying;
	}
	
	//----------------------------------------------------------------------------------------
	// WriterAgent Part
	//----------------------------------------------------------------------------------------
	
	public Sheet createNewSheet(String gameId) {
		org.apache.poi.ss.usermodel.Sheet sheet = this.workbook.createSheet(gameId);
		return null;
	}
	
	
	
	
}
