package com.bigbeard.yatzystats.core.model.sheets;

import com.bigbeard.yatzystats.core.model.players.ConfrontationDTO;
import com.bigbeard.yatzystats.core.model.players.PlayerResult;

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

    public ConfrontationDTO findConfrontation(String playerName1, String playerName2){
        PlayerResult player1Result = null;
        PlayerResult player2Result = null;
        for(PlayerResult playerResult : playerList){
            if(playerResult.playerName().equals(playerName1)){
                player1Result = playerResult;
            } else if(playerResult.playerName().equals(playerName2)){
                player2Result = playerResult;
            } else {
                if(player1Result != null && player2Result != null) break;
            }
        }
        return (player1Result != null && player2Result != null) ? new ConfrontationDTO(player1Result, player2Result) : null;
    }

    @Override
    public String toString(){
        StringBuilder sheetContent = new StringBuilder("Players :" + System.lineSeparator());
        for(PlayerResult p : playerList){
            sheetContent.append(p.playerName()).append(" : ").append(p.score()).append(System.lineSeparator());
        }

        return sheetContent.toString();
    }
}
