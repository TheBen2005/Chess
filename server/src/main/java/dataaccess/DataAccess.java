package dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.List;

public interface DataAccess {

    public UserData getUser(String username) throws DataAccessException;

    public void createUser(UserData userData) throws DataAccessException;

    public void createAuth(AuthData authData) throws DataAccessException;

    public void deleteAuth(AuthData authData) throws DataAccessException;

    public AuthData getAuth(String authToken) throws DataAccessException;

    public List<GameData> listGames() throws DataAccessException;

    public void createGame(GameData gameData) throws DataAccessException;

    public GameData getGame(int gameId) throws DataAccessException;

    public void updateGame(GameData gameData) throws DataAccessException;

    public void clearUsers() throws DataAccessException;

    public void clearGames() throws DataAccessException;

    public void clearAuth() throws DataAccessException;
}
