package service;

import chess.ChessGame;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.MemoryDataAccess;
import model.AuthData;
import model.GameData;

import java.util.List;
import java.util.Random;

public class GameService {


    public ListGamesResult listgames(ListGamesRequest listGamesRequest) throws DataAccessException {
        DataAccess dataAccess = new MemoryDataAccess();
        AuthData authData = dataAccess.getAuth(listGamesRequest.authtoken());
        List<GameData> games = dataAccess.listGames();
        ListGamesResult listGamesResult = new ListGamesResult(games);
        return listGamesResult;


    }

    public CreateGamesResult creategame(CreateGameRequest createGamesRequest) throws DataAccessException {
        DataAccess dataAccess = new MemoryDataAccess();
        AuthData authData = dataAccess.getAuth(createGamesRequest.authtoken());
        Random randomNum = new Random();
        int gameId = Math.abs(randomNum.nextInt() % 10000);
        ChessGame game = new ChessGame();
        GameData gameData = new GameData(gameId,"", "", "", game);
        dataAccess.createGame(gameData);
        CreateGamesResult createGamesResult = new CreateGamesResult(gameId);
        return createGamesResult;



    }

    public void joingame(JoinGameRequest joinGameRequest) throws DataAccessException {
        DataAccess dataAccess = new MemoryDataAccess();
        AuthData authData = dataAccess.getAuth(joinGameRequest.authtoken());
        GameData gameData = dataAccess.getGame(joinGameRequest.gameId());
        if((gameData.whiteUsername() != null && joinGameRequest.playerColor() == "WHITE") || (gameData.blackUsername() != null && joinGameRequest.playerColor() == "BLACK")){
            throw new DataAccessException("already taken");
        }
        String username = authData.username();
        if(joinGameRequest.playerColor() == "WHITE"){
            GameData newData = new GameData(gameData.gameID(), authData.username(), gameData.blackUsername(), gameData.gameName(), gameData.game());
        }
        dataAccess.updateGame(gameData);


    }

}
