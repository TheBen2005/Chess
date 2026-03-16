package dataaccess;

import chess.ChessGame;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.MySqlDataAccess;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;
import service.GameService;
import service.UserService;

import java.util.List;

public class DAOTests {
    DataAccess dataAccess = new MySqlDataAccess();


    public DAOTests() throws DataAccessException {
    }

    @BeforeEach
    public void setup() throws DataAccessException {
        dataAccess.clearUsers();
        dataAccess.clearAuth();
        dataAccess.clearGames();
    }

    @Test
    public void getUserSuccess() throws DataAccessException {
        UserData userData = new UserData("ben", "12345", "ben email");
        dataAccess.createUser(userData);
        String username = userData.username();
        UserData user = dataAccess.getUser(username);
        String usernameSecond = user.username();
        Assertions.assertEquals("ben", usernameSecond);
    }
    @Test
    public void getUserFailure() throws DataAccessException {
        try {
            UserData userData = new UserData("ben", "12345", "ben email");
            dataAccess.createUser(userData);
            String username = "david";
            UserData user = dataAccess.getUser(username);
            Assertions.assertNull(user);
        } catch(DataAccessException dataAccessException){
            Assertions.assertNotNull(dataAccessException.getMessage());

        }
    }
    @Test
    public void createUserSuccess() throws DataAccessException {
        UserData userData = new UserData("ben", "12345", "ben email");
        dataAccess.createUser(userData);
        UserData user = dataAccess.getUser("ben");
        Assertions.assertNotNull(user);
    }

    @Test
    public void createUserFailure() throws DataAccessException {
        try{
            UserData userData = new UserData("ben", "12345", "ben email");
            dataAccess.createUser(userData);
            UserData userDataSame = new UserData("ben", "12345", "ben email");
            dataAccess.createUser(userDataSame);
            Assertions.fail("should have thrown an exception");
        } catch(DataAccessException dataAccessException){
            Assertions.assertNotNull(dataAccessException.getMessage());

        }

    }
    @Test
    public void createAuthSuccess() throws DataAccessException {
        AuthData authData = new AuthData("token", "ben");
        dataAccess.createAuth(authData);
        AuthData auth = dataAccess.getAuth("token");
        Assertions.assertNotNull(auth);
    }
    @Test
    public void createAuthFailure() throws DataAccessException {
        try{
            AuthData authData = new AuthData("token", "ben");
            dataAccess.createAuth(authData);
            AuthData authDataSame = new AuthData("token", "ben");
            dataAccess.createAuth(authDataSame);
            Assertions.fail("should have thrown an exception");
        } catch(DataAccessException dataAccessException){
            Assertions.assertNotNull(dataAccessException.getMessage());

        }

    }
    @Test
    public void deleteAuthSuccess() throws DataAccessException {
        AuthData authData = new AuthData("token", "ben");
        dataAccess.createAuth(authData);
        dataAccess.deleteAuth(authData);
        AuthData auth = dataAccess.getAuth("token");
        Assertions.assertNull(auth);

    }

    @Test
    public void deleteAuthFailure() throws DataAccessException {
        try {
            AuthData authData = new AuthData("token", "ben");
            dataAccess.createAuth(authData);
            AuthData authSecond = new AuthData("tok", "david");
            dataAccess.deleteAuth(authSecond);
            AuthData authDataOriginal = dataAccess.getAuth("token");
            Assertions.assertNotNull(authDataOriginal);
        }
        catch(DataAccessException dataAccessException){
            Assertions.assertNotNull(dataAccessException.getMessage());

        }

    }

    @Test
    public void getAuthSuccess() throws DataAccessException {
        AuthData authData = new AuthData("token", "ben");
        dataAccess.createAuth(authData);
        String authToken = authData.authToken();
        AuthData user = dataAccess.getAuth(authToken);
        String authTokenTwo = user.authToken();
        Assertions.assertEquals("token", authTokenTwo);
    }
    @Test
    public void getAuthFailure() throws DataAccessException {
        try {
            AuthData authData = new AuthData("token", "ben");
            dataAccess.createAuth(authData);
            String authToken = "david";
            AuthData user = dataAccess.getAuth(authToken);
            Assertions.assertNull(user);
        } catch(DataAccessException dataAccessException){
            Assertions.assertNotNull(dataAccessException.getMessage());

        }
    }

