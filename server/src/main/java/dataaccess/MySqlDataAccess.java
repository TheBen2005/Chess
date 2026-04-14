package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataaccess.DataAccessException;
import model.*;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;


public class MySqlDataAccess implements DataAccess {


    public MySqlDataAccess() throws DataAccessException {
        configureDatabase();
    }

    public UserData getUser(String userName) throws DataAccessException{
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM UserData WHERE username=?";
            try(PreparedStatement ps = conn.prepareStatement(statement)){
                ps.setString(1, userName);
                try (ResultSet rs = ps.executeQuery()){
                    if (rs.next()) {
                        return readUserData(rs);
                    }
                }
            }
        } catch (Exception e){
            throw new DataAccessException(e.getMessage());
        }
        return null;
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
        var statement = "INSERT INTO AuthData (username, authtoken) VALUES(?, ?)";
        int id = executeUpdate(statement, username, authToken);
    }

    public void deleteAuth(AuthData authData) throws DataAccessException{
        String authToken = authData.authToken();
        var statement = "DELETE FROM AuthData WHERE authtoken=?";
        int id = executeUpdate(statement, authToken);

    }

    public AuthData getAuth(String authToken) throws DataAccessException{
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM AuthData WHERE authtoken=?";
            try(PreparedStatement ps = conn.prepareStatement(statement)){
                ps.setString(1, authToken);
                try (ResultSet rs = ps.executeQuery()){
                    if (rs.next()) {
                        return readAuthData(rs);
                    }
                }
            }
        } catch (Exception e){
            throw new DataAccessException(e.getMessage());
        }
        return null;

    }

    public List<GameData> listGames() throws DataAccessException{
        List<GameData> gameList = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM GameData";
            try(PreparedStatement ps = conn.prepareStatement(statement)){
                try (ResultSet rs = ps.executeQuery()){
                    while (rs.next()) {
                        gameList.add(readGameData(rs));
                    }
                }
            }
        } catch (Exception e){
            throw new DataAccessException(e.getMessage());
        }
        return gameList;

    }

    public void createGame(GameData gameData) throws DataAccessException{
        int gameID = gameData.gameID();
        String whiteUsername = gameData.whiteUsername();
        String blackUsername = gameData.blackUsername();
        String gameName = gameData.gameName();
        ChessGame gameObject = gameData.game();
        String game = new Gson().toJson(gameObject);
        var statement = "INSERT INTO GameData (gameID, whiteUsername, blackUsername, gameName, game) VALUES(?, ?, ?, ?, ?)";
        int id = executeUpdate(statement, gameID, whiteUsername, blackUsername, gameName, game);



    }

    public GameData getGame(int gameId) throws DataAccessException{

        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM GameData WHERE gameID=?";
            try(PreparedStatement ps = conn.prepareStatement(statement)){
                ps.setInt(1, gameId);
                try (ResultSet rs = ps.executeQuery()){
                    if (rs.next()) {
                        return readGameData(rs);
                    }
                }
            }
        } catch (Exception e){
            throw new DataAccessException(e.getMessage());
        }
        return null;

    }

    public void updateGame(GameData gameData) throws DataAccessException{
        var statement = "Update GameData SET whiteUsername=?, blackUsername=?, gameName=?, game=? WHERE gameID=?";
        int gameID = gameData.gameID();
        String whiteUsername = gameData.whiteUsername();
        String blackUsername = gameData.blackUsername();
        String gameName = gameData.gameName();
        ChessGame game = gameData.game();
        String json = new GsonBuilder().serializeNulls().create().toJson(game);
        int id = executeUpdate(statement, whiteUsername, blackUsername, gameName, json, gameID);




    }

    public void clearUsers() throws DataAccessException{
        var statement = "TRUNCATE TABLE UserData";
        int id = executeUpdate(statement);

    }

    public void clearGames() throws DataAccessException{
        var statement = "TRUNCATE TABLE GameData";
        int id = executeUpdate(statement);
    }

    public void clearAuth() throws DataAccessException{
        var statement = "TRUNCATE TABLE AuthData";
        int id = executeUpdate(statement);
    }


    private final String[] createStatements = {
            """
            
            CREATE TABLE IF NOT EXISTS UserData(
            username varchar(256) NOT NULL PRIMARY KEY,
            password varchar(256) NOT NULL,
            email varchar(256) NOT NULL
            )
            """,
            """
            CREATE TABLE IF NOT EXISTS GameData(
            gameID int NOT NULL PRIMARY KEY,
            whiteUsername varchar(256),
            blackUsername varchar(256),
            gameName varchar(256) NOT NULL,
            game TEXT NOT NULL
            )
            """,
            """
            CREATE TABLE IF NOT EXISTS AuthData(
            username varchar(256) NOT NULL,
            authtoken varchar(256) NOT NULL PRIMARY KEY
            )
            """

    };


    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (Connection conn = DatabaseManager.getConnection()) {
            for (String statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQL Error: " + ex.getMessage());
            throw new DataAccessException("can't configure database");
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private int executeUpdate(String statement, Object... params) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (int i = 0; i < params.length; i++) {
                    Object param = params[i];
                    if (param instanceof String p) { ps.setString(i + 1, p); }
                    else if (param instanceof Integer p) { ps.setInt(i + 1, p); }
                    else if (param == null) { ps.setNull(i + 1, NULL); }
                }
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }

                return 0;
            }
        } catch (SQLException e) {
            throw new DataAccessException("can't update database");
        }
    }

    private UserData readUserData(ResultSet rs) throws SQLException {
        String username = rs.getString("username");
        String password = rs.getString("password");
        String email = rs.getString("email");
        UserData userData = new UserData(username, password, email);
        return userData;

    }

    private AuthData readAuthData(ResultSet rs) throws SQLException {
        String authToken = rs.getString("authtoken");
        String username = rs.getString("username");
        AuthData authData = new AuthData(authToken, username);
        return authData;

    }

    private GameData readGameData(ResultSet rs) throws SQLException {
        int gameID = rs.getInt("gameID");
        String whiteUsername = rs.getString("whiteUsername");
        String blackUsername = rs.getString("blackUsername");
        String gameName = rs.getString("gameName");
        String json = rs.getString("game");
        ChessGame game = new Gson().fromJson(json, ChessGame.class);
        GameData gameData = new GameData(gameID, whiteUsername, blackUsername, gameName, game);
        return gameData;
    }
    }
