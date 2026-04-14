package server;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import model.AuthData;
import model.GameData;
import websocket.messages.ServerMessage;
import io.javalin.websocket.WsMessageContext;
import com.google.gson.Gson;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.MemoryDataAccess;
import dataaccess.MySqlDataAccess;
import io.javalin.*;
import org.eclipse.jetty.websocket.api.Session;

import io.javalin.http.Context;
import io.javalin.json.JavalinGson;
import service.*;
import websocket.commands.UserGameCommand;

import java.io.IOException;


public class Server {

    private final Javalin javalin;
    private DataAccess dataAccess;
    private final ConnectionManager connections = new ConnectionManager();

    public Server() {

        try{
            dataAccess = new MySqlDataAccess();
        }
        catch (DataAccessException e) {
            System.out.println("FAILED TO CREATE DATA ACCESS: " + e.getMessage());
            throw new RuntimeException(e);
        }
        javalin = Javalin.create(config -> { config.staticFiles.add("web");
            config.jsonMapper(new JavalinGson());
        });

        javalin.post("/user", this::registerHandler);
        javalin.post("/session", this::loginHandler);
        javalin.delete("/session", this::logoutHandler);
        javalin.get("/game", this::listGamesHandler);
        javalin.post("/game", this::createGameHandler);
        javalin.put("/game", this::joinGameHandler);
        javalin.delete("/db", this::clearApplicationHandler);
        javalin.ws("/ws", ws -> {
            ws.onConnect(ctx -> {
                ctx.enableAutomaticPings();
                System.out.println("Websocket connected");
            });
            ws.onMessage(ctx -> webSocketHandlerHelper(ctx));

            ws.onClose(ctx -> System.out.println("Websocket closed"));
        });


    }

