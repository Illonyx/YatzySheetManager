package com.bigbeard.yatzystats.core.sheets;

import com.bigbeard.yatzystats.core.players.PlayerResult;
import yatzySheetManager.players.Player;

import java.util.List;

public class SheetDto {

    private String sheetName;
    private List<PlayerResult> playerList;
    private Integer bestScore;

    public SheetDto(String sheetName, List<PlayerResult> playerList, int bestScore){
        this.sheetName = sheetName;
        this.playerList = playerList;
        this.bestScore = bestScore;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<PlayerResult> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<PlayerResult> playerList) {
        this.playerList = playerList;
    }

    public Integer getBestScore() {
        return bestScore;
    }

    public void setBestScore(Integer bestScore) {
        this.bestScore = bestScore;
    }

    @Override
    public String toString(){
        String sheetContent = "Players :" + System.lineSeparator();
        for(PlayerResult p : playerList){
            sheetContent += p.getPlayerName() + " : " + p.getScore() + System.lineSeparator();
        }

        return sheetContent;
    }
}
