package dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.ArrayList;
import java.util.List;

public class MemoryDataAccess implements DataAccess{
    List<UserData> userList = new ArrayList<>();
    List<GameData> gameList = new ArrayList<>();
    List<AuthData> authList = new ArrayList<>();



    public UserData getUser(String username) throws DataAccessException{
        for(UserData user : userList){
            if(user.username() == username){
                return user;
            }
        }
        throw new DataAccessException("already taken");
    }

    public UserData createUser(UserData userData) throws DataAccessException{
        userList.add(userData);

    }

    public void createAuth(AuthData authData) throws DataAccessException{
        authList.add(authData);
    }

    public void deleteAuth(String authToken){
        authList.remove(authToken);

    }

    public void getAuth(String authToken) throws DataAccessException{
        for(AuthData auth : authList){
            if(auth.equals(authToken)){
                return auth;
            }
        }
        throw new DataAccessException("unauthorized");
    }

    public List<GameData> listGames(){
        return gameList;

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
        authList.clear();
    }


}
