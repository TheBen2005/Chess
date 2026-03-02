package service;

import dataaccess.DataAccessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserServiceTests {

    private UserService userService;

    @BeforeEach
    public void setup(){
        userService = new UserService();
    }

    @Test
    public void registerSuccess(){
        RegisterRequest registerRequest = new("Ben", "12345", "Ben email");
        RegisterResult registerResult = userService.register(registerRequest);
        Assertions.assertNotNull(registerResult.authToken);
        Assertions.assertEquals("Ben" registerResult.username());
    }

    public void registerTwice(){
        try{
            RegisterRequest registerRequest = new("Ben", "12345", "Ben email");
            userService.register(registerRequest);
            userService.register(registerRequest);
            Assertions.fail("Exception was not thrown")
        }
        catch(DataAccessException dataAccessException){

        }

    }

}
