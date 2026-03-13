package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import exception.ResponseException;
import model.*;
import org.mindrot.jbcrypt.BCrypt;

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
        String username = userData.username();
        String password = userData.password();
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        String email = userData.email();
        var statement = "INSERT INTO UserData (username, password, email) VALUES(?, ?, ?)";
        int id = executeUpdate(statement, username, hashedPassword, email);

    }

    public void createAuth(AuthData authData) throws DataAccessException{
        String username = authData.username();
        String authToken = authData.authToken();
        var statement = "INSERT INTO AuthData (username, authToken) VALUES(?, ?)";
        int id = executeUpdate(statement, username, authToken);
    }

    public void deleteAuth(AuthData authData){
        String username = authData.username();
        String authToken = authData.authToken();
        var statement = "DELETE AuthData (username, authToken) VALUES(?, ?)";
        int id = executeUpdate(statement, username, authToken);

    }

    public AuthData getAuth(String authToken) throws DataAccessException{

    }

    public List<GameData> listGames(){

    }

    public void createGame(GameData gameData){
        int gameID = gameData.gameID();
        String whiteUsername = gameData.whiteUsername();
        String blackUsername = gameData.blackUsername();
        String gameName = gameData.gameName();
        ChessGame game = gameData.game();
        String json = new Gson().toJson(game);
        var statement = "INSERT INTO AuthData (gameID, whiteUsername, blackUsername, gameName, json) VALUES(?, ?, ?, ?, ?)";
        int id = executeUpdate(statement, gameID, whiteUsername, blackUsername, gameName, json);



    }

    public GameData getGame(int gameId) throws DataAccessException{

    }

    public void updateGame(GameData gameData) throws DataAccessException{

    }

    public void clearUsers() throws DataAccessException{
        var statement = "DROP TABLE IF EXISTS UserData";
        int id = executeUpdate(statement);

    }

    public void clearGames() throws DataAccessException{
        var statement = "DROP TABLE IF EXISTS GameData";
        int id = executeUpdate(statement);
    }

    public void clearAuth() throws DataAccessException{
        var statement = "DROP TABLE IF EXISTS AuthData";
        int id = executeUpdate(statement);
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

    private int executeUpdate(String statement, Object... params) throws ResponseException {
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (int i = 0; i < params.length; i++) {
                    Object param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
                    else if (param instanceof PetType p) ps.setString(i + 1, p.toString());
                    else if (param == null) ps.setNull(i + 1, NULL);
                }
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }

                return 0;
            }
        } catch (SQLException e) {
            throw new ResponseException(ResponseException.Code.ServerError, String.format("unable to update database: %s, %s", statement, e.getMessage()));
        }
    }
    }
