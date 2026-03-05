package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.MemoryDataAccess;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserServiceTests {

    private UserService userService;
    private GameService gameService;
    private ClearService clearService;

    @BeforeEach
    public void setup() throws DataAccessException {
        DataAccess dataAccess = new MemoryDataAccess();
        userService = new UserService(dataAccess);
        gameService = new GameService(dataAccess);
        clearService = new ClearService(dataAccess);
        clearService.clear();
    }

    @Test
    public void registerSuccess() throws DataAccessException {
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
        public void loginSuccess() throws DataAccessException {
            RegisterRequest registerRequest = new RegisterRequest("ben", "12345", "benemail");
            userService.register(registerRequest);
            LoginRequest loginRequest = new LoginRequest("ben", "12345");
            LoginResult loginResult = userService.login(loginRequest);
            Assertions.assertNotNull(loginResult.authToken());
            Assertions.assertEquals("ben", loginResult.username());
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
    public void logoutSuccess() throws DataAccessException {
        RegisterRequest registerRequest = new RegisterRequest("ben", "12345", "benemail");
        RegisterResult registerResult = userService.register(registerRequest);
        String token = registerResult.authToken();
        LogoutRequest logoutRequest = new LogoutRequest(token);
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


}
