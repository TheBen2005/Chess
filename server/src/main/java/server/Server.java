package server;

import io.javalin.*;

public class Server {

    private final Javalin javalin;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        javalin.post("/user", this::register_handler());
        javalin.post("/session", this::login_handler());
        javalin.delete("/session", this::logout_handler());
        javalin.get("/game", this::list_games_handler());
        javalin.post("/game", this::create_game_handler());
        javalin.put("/game", this::join_game_handler());
        javalin.delete("/db", this::clear_application_handler());

        // Register your endpoints and exception handlers here.

    }

    private void register_handler(){

    }

    private void login_handler(){

    }

    private void logout_handler(){

    }

    private void list_games_handler(){

    }

    private void create_game_handler(){

    }

    private void join_game_handler(){

    }

    private void clear_application_handler(){

    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }



    public void stop() {
        javalin.stop();
    }
}
