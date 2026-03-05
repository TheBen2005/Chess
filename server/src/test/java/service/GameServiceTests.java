package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.MemoryDataAccess;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameServiceTests {

    private GameService gameService;
    private ClearService clearService;
    private UserService userService;

    @BeforeEach
    public void setup() throws DataAccessException {
        DataAccess dataAccess = new MemoryDataAccess();
        userService = new UserService(dataAccess);
        gameService = new GameService(dataAccess);
        clearService = new ClearService(dataAccess);
        clearService.clear();
    }

    @Test
    public void listGameSuccess() throws DataAccessException {
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
    public void createGameSuccess() throws DataAccessException {
        RegisterRequest registerRequest = new RegisterRequest("Ben", "12345", "Ben email");
        RegisterResult user = userService.register(registerRequest);
        CreateGameRequest request = new CreateGameRequest("fun game", user.authToken());
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
    public void joinGameSuccess() throws DataAccessException {
        RegisterRequest registerRequest = new RegisterRequest("Ben", "12345", "Ben email");
        RegisterResult user = userService.register(registerRequest);
        CreateGameRequest request = new CreateGameRequest("fun game", user.authToken());
        CreateGamesResult game = gameService.creategame(request);
        JoinGameRequest gameRequest = new JoinGameRequest("WHITE", game.gameId(), user.authToken());
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
}