    @Test
    public void listGamesSuccess() throws DataAccessException {
        UserData userData = new UserData("ben", "12345", "benemail");
        dataAccess.createUser(userData);
        ChessGame game = new ChessGame();
        GameData gameData = new GameData(12, userData.username(), "john", "fungame", game);
        dataAccess.createGame(gameData);
        List<GameData> gamelist = dataAccess.listGames();
        Assertions.assertEquals(1, gamelist.size());
    }
    @Test
    public void listGamesFailure() throws DataAccessException {
        try{
            List<GameData> games = dataAccess.listGames();
            Assertions.assertEquals(0, games.size());
        }
        catch(DataAccessException dataAccessException){
            Assertions.assertNotNull(dataAccessException.getMessage());

        }
    }
    @Test
    public void createGameSuccess() throws DataAccessException {
        ChessGame game = new ChessGame();
        GameData gameData = new GameData(12, "ben", "david", "game", game);
        dataAccess.createGame(gameData);
        int gameIdTwo = 12;
        GameData gameDataTwo = dataAccess.getGame(gameIdTwo);
        Assertions.assertNotNull(gameDataTwo);
    }

    @Test
    public void createGameFailure() throws DataAccessException {
        try{
            ChessGame game = new ChessGame();
            GameData gameData = new GameData(12, "ben", "david", "game", game);
            dataAccess.createGame(gameData);
            GameData gameDataSame = new GameData(12, "ben", "david", "game", game);
            dataAccess.createGame(gameDataSame);
            Assertions.fail("exception should have been thrown");
        } catch(DataAccessException dataAccessException){
            Assertions.assertNotNull(dataAccessException.getMessage());

        }

    }


    @Test
    public void getGameSuccess() throws DataAccessException {
        ChessGame game = new ChessGame();
        GameData gameData = new GameData(12, "ben", "david", "game", game);
        dataAccess.createGame(gameData);
        int gameId = gameData.gameID();
        GameData gameTwo = dataAccess.getGame(gameId);
        int gameIdOne = gameData.gameID();
        int gameIdTwo = gameTwo.gameID();
        Assertions.assertEquals(gameIdOne, gameIdTwo);
    }
    @Test
    public void getGameFailure() throws DataAccessException {
        try {
            ChessGame game = new ChessGame();
            GameData gameData = new GameData(12, "ben", "david", "game", game);
            dataAccess.createGame(gameData);
            int gameId = 14;
            GameData gameTwo = dataAccess.getGame(gameId);
            Assertions.assertNull(gameTwo);
        } catch(DataAccessException dataAccessException){
            Assertions.assertNotNull(dataAccessException.getMessage());

        }
    }

    @Test
    public void updateGameSuccess() throws DataAccessException {
        ChessGame game = new ChessGame();
        GameData gameData = new GameData(12, "ben", "david", "game", game);
        dataAccess.createGame(gameData);
        GameData gameDataTwo = new GameData(12, "ben", "John", "game", game);
        dataAccess.updateGame(gameDataTwo);
        int gameId = 12;
        GameData gameDataThree = dataAccess.getGame(gameId);
        String blackUsername = gameDataThree.blackUsername();
        Assertions.assertEquals("John", blackUsername);
    }

    @Test
    public void updateGameFailure() throws DataAccessException {
        try{
            ChessGame game = new ChessGame();
            GameData gameData = new GameData(12, "ben", "david", "game", game);
            dataAccess.updateGame(gameData);
            int gameId = 12;
            GameData gameDataTwo = dataAccess.getGame(gameId);
            Assertions.assertNull(gameDataTwo);
        }
        catch (DataAccessException dataAccessException){
            Assertions.assertNotNull(dataAccessException.getMessage());

        }

    }

    @Test
    public void clearUsersSuccess() throws DataAccessException {
        dataAccess.clearUsers();
    }

    @Test
    public void clearGamesSuccess() throws DataAccessException {
        dataAccess.clearGames();
    }

    @Test
    public void clearAuthSuccess() throws DataAccessException {
        dataAccess.clearAuth();
    }







}
