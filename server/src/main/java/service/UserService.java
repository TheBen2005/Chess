package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.MemoryDataAccess;
import model.AuthData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;
public class UserService {
    private DataAccess dataAccess;

    public UserService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    public RegisterResult register(RegisterRequest registerRequest) throws DataAccessException {
        if(registerRequest.username() == null || registerRequest.password() == null || registerRequest.email() == null){
            throw new DataAccessException("bad request");
        }
        String username = registerRequest.username();
        UserData user = dataAccess.getUser(username);
        if(user != null){
            throw new DataAccessException("already taken");
        }
        UserData userData = new UserData(registerRequest.username(), registerRequest.password(), registerRequest.email());
        dataAccess.createUser(userData);
        String token = GenerateToken.generateToken();
        AuthData authData = new AuthData(token, username);
        dataAccess.createAuth(authData);
        RegisterResult registerResult = new RegisterResult(username, token);
        return registerResult;
    }

    public LoginResult login(LoginRequest loginRequest) throws DataAccessException{
        if(loginRequest.username() == null || loginRequest.password() == null){
            throw new DataAccessException("bad request");
        }
        UserData user = dataAccess.getUser(loginRequest.username());
        if(user == null){
            throw new DataAccessException("unauthorized");
        }
        if(!BCrypt.checkpw(loginRequest.password(), user.password())){
            throw new DataAccessException("unauthorized");
        }
        String token = GenerateToken.generateToken();
        AuthData authData = new AuthData(token, loginRequest.username());
        dataAccess.createAuth(authData);
        LoginResult loginResult = new LoginResult(loginRequest.username(), token);
        return loginResult;
    }
    public void logout(LogoutRequest logoutRequest) throws DataAccessException {
        AuthData authData = dataAccess.getAuth(logoutRequest.authToken());
        if(authData == null){
            throw new DataAccessException("unauthorized");
        }
        dataAccess.deleteAuth(authData);


    }
}
