package dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.List;

public interface DataAccess {

    public UserData getUser(String username) throws DataAccessException;

    public UserData createUser(UserData userData) throws DataAccessException;

    public void createAuth(AuthData authData) throws DataAccessException;

    public void deleteAuth(String authToken);

    public void getAuth(String authtoken) throws DataAccessException;

    public List<GameData> listGames();

    public void createGame(GameData gameData);

    public void getGame(int gameId) throws DataAccessException;

    public void updateGame(GameData gameData);

    public void clearUsers() throws DataAccessException;

    public void clearGames() throws DataAccessException;

    public void clearAuth() throws DataAccessException;
}
