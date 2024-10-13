package com.bigbeard.yatzystats.core.model.sheets;

import com.bigbeard.yatzystats.core.model.players.ConfrontationDTO;
import com.bigbeard.yatzystats.core.model.players.PlayerResult;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public record SheetDto(String sheetName, List<PlayerResult> playerList, int bestScore) {

    public SheetDto {
        // Defensive copy of playerList to ensure immutability
        playerList = List.copyOf(playerList);
    }

    /**
     * Finds the confrontation between two players, if both exist in the list.
     * Returns an Optional of ConfrontationDTO.
     */
    public Optional<ConfrontationDTO> findConfrontation(String playerName1, String playerName2) {
        var player1Result = playerList.stream()
                .filter(p -> p.playerName().equals(playerName1))
                .findFirst();
        var player2Result = playerList.stream()
                .filter(p -> p.playerName().equals(playerName2))
                .findFirst();

        return player1Result.isPresent() && player2Result.isPresent()
                ? Optional.of(new ConfrontationDTO(player1Result.get(), player2Result.get()))
                : Optional.empty();
    }

    /**
     * Builds a string representing all players and their scores.
     */
    @Override
    public String toString() {
        return playerList.stream()
                .map(p -> p.playerName() + " : " + p.score())
                .collect(Collectors.joining(System.lineSeparator(), "Players:" + System.lineSeparator(), ""));
    }
}
