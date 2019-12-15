package com.bigbeard.yatzystats.core.players;

public class ConfrontationDTO {
    private PlayerResult playerResult1;
    private PlayerResult playerResult2;

    public ConfrontationDTO(PlayerResult playerResult1, PlayerResult playerResult2) {
        this.playerResult1 = playerResult1;
        this.playerResult2 = playerResult2;
    }

    public String getWinnerName(){
        return (playerResult1.getScore() == playerResult2.getScore())
                ? "" : (playerResult1.getScore() > playerResult2.getScore()) ?
        playerResult1.getPlayerName() : playerResult2.getPlayerName();
    }

    public PlayerResult getPlayerResultWithName(String playerName){
        return (playerResult1.getPlayerName().equals(playerName)) ? playerResult1 :
                (playerResult2.getPlayerName().equals(playerName)) ? playerResult2 : null;
    }
}