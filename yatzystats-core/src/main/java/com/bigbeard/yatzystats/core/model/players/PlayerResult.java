package com.bigbeard.yatzystats.core.model.players;

public record PlayerResult(String playerName, int score, boolean hasYatzy, boolean hasBonus, boolean isWinner) {
    public PlayerResult withWinner(boolean isWinner) {
        return new PlayerResult(playerName, score, hasYatzy, hasBonus, isWinner);
    }
}