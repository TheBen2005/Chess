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



    public UserData getUser(String username){
        for(UserData user : userList){
            if(user.username() == username){
                throw new DataAccessException("already taken")
            }
        }
        return null;
    }

    public UserData createUser(UserData userData){

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

    public void clearUsers() throws DataAccessException{
        userList.clear();

    }

    public void clearGames() throws DataAccessException{
        gameList.clear();

    }

    public void clearAuth() throws DataAccessException{
        AuthData.clear();
    }


}
