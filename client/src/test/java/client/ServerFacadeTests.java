package client;

import dataaccess.DataAccessException;
import model.GameData;
import org.junit.jupiter.api.*;
import server.Server;
import service.*;
import recordClasses.CreateGameRequest;
import recordClasses.LoginRequest;
import recordClasses.RegisterRequest;
import recordClasses.RegisterResult;
import recordClasses.ListGamesRequest;
import recordClasses.JoinGameRequest;
import recordClasses.LoginResult;
import recordClasses.LogoutRequest;
import recordClasses.ListGamesResult;

import java.util.List;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:" + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeEach
    public void setup() throws Exception {
        facade.clear();
    }


    @Test
    void loginSuccess() throws Exception{
        RegisterRequest registerRequest = new RegisterRequest("Theben", "12345", "password");
        facade.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest("Theben", "12345");
        var authData = facade.login(loginRequest);
        Assertions.assertNotNull(authData.authToken());
    }

    @Test
    void loginfailure() throws Exception{
        try{
            RegisterRequest registerRequest = new RegisterRequest("Theben", "12345", "password");
            facade.register(registerRequest);
            LoginRequest loginRequest = new LoginRequest("Theb", "1235");
            facade.login(loginRequest);
            Assertions.fail("should have thrown an exception");
        }
        catch(DataAccessException dataAccessException){
            Assertions.assertNotNull(dataAccessException.getMessage());
        }


    }

    @Test
    void registerSuccess() throws Exception{
        RegisterRequest registerRequest = new RegisterRequest("Theben", "12345", "password");
        var authData = facade.register(registerRequest);
        Assertions.assertNotNull(authData.authToken());

    }

    @Test
    void registerfailure() throws Exception{
        try{
            RegisterRequest registerRequest = new RegisterRequest("Theben", "12345", "password");
            facade.register(registerRequest);
            RegisterRequest registerRequestTwo = new RegisterRequest("Theben", "12345", "password");
            facade.register(registerRequestTwo);
            Assertions.fail("should have thrown an exception");
        }
        catch(DataAccessException dataAccessException){
            Assertions.assertNotNull(dataAccessException.getMessage());
        }

    }

    @Test
    void logoutSuccess() throws Exception{
        RegisterRequest registerRequest = new RegisterRequest("Theben", "12345", "password");
        facade.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest("Theben", "12345");
        var authData = facade.login(loginRequest);
        String authToken = authData.authToken();
        LogoutRequest logoutRequest = new LogoutRequest(authToken);
        facade.logout(logoutRequest);
        Assertions.assertNotNull(authToken);

    }

    @Test
    void logoutFailure() throws Exception{
        try{
            RegisterRequest registerRequest = new RegisterRequest("Theben", "12345", "password");
            facade.register(registerRequest);
            LoginRequest loginRequest = new LoginRequest("Theben", "12345");
            var authData = facade.login(loginRequest);
            LogoutRequest logoutRequest = new LogoutRequest("hgsbsbs");
            facade.logout(logoutRequest);
            Assertions.fail("should have thrown an exception");
        }
        catch(DataAccessException dataAccessException){
            Assertions.assertNotNull(dataAccessException.getMessage());
        }

    }

    @Test
    void createGameSuccess() throws Exception{
        RegisterRequest registerRequest = new RegisterRequest("Theben", "12345", "password");
        facade.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest("Theben", "12345");
        var authData = facade.login(loginRequest);
        String authToken = authData.authToken();
        CreateGameRequest createGameRequest = new CreateGameRequest("game", authToken);
        CreateGamesResult createGamesResult = facade.createGame(createGameRequest);
        int gameId = createGamesResult.gameID();
        Assertions.assertNotNull(gameId);

    }

    @Test
    void createGamefailure() throws Exception{
        try{
            RegisterRequest registerRequest = new RegisterRequest("Theben", "12345", "password");
            facade.register(registerRequest);
            LoginRequest loginRequest = new LoginRequest("Theben", "12345");
            var authData = facade.login(loginRequest);
            CreateGameRequest createGameRequest = new CreateGameRequest("game", "hsbsbsb");
            facade.createGame(createGameRequest);
            Assertions.fail("should have thrown an exception");
        }
        catch(DataAccessException dataAccessException){
            Assertions.assertNotNull(dataAccessException.getMessage());
        }

    }

    @Test
    void listGamesSuccess() throws Exception{
        RegisterRequest registerRequest = new RegisterRequest("Theben", "12345", "password");
        facade.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest("Theben", "12345");
        var authData = facade.login(loginRequest);
        String authToken = authData.authToken();
        ListGamesRequest listGamesRequest = new ListGamesRequest(authToken);
        ListGamesResult listGamesResult = facade.listGames(listGamesRequest);
        List<GameData> gameList = listGamesResult.games();
        Assertions.assertNotNull(gameList);


    }

    @Test
    void listGamesfailure() throws Exception{
        try{
            RegisterRequest registerRequest = new RegisterRequest("Theben", "12345", "password");
            facade.register(registerRequest);
            LoginRequest loginRequest = new LoginRequest("Theben", "12345");
            var authData = facade.login(loginRequest);
            String authToken = authData.authToken();
            ListGamesRequest listGamesRequest = new ListGamesRequest("jsjbsjbdjdb");
            ListGamesResult listGamesResult = facade.listGames(listGamesRequest);
            Assertions.fail("should have thrown an exception");
        }
        catch(DataAccessException Exception){
            Assertions.assertNotNull(Exception.getMessage());
        }

    }

    @Test
    void joinGameSuccess() throws Exception{
        RegisterRequest registerRequest = new RegisterRequest("Theben", "12345", "password");
        facade.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest("Theben", "12345");
        var authData = facade.login(loginRequest);
        String authToken = authData.authToken();
        CreateGameRequest createGameRequest = new CreateGameRequest("game", authToken);
        CreateGamesResult createGamesResult = facade.createGame(createGameRequest);
        int gameId = createGamesResult.gameID();
        JoinGameRequest joinGameRequest = new JoinGameRequest("WHITE", gameId, authToken);
        facade.joinGame(joinGameRequest);
        Assertions.assertNotNull(authToken);
    }

    @Test
    void joinGameFailure() throws Exception{
        try{
            RegisterRequest registerRequest = new RegisterRequest("Theben", "12345", "password");
            facade.register(registerRequest);
            LoginRequest loginRequest = new LoginRequest("Theben", "12345");
            var authData = facade.login(loginRequest);
            String authToken = authData.authToken();
            CreateGameRequest createGameRequest = new CreateGameRequest("game", authToken);
            CreateGamesResult createGamesResult = facade.createGame(createGameRequest);
            int gameId = createGamesResult.gameID();
            JoinGameRequest joinGameRequest = new JoinGameRequest("White", 6, "gdhdvd");
            facade.joinGame(joinGameRequest);
            Assertions.fail("should have thrown an exception");
        }
        catch(Exception exception){
            Assertions.assertNotNull(exception.getMessage());
        }

    }

    @Test
    void clear() throws Exception{
        RegisterRequest registerRequest = new RegisterRequest("Theben", "12345", "password");
        var authData = facade.register(registerRequest);
        facade.clear();
        try{
            LoginRequest loginRequest = new LoginRequest("Theben", "12345");
            facade.login(loginRequest);
            Assertions.fail("should have thrown an exception");
        }
        catch(Exception exception){
            Assertions.assertNotNull(exception.getMessage());
        }
    }



}
