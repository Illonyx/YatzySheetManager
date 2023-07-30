package com.bigbeard.yatzystats.core.model.players;

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

    public PlayerResult getPlayerResult1() {
        return playerResult1;
    }

    public PlayerResult getPlayerResult2() {
        return playerResult2;
    }

    public String getConfrontationScore(String firstPlayer) {
        return playerResult1.getPlayerName().equals(firstPlayer) ? playerResult1.getScore() + "-" + playerResult2.getScore() :
                playerResult2.getScore() + "-" + playerResult1.getScore();
    }
}