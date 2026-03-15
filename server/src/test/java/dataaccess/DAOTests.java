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
        } catch(DataAccessException dataAccessException){

        }
    }
    @Test
    public void createUserSuccess() throws DataAccessException {
        UserData userData = new UserData("ben", "12345", "ben email");
        dataAccess.createUser(userData);
    }

    @Test
    public void createUserFailure() throws DataAccessException {
        try{
            UserData userData = new UserData("ben", "12345", "ben email");
            dataAccess.createUser(userData);
            UserData userDataSame = new UserData("ben", "12345", "ben email");
            dataAccess.createUser(userDataSame);
        } catch(DataAccessException dataAccessException){

        }

    }
    @Test
    public void createAuthSuccess() throws DataAccessException {
        AuthData authData = new AuthData("token", "ben");
        dataAccess.createAuth(authData);
    }
    @Test
    public void createAuthFailure() throws DataAccessException {
        try{
            AuthData authData = new AuthData("token", "ben");
            dataAccess.createAuth(authData);
            AuthData authDataSame = new AuthData("token", "ben");
            dataAccess.createAuth(authDataSame);
        } catch(DataAccessException dataAccessException){

        }

    }
    @Test
    public void deleteAuthSuccess() throws DataAccessException {
        AuthData authData = new AuthData("token", "ben");
        dataAccess.createAuth(authData);
        dataAccess.deleteAuth(authData);

    }

    @Test
    public void deleteAuthFailure() throws DataAccessException {
        try {
            AuthData authData = new AuthData("token", "ben");
            dataAccess.createAuth(authData);
            AuthData authSecond = new AuthData("tok", "david");
            dataAccess.deleteAuth(authSecond);
        }
        catch(DataAccessException dataAccessException){

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
        } catch(DataAccessException dataAccessException){

        }
    }

    @Test
    public void listGamesSuccess() throws DataAccessException {
        UserData userData = new UserData("ben", "12345", "benemail");
        dataAccess.createUser(userData);
        ChessGame game = new ChessGame();
        GameData gameData = new GameData(12, userData.username(), "john", "fungame", game);
        List<GameData> gamelist = dataAccess.listGames();
        Assertions.assertNotNull(gamelist);
    }
    @Test
    public void listGamesFailure() throws DataAccessException {
        try{
            List<GameData> games = dataAccess.listGames();
            Assertions.assertNotNull(games);
        }
        catch(DataAccessException dataAccessException){

        }
    }
    @Test
    public void createGameSuccess() throws DataAccessException {
        ChessGame game = new ChessGame();
        GameData gameData = new GameData(12, "ben", "david", "game", game);
        dataAccess.createGame(gameData);
    }

    @Test
    public void createGameFailure() throws DataAccessException {
        try{
            ChessGame game = new ChessGame();
            GameData gameData = new GameData(12, "ben", "david", "game", game);
            dataAccess.createGame(gameData);
            GameData gameDataSame = new GameData(12, "ben", "david", "game", game);
            dataAccess.createGame(gameDataSame);
        } catch(DataAccessException dataAccessException){

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
        } catch(DataAccessException dataAccessException){

        }
    }

    @Test
    public void updateGameSuccess() throws DataAccessException {
        ChessGame game = new ChessGame();
        GameData gameData = new GameData(12, "ben", "david", "game", game);
        dataAccess.createGame(gameData);
        GameData gameDataTwo = new GameData(12, "ben", "John", "game", game);
        dataAccess.updateGame(gameDataTwo);
    }

    @Test
    public void updateGameFailure() throws DataAccessException {
        try{
            ChessGame game = new ChessGame();
            GameData gameData = new GameData(12, "ben", "david", "game", game);
            dataAccess.updateGame(gameData);
            Assertions.fail("exception not thrown");
        }
        catch (DataAccessException dataAccessException){

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
