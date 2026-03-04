package service;

import dataaccess.DataAccessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserServiceTests {

    private UserService userService;
    private GameService gameService;
    private ClearService clearService;

    @BeforeEach
    public void setup(){
        userService = new UserService();
        gameService = new GameService();
        clearService = new ClearService();
        clearService.clear();
    }

    @Test
    public void registerSuccess(){
        RegisterRequest registerRequest = new RegisterRequest("Ben", "12345", "Ben email");
        RegisterResult registerResult = userService.register(registerRequest);
        Assertions.assertNotNull(registerResult.authToken());
        Assertions.assertEquals("Ben", registerResult.username());
    }

    @Test
    public void registerTwice(){
        try {
            RegisterRequest registerRequest = new RegisterRequest("Ben", "12345", "Ben email");
            userService.register(registerRequest);
            userService.register(registerRequest);
            Assertions.fail("Exception was not thrown");
        } catch (DataAccessException dataAccessException) {

        }
    }
        @Test
        public void loginSuccess(){
            RegisterRequest registerRequest = new RegisterRequest("ben", "12345", "benemail");
            userService.register(registerRequest);
            LoginRequest loginRequest = new LoginRequest("ben", "12345");
            LoginResult loginResult = userService.login(loginRequest);
            Assertions.assertNotNull(loginResult.authToken());
            Assertions.assertEquals("Ben", loginResult.username());
        }
        @Test
        public void loginWrongUser(){
            try {
                LoginRequest loginRequest = new LoginRequest("Ben", "12345");
                userService.login(loginRequest);
                Assertions.fail("Exception was not thrown");
            }
            catch(DataAccessException dataAccessException){
            }

    }
    @Test
    public void logoutSuccess(){
        RegisterRequest registerRequest = new RegisterRequest("ben", "12345", "benemail");
        userService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest("ben", "12345");
        LoginResult loginResult = userService.login(loginRequest);
        LogoutRequest logoutRequest = new LogoutRequest(loginResult.authtoken());
        userService.logout(logoutRequest);

    }
    @Test
    public void logoutNotAuth(){
        try {
            LogoutRequest logoutRequest = new LogoutRequest("gdeygeuyg");
            userService.logout(logoutRequest);
            Assertions.fail("Exception not thrown");
        }
        catch(DataAccessException dataAccessException){

        }

    }
    @Test
    public void listGameSuccess(){
        RegisterRequest registerRequest = new RegisterRequest("ben", "12345", "benemail");
        RegisterResult user = userService.register(registerRequest);
        ListGamesRequest request = new ListGamesRequest(user.authToken());
        ListGamesResult result = gameService.listgames(request);

        Assertions.assertNotNull(result.games());

    }
    @Test
    public void listGameNoAuth(){
        try {
            ListGamesRequest request = new ListGamesRequest("hahdhdgbd");
            ListGamesResult result = gameService.listgames(request);
            Assertions.fail("Exception was not thrown");
        }
        catch(DataAccessException dataAccessException){

        }

    }
    @Test
    public void createGameSuccess(){
        RegisterRequest registerRequest = new RegisterRequest("Ben", "12345", "Ben email");
        RegisterResult user = userService.register(registerRequest);
        CreateGameRequest request = new CreateGameRequest("fun game", user.authtoken());
        CreateGamesResult result = gameService.creategame(request);

        Assertions.assertNotNull(result.gameId());
    }
    @Test
    public void createGameWrongAuth(){
        try {
            CreateGameRequest request = new CreateGameRequest("fun game", "jdndjd");
            CreateGamesResult result = gameService.creategame(request);
            Assertions.fail("Exception was not thrown");
        }
        catch(DataAccessException dataAccessException){

        }

    }
    @Test
    public void joinGameSuccess(){
        RegisterRequest registerRequest = new RegisterRequest("Ben", "12345", "Ben email");
        RegisterResult user = userService.register(registerRequest);
        CreateGameRequest request = new CreateGameRequest("fun game", user.authToken());
        CreateGamesResult game = gameService.creategame(request);
        JoinGameRequest gameRequest = new JoinGameRequest("white", game.gameId(), user.authToken());
        gameService.joingame(gameRequest);


    }
    @Test
    public void joinGameWrongAuth(){
        try {
            CreateGameRequest request = new CreateGameRequest("fun game", "hsvdhd");
            CreateGamesResult game = gameService.creategame(request);
            JoinGameRequest gameRequest = new JoinGameRequest("white", game.gameId(), "hsvdhd");
            gameService.joingame(gameRequest);
            Assertions.fail("Exception not thrown");
        }
        catch(DataAccessException dataAccessException){

        }

    }

    @Test
    public void clearSuccess(){
        clearService.clear();
    }

}
