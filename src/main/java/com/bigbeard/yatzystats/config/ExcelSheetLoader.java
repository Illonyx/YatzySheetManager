package com.bigbeard.yatzystats.config;

import com.bigbeard.yatzystats.core.sheets.ExcelSheetFacade;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelSheetLoader {

    private List<String> allSheetNames;
    private Workbook workbook;

    public ExcelSheetLoader(String filePath) throws IOException {
        this.allSheetNames = new ArrayList<>();
        File file = new File(filePath);
        this.workbook = WorkbookFactory.create(file);

        for(int i=0; i<this.workbook.getNumberOfSheets(); i++){
            Sheet sheet = this.workbook.getSheetAt(i);
            this.allSheetNames.add(sheet.getSheetName());
        }

    }

    public List<String> getAllSheetNames() {
        return allSheetNames;
    }

    public List<Sheet> getSelectedSheets(List<String> selectedSheets) {
        List<Sheet> sheets = new ArrayList<Sheet>();
        for(String selectedSheet : selectedSheets){
            try {
                sheets.add(this.workbook.getSheet(selectedSheet));
            } catch(NullPointerException ex){
                System.err.println("La feuille" + selectedSheet + "n'a pu être chargée");
            }
        }
        return sheets;
    }

    //FIXME : Where to do this?
    public Map<String, Integer> getPlayersByGameNumber(List<Sheet> sheets) {
        Map<String, Integer> playersByGameNumber = new HashMap<>();
        for(Sheet s : sheets){
            List<String> players = new ExcelSheetFacade().reachPlayersList(s);
            for(String player : players){
                if(playersByGameNumber.get(player) == null) playersByGameNumber.put(player, 1);
                else {
                    int nbGames = playersByGameNumber.get(player);
                    playersByGameNumber.put(player, ++nbGames);
                }
            }
        }
        return playersByGameNumber;
    }

}