    private void webSocketHandlerHelper(WsMessageContext userInfo) throws IOException, DataAccessException, InvalidMoveException {
        var serializer = new Gson();
        UserGameCommand userGameCommand = serializer.fromJson(userInfo.message(), UserGameCommand.class);
        if(userGameCommand.getCommandType() == UserGameCommand.CommandType.CONNECT){
            connectHandler(userGameCommand, userInfo.session);
        }
        else if(userGameCommand.getCommandType() == UserGameCommand.CommandType.LEAVE){
            leaveHandler(userGameCommand, userInfo.session);
        }
        else if(userGameCommand.getCommandType() == UserGameCommand.CommandType.RESIGN){
            resignHandler(userGameCommand, userInfo.session);
        }
        else if(userGameCommand.getCommandType() == UserGameCommand.CommandType.MAKE_MOVE){
            makeMoveHandler(userGameCommand, userInfo.session);
        }
    }
    private void connectHandler(UserGameCommand userGameCommand, Session session) throws IOException, DataAccessException {
        int gameId = userGameCommand.getGameID();
        String authToken = userGameCommand.getAuthToken();
        MySqlDataAccess dataAccess = new MySqlDataAccess();
        AuthData auth = dataAccess.getAuth(authToken);
        if(auth == null){
            var message = String.format("Error: Not authrized");
            connections.sendErrorNoUser(session, message);
            return;
        }
        String userName = auth.username();
        GameData game = dataAccess.getGame(gameId);
        if(game == null){
            var message = String.format("Error: Game not Found");
            connections.sendErrorNoUser(session, message);
            return;
        }
        String playerColor = "";
        boolean isPlaying = true;
        String whiteUsername = game.whiteUsername();
        String blackUsername = game.blackUsername();
        if(userName.equals(whiteUsername)){
            playerColor = "white";
        }
        else if(userName.equals(blackUsername)){
            playerColor = "black";
        }
        else{
            playerColor = "observe";
            isPlaying = false;
        }


        try {
            connections.add(session, userName, gameId);
            var message = "";
            if (isPlaying) {
                message = String.format("%s connected as %s", userName, playerColor);
            } else {
                message = String.format("%s is observing", userName);
            }
            ChessGame chessGame = game.game();
            var serverMessage = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message, null, playerColor, null);
            connections.broadcast(serverMessage, gameId, userName);
            var gameServerMessage = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME, null, null, playerColor, chessGame);
            if (isPlaying) {
                gameServerMessage = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME, null, null, playerColor, chessGame);
            } else {
                gameServerMessage = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME, null, null,"white", chessGame);
            }
            connections.specific_user(session, gameServerMessage, gameId, userName);
        }
        catch(IOException exception){
            var message = String.format("Error: failed to connect");
            connections.sendErrorNoUser(session, message);
        }
        catch(Exception exception){
            var message = exception.getMessage();
            var errorMessage = String.format("Error: " + message);
            connections.sendErrorNoUser(session, errorMessage);

        }


    }
    private void makeMoveHandler(UserGameCommand userGameCommand, Session session) throws DataAccessException, InvalidMoveException, IOException {
        int gameId = userGameCommand.getGameID();
        String authToken = userGameCommand.getAuthToken();
        MySqlDataAccess dataAccess = new MySqlDataAccess();
        AuthData auth = dataAccess.getAuth(authToken);
        if(auth == null){
            var message = String.format("Error: Not authrized");
            connections.sendErrorNoUser(session, message);
            return;
        }
        String userName = auth.username();
        GameData game = dataAccess.getGame(gameId);
        if(game == null){
            var message = String.format("Error: Game not Found");
            connections.sendErrorNoUser(session, message);
            return;
        }
        String playerColor = "";
        String whiteUsername = game.whiteUsername();
        String blackUsername = game.blackUsername();
        if(userName.equals(whiteUsername)){
            playerColor = "white";
        }
        else if(userName.equals(blackUsername)){
            playerColor = "black";
        }
        else{
            playerColor = "observe";
        }
        try {
            ChessGame chessGame = game.game();
            if(playerColor.equals("observe")){
                var message = String.format("Error: Obeservor cannot make a move");
                connections.sendErrorNoUser(session, message);
                return;
            }
            if(playerColor.equals("white") && chessGame.getTeamTurn() != ChessGame.TeamColor.WHITE){
                var message = String.format("Error: Not your turn");
                connections.sendErrorNoUser(session, message);
                return;

            }
            if(playerColor.equals("black") && chessGame.getTeamTurn() != ChessGame.TeamColor.BLACK){
                var message = String.format("Error: Not your turn");
                connections.sendErrorNoUser(session, message);
                return;

            }
            if(chessGame.getTeamTurn() == null){
                var message = String.format("Game is already Over");
                connections.sendErrorNoUser(session, message);
                return;
            }
            ChessMove move = userGameCommand.getMove();
            chessGame.makeMove(move);
            GameData newData = new GameData(game.gameID(), game.whiteUsername(), game.blackUsername(), game.gameName(), chessGame);
            dataAccess.updateGame(newData);
            var message = String.format("%s moved from %s to %s", userName, userGameCommand.getMoveOne(), userGameCommand.getMoveTwo());
            var serverMessageGame = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME, null, null, playerColor, chessGame);
            connections.broadcast(serverMessageGame, gameId, userName);
            connections.specific_user(session, serverMessageGame, gameId, userName);
            var serverMessageNotification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message, null, playerColor, null);
            connections.broadcast(serverMessageNotification, gameId, userName);
            if (playerColor.equals("white")) {
                if (chessGame.isInCheckmate(ChessGame.TeamColor.BLACK)) {
                    var checkMateMessage = String.format("checkmate");
                    var checkMateNotification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, checkMateMessage, null, playerColor, null);
                    connections.broadcast(checkMateNotification, gameId, userName);
                    connections.specific_user(session, checkMateNotification, gameId, userName);
                    chessGame.setTeamTurn(null);
                    GameData newDataTwo = new GameData(gameId, game.whiteUsername(), game.blackUsername(), game.gameName(), chessGame);
                    dataAccess.updateGame(newDataTwo);
                }
                else if (chessGame.isInStalemate(ChessGame.TeamColor.BLACK)) {
                    var staleMateMessage = String.format("stalemate");
                    var stalemateNotification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, staleMateMessage, null, playerColor, null);
                    connections.broadcast(stalemateNotification, gameId, userName);
                    connections.specific_user(session, stalemateNotification, gameId, userName);
                    chessGame.setTeamTurn(null);
                    GameData newDataTwo = new GameData(gameId, game.whiteUsername(), game.blackUsername(), game.gameName(), chessGame);
                    dataAccess.updateGame(newDataTwo);
                }
                else if (chessGame.isInCheck(ChessGame.TeamColor.BLACK)) {
                    var checkMessage = String.format("check");
                    var checkNotification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, checkMessage, null, playerColor, null);
                    connections.broadcast(checkNotification, gameId, userName);
                    connections.specific_user(session, checkNotification, gameId, userName);
                }
            }

            else if (playerColor.equals("black")) {
                if (chessGame.isInCheckmate(ChessGame.TeamColor.WHITE)) {
                    var checkMateMessage = String.format("checkmate");
                    var checkMateNotification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, checkMateMessage, null, playerColor, null);
                    connections.broadcast(checkMateNotification, gameId, userName);
                    connections.specific_user(session, checkMateNotification, gameId, userName);
                    chessGame.setTeamTurn(null);
                    GameData newDataTwo = new GameData(gameId, game.whiteUsername(), game.blackUsername(), game.gameName(), chessGame);
                    dataAccess.updateGame(newDataTwo);
                }
                else if (chessGame.isInStalemate(ChessGame.TeamColor.WHITE)) {
                    var staleMateMessage = String.format("stalemate");
                    var stalemateNotification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, staleMateMessage, null, playerColor, null);
                    connections.broadcast(stalemateNotification, gameId, userName);
                    connections.specific_user(session, stalemateNotification, gameId, userName);
                    chessGame.setTeamTurn(null);
                    GameData newDataTwo = new GameData(gameId, game.whiteUsername(), game.blackUsername(), game.gameName(), chessGame);
                    dataAccess.updateGame(newDataTwo);
                }
                else if (chessGame.isInCheck(ChessGame.TeamColor.WHITE)) {
                    var checkMessage = String.format("check");
                    var checkNotification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, checkMessage, null, playerColor, null);
                    connections.broadcast(checkNotification, gameId, userName);
                    connections.specific_user(session, checkNotification, gameId, userName);
                }
            }
        }
        catch(IOException exception){
            var message = String.format("Error: failed to connect");
            connections.sendErrorNoUser(session, message);

        }
        catch(InvalidMoveException exception){
            var message = String.format("Error: move not legal");
            connections.sendErrorNoUser(session, message);

        }
        catch(DataAccessException exception){
            var message = String.format("Error: failed to access game");
            connections.sendErrorNoUser(session, message);

        }
        catch(Exception exception){
            var message = exception.getMessage();
            var errorMessage = String.format("Error: " + message);
            connections.sendErrorNoUser(session, errorMessage);

        }






    }
    private void leaveHandler(UserGameCommand userGameCommand, Session session) throws IOException, DataAccessException {
        int gameId = userGameCommand.getGameID();
        String authToken = userGameCommand.getAuthToken();
        MySqlDataAccess dataAccess = new MySqlDataAccess();
        AuthData auth = dataAccess.getAuth(authToken);
        if(auth == null){
            var message = String.format("Error: Not authrized");
            connections.sendErrorNoUser(session, message);
            return;
        }
        String userName = auth.username();
        GameData game = dataAccess.getGame(gameId);
        if(game == null){
            var message = String.format("Error: Game not Found");
            connections.sendErrorNoUser(session, message);
            return;
        }
        String playerColor = "";
        String whiteUsername = game.whiteUsername();
        String blackUsername = game.blackUsername();
        if(userName.equals(whiteUsername)){
            playerColor = "white";
        }
        else if(userName.equals(blackUsername)){
            playerColor = "black";
        }
        else{
            playerColor = "observe";
        }

        try {
            ChessGame chessGame = game.game();
            connections.remove(session, userName, gameId);
            var message = String.format("%s left the game", userName);
            var serverMessage = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message, null,"", null);
            connections.broadcast(serverMessage, gameId, userName);
            if (game == null) {
                return;
            }
            GameData newData = null;
            if (game.blackUsername() != null && userName.equals(game.blackUsername())) {
                newData = new GameData(gameId, game.whiteUsername(), null, game.gameName(), game.game());
            } else if (game.whiteUsername() != null && userName.equals(game.whiteUsername())) {
                newData = new GameData(gameId, null, game.blackUsername(), game.gameName(), game.game());
            }
            if (newData != null) {
                dataAccess.updateGame(newData);
            }
        }
        catch(IOException exception){
            var message = String.format("Error: failed to connect");
            connections.sendErrorNoUser(session, message);
        }
        catch(DataAccessException exception){
            var message = String.format("Error: failed to access game");
            connections.sendErrorNoUser(session, message);

        }
        catch(Exception exception){
            var message = exception.getMessage();
            var errorMessage = String.format("Error: " + message);
            connections.sendErrorNoUser(session, errorMessage);

        }


    }
    private void resignHandler(UserGameCommand userGameCommand, Session session) throws IOException, DataAccessException {
        int gameId = userGameCommand.getGameID();
        String authToken = userGameCommand.getAuthToken();
        MySqlDataAccess dataAccess = new MySqlDataAccess();
        AuthData auth = dataAccess.getAuth(authToken);
        if(auth == null){
            var message = String.format("Error: Not authrized");
            connections.sendErrorNoUser(session, message);
            return;
        }
        String userName = auth.username();
        GameData game = dataAccess.getGame(gameId);
        if(game == null){
            var message = String.format("Error: Game not Found");
            connections.sendErrorNoUser(session, message);
            return;
        }
        ChessGame chessGame = game.game();
        String playerColor = "";
        String whiteUsername = game.whiteUsername();
        String blackUsername = game.blackUsername();
        if(userName.equals(whiteUsername)){
            playerColor = "white";
        }
        else if(userName.equals(blackUsername)){
            playerColor = "black";
        }
        else{
            playerColor = "observe";
        }
        if(playerColor.equals("observe")){
            var message = String.format("Error: Observor cannot resign");
            connections.sendErrorNoUser(session, message);
            return;
        }
        try {
            if(chessGame.getTeamTurn() == null){
                var message = String.format("Game is already Over");
                connections.sendErrorNoUser(session, message);
                return;
            }
            var message = String.format("%s resigned the game", userName);
            var serverMessage = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message, null, playerColor, null);
            connections.broadcast(serverMessage, gameId, userName);
            connections.specific_user(session, serverMessage, gameId, userName);

            if (game == null) {
                return;
            }
            chessGame.setTeamTurn(null);
            GameData newData = new GameData(gameId, game.whiteUsername(), game.blackUsername(), game.gameName(), chessGame);
            if (newData != null) {
                dataAccess.updateGame(newData);
                System.out.println("success");

            }
        }
        catch(IOException exception){
            var message = String.format("Error: failed to connect");
            connections.sendErrorNoUser(session, message);
        }
        catch(DataAccessException exception){
            var message = String.format("Error: failed to access game");
            connections.sendErrorNoUser(session, message);

        }
        catch(Exception exception){
            var message = exception.getMessage();
            var errorMessage = String.format("Error: " + message);
            connections.sendErrorNoUser(session, errorMessage);

        }






    }

    private void registerHandler(Context userInfo){
        try {
            UserService userService = new UserService(dataAccess);
            var serializer = new Gson();
            RegisterRequest registerRequest = serializer.fromJson(userInfo.body(), RegisterRequest.class);
            RegisterResult registerResult = userService.register(registerRequest);
            userInfo.json(registerResult);
            userInfo.status(200);
        }
        catch(DataAccessException dataAccessException){
            if(dataAccessException.getMessage().equals("bad request")){
                userInfo.json(new ResultError("Error: " + dataAccessException.getMessage()));
                userInfo.status(400);
            }
            else if(dataAccessException.getMessage().equals("already taken")){
                userInfo.json(new ResultError("Error: " + dataAccessException.getMessage()));
                userInfo.status(403);
            }
            else{
                userInfo.json(new ResultError("Error: Something is wrong"));
                userInfo.status(500);
            }

        }
    }

    private void loginHandler(Context userInfo){
        try{
            UserService userService = new UserService(dataAccess);
            var serializer = new Gson();
            LoginRequest loginRequest = serializer.fromJson(userInfo.body(), LoginRequest.class);
            LoginResult loginResult = userService.login(loginRequest);
            userInfo.json(loginResult);
            userInfo.status(200);
        }
        catch(DataAccessException dataAccessException){
            if(dataAccessException.getMessage().equals("bad request")){
                userInfo.json(new ResultError("Error: " + dataAccessException.getMessage()));
                userInfo.status(400);
            }
            else if(dataAccessException.getMessage().equals("unauthorized")){
                userInfo.json(new ResultError("Error: "+ dataAccessException.getMessage()));
                userInfo.status(401);
            }
            else{
                userInfo.json(new ResultError("Error: Something is wrong"));
                userInfo.status(500);
            }

        }


    }

    private void logoutHandler(Context userInfo){
        try {
            UserService userService = new UserService(dataAccess);
            String authToken = userInfo.header("authorization");
            LogoutRequest logoutRequest = new LogoutRequest(authToken);
            userService.logout(logoutRequest);
            userInfo.status(200);
        }
        catch(DataAccessException dataAccessException){
            if(dataAccessException.getMessage().equals("unauthorized")){
                userInfo.json(new ResultError("Error: " + dataAccessException.getMessage()));
                userInfo.status(401);
            }
            else{
                userInfo.json(new ResultError("Error: Something is wrong"));
                userInfo.status(500);
            }

        }
    }

    private void listGamesHandler(Context userInfo){
        try {
            GameService gameService = new GameService(dataAccess);
            var serializer = new Gson();
            String authToken = userInfo.header("authorization");
            ListGamesRequest listGamesRequest = new ListGamesRequest(authToken);
            ListGamesResult listGamesResult = gameService.listgames(listGamesRequest);
            userInfo.json(listGamesResult);
            userInfo.status(200);
        }
        catch (DataAccessException dataAccessException){
            if(dataAccessException.getMessage().equals("unauthorized")){
                userInfo.json(new ResultError("Error: " + dataAccessException.getMessage()));
                userInfo.status(401);
            }
            else{
                userInfo.json(new ResultError("Error: Something is wrong"));
                userInfo.status(500);
            }

        }
    }

    private void createGameHandler(Context userInfo){
        try {
            GameService gameService = new GameService(dataAccess);
            var serializer = new Gson();
            CreateGameRequest createGameRequest = serializer.fromJson(userInfo.body(), CreateGameRequest.class);
            String authToken = userInfo.header("authorization");
            CreateGameRequest completeRequest = new CreateGameRequest(createGameRequest.gameName(), authToken);
            CreateGamesResult createGamesResult = gameService.creategame(completeRequest);
            userInfo.json(createGamesResult);
            userInfo.status(200);
        }
        catch (DataAccessException dataAccessException) {
            if (dataAccessException.getMessage().equals("bad request")){
                userInfo.json(new ResultError("Error: " + dataAccessException.getMessage()));
                userInfo.status(400);
            }
            else if (dataAccessException.getMessage().equals("unauthorized")){
                userInfo.json(new ResultError("Error: " + dataAccessException.getMessage()));
                userInfo.status(401);
            } else {
                userInfo.json(new ResultError("Error: Something is wrong"));
                userInfo.status(500);
            }
        }

    }

    private void joinGameHandler(Context userInfo){
        try {
            GameService gameService = new GameService(dataAccess);
            var serializer = new Gson();
            JoinGameRequest joinGameRequest = serializer.fromJson(userInfo.body(), JoinGameRequest.class);
            String authToken = userInfo.header("authorization");
            JoinGameRequest completeRequest = new JoinGameRequest(joinGameRequest.playerColor(), joinGameRequest.gameID(), authToken);
            gameService.joingame(completeRequest);
            userInfo.status(200);
        }
        catch (DataAccessException dataAccessException) {
            if (dataAccessException.getMessage().equals("bad request")){
                userInfo.json(new ResultError("Error: " + dataAccessException.getMessage()));
                userInfo.status(400);
            }
            else if (dataAccessException.getMessage().equals("unauthorized")){
                userInfo.json(new ResultError("Error: " + dataAccessException.getMessage()));
                userInfo.status(401);
            }
            else if (dataAccessException.getMessage().equals("already taken")){
                userInfo.json(new ResultError("Error: " + dataAccessException.getMessage()));
                userInfo.status(403);
            }

            else {
                userInfo.json(new ResultError("Error: Something is wrong"));
                userInfo.status(500);
            }
        }



    }

    private void clearApplicationHandler(Context userInfo){
        try {
            ClearService clearService = new ClearService(dataAccess);
            clearService.clear();
            userInfo.json("{}");
            userInfo.status(200);
        }
        catch (DataAccessException dataAccessException){
            userInfo.json(new ResultError("Error: " + dataAccessException.getMessage()));
            userInfo.status(500);
        }

    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }



    public void stop() {
        javalin.stop();
    }
}
