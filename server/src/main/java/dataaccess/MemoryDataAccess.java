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
            if(user.username().equals(username)){
                return user;
            }
        }
        return null;
    }

    public void createUser(UserData userData) throws DataAccessException{
        userList.add(userData);

    }

    public void createAuth(AuthData authData) throws DataAccessException{
        authList.add(authData);
    }

    public void deleteAuth(AuthData authData){
        authList.remove(authData);

    }

    public AuthData getAuth(String authToken) throws DataAccessException{
        for(AuthData auth : authList){
            String token = auth.authToken();
            if(token.equals(authToken)){
                return auth;
            }
        }
        return null;
    }

    public List<GameData> listGames(){
        return gameList;

    }

    public void createGame(GameData gameData){
        gameList.add(gameData);

    }

    public GameData getGame(int gameId) throws DataAccessException{
        for(GameData game : gameList){
            if(game.gameID() == gameId){
                return game;
            }
        }
        throw new DataAccessException("bad request");

    }

    public void updateGame(GameData gameData) throws DataAccessException{
        int position = 0;
        for(GameData game: gameList){
            if(game.gameID() == gameData.gameID()){
                gameList.set(position, gameData);
                return;
            }
            position += 1;
        }
        throw new DataAccessException("bad request");


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
