package yatzySheetManager.sheets;

public class SheetRecognizer {
	
	//Evalue date/Nom/SheetKind
	
	private org.apache.poi.ss.usermodel.Sheet sheet;
	public org.apache.poi.ss.usermodel.FormulaEvaluator evaluator;
	
	public SheetRecognizer(org.apache.poi.ss.usermodel.Sheet sheet, org.apache.poi.ss.usermodel.FormulaEvaluator evaluator){
		this.sheet=sheet;
		this.evaluator=evaluator;
	}
	
	public SheetKind determineSheetKind(){
		return SheetKind.SCANDINAVIAN_YATZEE;
	}
	
	
}
