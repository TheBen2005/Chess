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

    }

    public void joingame(JoinGameRequest joinGameRequest){

    }

}
