package websocket.commands;

import chess.ChessMove;

import java.util.Objects;

/**
 * Represents a command a user can send the server over a websocket
 * <p>
 * Note: You can add to this class, but you should not alter the existing
 * methods.
 */
public class UserGameCommand {

    private final CommandType commandType;

    private final String authToken;

    private final Integer gameID;

    private final String userName;

    private final String playerColor;

    private ChessMove move;

    private String moveOne;

    private String moveTwo;

    public UserGameCommand(CommandType commandType, String authToken, Integer gameID, String username, String playerColor, ChessMove move, String moveOne, String moveTwo) {
        this.commandType = commandType;
        this.authToken = authToken;
        this.gameID = gameID;
        this.userName = username;
        this.playerColor = playerColor;
        this.move = move;
        this.moveOne = moveOne;
        this.moveTwo = moveTwo;

    }

    public enum CommandType {
        CONNECT,
        MAKE_MOVE,
        LEAVE,
        RESIGN
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public String getAuthToken() {
        return authToken;
    }

    public Integer getGameID() {
        return gameID;
    }

    public String getUserName() {
        return userName;
    }

    public String getPlayerColor(){return playerColor; }

    public ChessMove getMove(){
        return move;
    }

    public String getMoveOne(){
        return moveOne;
    }
    public String getMoveTwo(){
        return moveTwo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserGameCommand that)) {
            return false;
        }
        return getCommandType() == that.getCommandType() &&
                Objects.equals(getAuthToken(), that.getAuthToken()) &&
                Objects.equals(getGameID(), that.getGameID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommandType(), getAuthToken(), getGameID());
    }
}
