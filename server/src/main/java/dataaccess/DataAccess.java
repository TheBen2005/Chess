package dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;

public interface DataAccess {

    public UserData getUser(String username);

    public UserData createUser(UserData userData);

    public void createAuth(AuthData authData);

    public void deleteAuth(String authToken);

    public void listGames();

    public void createGame(GameData gameData);

    public void getGame(int gameId);

    public void updateGame(GameData gameData);

    public void clearUsers() throws DataAccessException;

    public void clearGames() throws DataAccessException;

    public void clearAuth() throws DataAccessException;
}
