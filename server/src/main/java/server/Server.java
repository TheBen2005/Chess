package server;

import com.google.gson.Gson;
import io.javalin.*;

import io.javalin.http.Context;
import service.*;

public class Server {

    private final Javalin javalin;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        javalin.post("/user", this::registerHandler());
        javalin.post("/session", this::loginHandler());
        javalin.delete("/session", this::logoutHandler());
        javalin.get("/game", this::listGamesHandler());
        javalin.post("/game", this::createGameHandler());
        javalin.put("/game", this::joinGameHandler());
        javalin.delete("/db", this::clearApplicationHandler());

        // Register your endpoints and exception handlers here.

    }

    private void registerHandler(Context userInfo){
        UserService userService = new UserService();
        var serializer = new Gson();
        RegisterRequest registerRequest = serializer.fromJson(userInfo.body(), RegisterRequest.class);
        RegisterResult registerResult = userService.register(registerRequest);
    }

    private void loginHandler(userInfo){
        UserService userService = new UserService();
        var serializer = new Gson();
        LoginRequest loginRequest = serializer.fromJson(userInfo.body(), LoginRequest.class);
        LoginResult loginResult = userService.login(loginRequest);

    }

    private void logoutHandler(userInfo){
        UserService userService = new UserService();
        var serializer = new Gson();
        LogoutRequest logoutRequest = serializer.fromJson(userInfo.body(), LogoutRequest.class);
    }

    private void listGamesHandler(userInfo){
        GameService gameService = new GameService();
        var serializer = new Gson();
        ListGamesRequest listGamesRequest = serializer.fromJson(userInfo.body(), ListGamesRequest.class);
        ListGamesResult listGamesResult = gameService.listgames(listGamesRequest);
    }

    private void createGameHandler(userInfo){
        GameService gameService = new GameService();
        var serializer = new Gson();
        CreateGameRequest createGameRequest =  serializer.fromJson(userInfo.body(), CreateGameRequest.class);
        CreateGamesResult createGamesResult = gameService.creategame(createGameRequest);

    }

    private void joinGameHandler(userInfo){
        GameService gameService = new GameService();
        var serializer = new Gson();
        JoinGameRequest joinGameRequest =  serializer.fromJson(userInfo.body(), JoinGameRequest.class);


    }

    private void clearApplicationHandler(userInfo){
        ClearService clearService = new ClearService();
        clearService.clear();

    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }



    public void stop() {
        javalin.stop();
    }
}
