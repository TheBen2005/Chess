package server;

import websocket.messages.ServerMessage;
import io.javalin.websocket.WsMessageContext;
import com.google.gson.Gson;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.MemoryDataAccess;
import dataaccess.MySqlDataAccess;
import io.javalin.*;

import io.javalin.http.Context;
import io.javalin.json.JavalinGson;
import service.*;
import websocket.commands.UserGameCommand;

public class Server {

    private final Javalin javalin;
    private DataAccess dataAccess;

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

            ws.onClose(_ -> System.out.println("Websocket closed"));
        });


    }

    private void webSocketHandlerHelper(WsMessageContext userInfo){
        var serializer = new Gson();
        UserGameCommand userGameCommand = serializer.fromJson(userInfo.message(), UserGameCommand.class);
        if(userGameCommand.getCommandType() == UserGameCommand.CommandType.CONNECT){
            connectHandler(userGameCommand);
        }
        else if(userGameCommand.getCommandType() == UserGameCommand.CommandType.LEAVE){
            leaveHandler(userGameCommand);
        }
        else if(userGameCommand.getCommandType() == UserGameCommand.CommandType.RESIGN){
            resignHandler(userGameCommand);
        }
        else if(userGameCommand.getCommandType() == UserGameCommand.CommandType.MAKE_MOVE){
            makeMoveHandler(userGameCommand);
        }
    }
    private void connectHandler(UserGameCommand userGameCommand){
        ServerMessage serverMessage = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);




    }
    private void makeMoveHandler(UserGameCommand userGameCommand){

    }
    private void leaveHandler(UserGameCommand userGameCommand){

    }
    private void resignHandler(UserGameCommand userGameCommand){

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
