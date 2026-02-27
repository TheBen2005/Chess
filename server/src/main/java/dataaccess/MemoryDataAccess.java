package dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.ArrayList;
import java.util.List;

public class MemoryDataAccess implements DataAccess{
    List<UserData> userList = new ArrayList<>();
    List<GameData> gameList = new ArrayList<>();
    List<AuthData> AuthData = new ArrayList<>();



    public void getUser(String username){

    }

    public void createUser(UserData userData){

    }

    public void createAuth(AuthData authData){

    }

    public void deleteAuth(String authToken){

    }

    public void listGames(){

    }

    public void createGame(GameData gameData){

    }

    public void getGame(int gameId){

    }

    public void updateGame(GameData gameData){

    }

    public void clearUsers(){

    }

    public void clearGames(){

    }

    public void clearAuth(){

    }


}
