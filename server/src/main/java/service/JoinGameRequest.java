package service;

public record JoinGameRequest(String playerColor, int gameId, String authtoken) {
}
