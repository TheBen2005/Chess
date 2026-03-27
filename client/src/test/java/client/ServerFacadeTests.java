package client;

import dataaccess.DataAccessException;
import org.junit.jupiter.api.*;
import server.Server;
import service.LoginRequest;
import service.LogoutRequest;
import service.RegisterRequest;


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
    public void setup() throws DataAccessException {
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
    void loginfailure() throws DataAccessException{
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
    void registerSuccess() throws DataAccessException{
        RegisterRequest registerRequest = new RegisterRequest("Theben", "12345", "password");
        var authData = facade.register(registerRequest);
        Assertions.assertNotNull(authData.authToken());

    }

    @Test
    void registerfailure() throws DataAccessException{
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
    void logoutSuccess() throws DataAccessException{
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
    void logoutFailure() throws DataAccessException{
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

    }

    @Test
    void createGamefailure() throws Exception{

    }

    @Test
    void listGamesSuccess() throws Exception{

    }

    @Test
    void listGamesfailure() throws Exception{

    }

    @Test
    void joinGameSuccess() throws Exception{

    }

    @Test
    void joinGameFailure() throws Exception{

    }

    @Test
    void clear() throws Exception{
        facade.clear();
    }



}
