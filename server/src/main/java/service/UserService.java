package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.MemoryDataAccess;
import model.AuthData;
import model.UserData;

public class UserService {

    public RegisterResult register(RegisterRequest registerRequest) throws DataAccessException {
        DataAccess dataAccess = new MemoryDataAccess();
        if(registerRequest.username() == null || registerRequest.password() == null || registerRequest.email() == null){
            throw new DataAccessException("bad request");
        }
        String username = registerRequest.username();
        dataAccess.getUser(username);
        DataAccess.createUser(registerRequest);
        String token = GenerateToken.generateToken();
        AuthData authData = new AuthData(token, username);
        RegisterResult registerResult = new RegisterResult(username, token);
        return registerResult;
    }

    public LoginResult login(LoginRequest loginRequest){
        return null;
    }

    public void logout(LogoutRequest logoutRequest){

    }
}
