package com.bigbeard.yatzystats.core.model.players;

public record ConfrontationDTO(PlayerResult playerResult1, PlayerResult playerResult2) {

    /**
     * Returns the name of the winner or an empty string if it's a draw.
     */
    public String getWinnerName() {
        if (playerResult1.score() == playerResult2.score()) {
            return "";  // It's a draw
        }
        return playerResult1.score() > playerResult2.score()
                ? playerResult1.playerName()
                : playerResult2.playerName();
    }

    /**
     * Returns the score in the format "{firstPlayerScore}-{secondPlayerScore}".
     * Assumes the order depends on the provided player name.
     */
    public String getConfrontationScore(String firstPlayer) {
        PlayerResult first = playerResult1.playerName().equals(firstPlayer) ? playerResult1 : playerResult2;
        PlayerResult second = first == playerResult1 ? playerResult2 : playerResult1;
        return first.score() + "-" + second.score();
    }
}