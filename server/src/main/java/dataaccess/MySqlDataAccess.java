package dataaccess;

import com.google.gson.Gson;
import exception.ResponseException;
import model.*;

import java.sql.*;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;


public class MySqlDataAccess implements DataAccess {


    public MySqlDataAccess() throws ResponseException {
        DatabaseManager.configureDatabase();
    }

    public UserData getUser(String username) throws DataAccessException{
    }

    public void createUser(UserData userData) throws DataAccessException{
    }

    public void createAuth(AuthData authData) throws DataAccessException{
    }

    public void deleteAuth(AuthData authData){

    }

    public AuthData getAuth(String authToken) throws DataAccessException{

    }

    public List<GameData> listGames(){

    }

    public void createGame(GameData gameData){

    }

    public GameData getGame(int gameId) throws DataAccessException{

    }

    public void updateGame(GameData gameData) throws DataAccessException{

    }

    public void clearUsers() throws DataAccessException{

    }

    public void clearGames() throws DataAccessException{

    }

    public void clearAuth() throws DataAccessException{
    }


    private final String[] createStatements = {
            """
            
            CREATE TABLE IF NOT EXISTS UserData(
            username varchar(256) NOT NULL PRIMARY KEY
            password varchar(256) NOT NULL
            email varchar(256) NOT NULL
            )
            
            CREATE TABLE IF NOT EXISTS GameData(
            id Int GameID NOT NULL PRIMARY KEY
            whiteUsername varchar(256) NOT NULL 
            blackUsername varchar(256) NOT NULL
            gameName varchar(256) NOT NULL
            game varchar(256) NOT NULL
            )
            
            CREATE TABLE IF NOT EXISTS AuthData(
            username varchar(256) NOT NULL PRIMARY KEY
            authToken varchar(256) NOT NULL 
            )
            
    
            CREATE TABLE IF NOT EXISTS  pet (
              `id` int NOT NULL AUTO_INCREMENT,
              `name` varchar(256) NOT NULL,
              `type` ENUM('CAT', 'DOG', 'FISH', 'FROG', 'ROCK') DEFAULT 'CAT',
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`id`),
              INDEX(type),
              INDEX(name)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };


    private void configureDatabase() throws ResponseException, DataAccessException {
        DatabaseManager.createDatabase();
        try (Connection conn = DatabaseManager.getConnection()) {
            for (String statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new ResponseException(ResponseException.Code.ServerError, String.format("Unable to configure database: %s", ex.getMessage()));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
    }
