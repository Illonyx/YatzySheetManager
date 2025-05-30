package com.bigbeard.yatzystats.core.model.players;

public record PlayerResult(String playerName, String year, int score,
                           int aces, int twos, int threes, int fours, int fives, int sixes,
                           boolean hasYatzy, boolean hasBonus, boolean isWinner) {
    public PlayerResult withWinner(boolean isWinner) {
        return new PlayerResult(playerName, year, score, aces, twos, threes, fours, fives, sixes, hasYatzy, hasBonus, isWinner);
    }
}