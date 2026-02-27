package service;

import chess.ChessGame;

public record ListGamesResult(list<ChessGame> game, String whiteusername, String blackusername, String gameName) {
}
