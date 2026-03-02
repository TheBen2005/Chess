package service;

import chess.ChessGame;

import java.util.List;

public record ListGamesResult(List<ChessGame> game, String whiteusername, String blackusername, String gameName) {
}
