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
        dataAccess.createUser(registerRequest);
        String token = GenerateToken.generateToken();
        AuthData authData = new AuthData(token, username);
        dataAccess.createAuth(authData);
        RegisterResult registerResult = new RegisterResult(username, token);
        return registerResult;
    }

    public LoginResult login(LoginRequest loginRequest) throws DataAccessException{
        DataAccess dataAccess = new MemoryDataAccess();
        if(loginRequest.username() == null){
            throw new DataAccessException("bad request");
        }
        UserData user = dataAccess.getUser(loginRequest.username());
        if(user.password != loginRequest.password){
            throw new DataAccessException("unauthorized");
        }
        String token = GenerateToken.generateToken();
        AuthData authData = new AuthData(token, loginRequest.username());
        dataAccess.createAuth(authData);
        LoginResult loginResult = new LoginResult(loginRequest.username(), token);
        return loginResult;
    }

    public void logout(LogoutRequest logoutRequest){

    }
}
