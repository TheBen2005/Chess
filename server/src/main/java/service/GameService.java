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

    private DataAccess dataAccess;

    public GameService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }


    public ListGamesResult listgames(ListGamesRequest listGamesRequest) throws DataAccessException {
        AuthData authData = dataAccess.getAuth(listGamesRequest.authToken());
        if(authData == null){
            throw new DataAccessException("unauthorized");
        }
        List<GameData> games = dataAccess.listGames();
        ListGamesResult listGamesResult = new ListGamesResult(games);
        return listGamesResult;


    }

    public CreateGamesResult creategame(CreateGameRequest createGamesRequest) throws DataAccessException {
        AuthData authData = dataAccess.getAuth(createGamesRequest.authToken());
        if(authData == null){
            throw new DataAccessException("unauthorized");
        }
        Random randomNum = new Random();
        int gameId = Math.abs(randomNum.nextInt() % 10000);
        ChessGame game = new ChessGame();
        GameData gameData = new GameData(gameId,null, null, createGamesRequest.gameName(), game);
        dataAccess.createGame(gameData);
        CreateGamesResult createGamesResult = new CreateGamesResult(gameId);
        return createGamesResult;



    }

    public void joingame(JoinGameRequest joinGameRequest) throws DataAccessException {
        AuthData authData = dataAccess.getAuth(joinGameRequest.authToken());
        if(authData == null){
            throw new DataAccessException("unauthorized");
        }
        GameData gameData = dataAccess.getGame(joinGameRequest.gameId());
        if (gameData == null) {
            throw new DataAccessException("bad request");
        }
        if((gameData.whiteUsername() != null && joinGameRequest.playerColor().equals("WHITE")) || (gameData.blackUsername() != null && joinGameRequest.playerColor().equals("BLACK"))){
            throw new DataAccessException("already taken");
        }
        String username = authData.username();
        GameData newData;
        if(joinGameRequest.playerColor().equals("WHITE")){
            newData = new GameData(gameData.gameID(), authData.username(), gameData.blackUsername(), gameData.gameName(), gameData.game());
        }
        else if(joinGameRequest.playerColor().equals("BLACK")){
            newData = new GameData(gameData.gameID(), gameData.whiteUsername(), authData.username(), gameData.gameName(), gameData.game());
        }
        else{
            throw new DataAccessException("bad request");

        }
        dataAccess.updateGame(newData);


    }

}
