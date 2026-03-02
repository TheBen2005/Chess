package service;

import dataaccess.DataAccess;
import dataaccess.MemoryDataAccess;
import model.AuthData;
import model.GameData;

import java.util.List;

public class GameService {


    public ListGamesResult listgames(ListGamesRequest listGamesRequest){
        DataAccess dataAccess = new MemoryDataAccess();
        AuthData authData = dataAccess.getAuth(listGamesRequest.authtoken);
        List<GameData> = dataAccess.listGames();
        ListGamesResult listGamesResult = new ListGamesResult(List<GameData>);


    }

    public CreateGamesResult creategame(CreateGameRequest createGamesRequest){
        DataAccess dataAccess = new MemoryDataAccess();
        AuthData authData = dataAccess.getAuth(createGamesRequest.authtoken);
        dataAccess.createGame(GameData);
        int gameid = GameData.gameid;
        CreateGamesResult createGamesResult = new CreateGamesResult(gameid);
        return createGamesResult;



    }

    public void joingame(JoinGameRequest joinGameRequest){
        DataAccess dataAccess = new MemoryDataAccess();
        AuthData authData = dataAccess.getAuth(listGamesRequest.authtoken);
        GameData gameData = dataAccess.getGame(joinGameRequest.gameId);
        dataAccess.updateGame(gameData);


    }

}
