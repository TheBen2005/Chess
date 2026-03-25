package ui;
package client;

import java.util.Arrays;
import java.util.Scanner;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.*;
import exception.ResponseException;
import client.websocket.NotificationHandler;
import server.ServerFacade;
import client.websocket.WebSocketFacade;
import webSocketMessages.Notification;

import static client.EscapeSequences.*;

public class LoginRepl implements NotificationHandler {
    private String visitorName = null;
    private final ServerFacade server;
    private final WebSocketFacade ws;
    private State state = State.PRELOGIN;
    private String authtoken = "";
    private List<GameData> gamelist;

    public PetClient(String serverUrl) throws ResponseException {
        server = new ServerFacade(serverUrl);
        ws = new WebSocketFacade(serverUrl, this);
    }
    public enum State {
        PRELOGIN,
        POSTLOGIN,
        GAMEPLAY
    }

    public void run() {
        System.out.println(LOGO + "Welcome to Ben's Chess Server. Sign in to start.");
        System.out.print(help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextline();
            try {
                result = eval(line);
                System.out.print(BLUE + result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();

    }

    public String eval(String input){
        try {
            String[] tokens = input.toLowerCase().split(" ");
            String cmd = (tokens.length > 0) ? tokens[0] : "help";
            String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "help" -> help();
                case "quit" -> quit();
                case "login" -> login(params);
                case "register" -> register(params);
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    }
    public String login(String name, String password) {
        try {
            LoginRequest loginRequest = new LoginRequest(name, password);
            LoginResult loginResult = server.login(loginRequest);
            authtoken = loginResult.authToken();
            state = state.POSTLOGIN;
            return String.format("You signed in as %s.", name);
        }
        catch(DataAccessException dataAccessException) {
            return String.format("Failed to login");

        }




    }

    public String quit() {

    }

    public String register(String username, String password, String email) {
        try {
            RegisterRequest registerRequest = new RegisterRequest(username, password);
            RegisterResult registerResult = server.login(registerRequest);
            authtoken = LoginResult.authtoken();
            state = state.POSTLOGIN;
            return String.format("You signed in as %s.", username);
        }
        catch(DataAccessException dataAccessException) {
            return String.format("Failed to register");

        }

    }

    public String logout() {
        try {
            LogoutRequest logoutRequest = new LogoutRequest(authtoken);
            server.logout(logoutRequest);
            state = state.PRELOGIN;
            authtoken = "";
            return String.format("You successfully signed out.");
        }
        catch(DataAccessException dataAccessException) {
            return String.format("Failed to logout");

        }


    }
    public String createGame(String gameName) {
        try {
            CreateGameRequest createGameRequest = new CreateGameRequest(gameName, authtoken);
            server.createGame(createGameRequest);
            return String.format("You successfully created a game");
        }
        catch(DataAccessException dataAccessException) {
            return String.format("Failed to create a game");

        }
    }
    public String listGames() {
        try {
            ListGamesRequest listGamesRequest = new ListGamesRequest(authtoken);
            games = server.listGames(listGamesRequest);
            int game_num = 1;
            var result = new Stringbuilder();
            for(GameData game: games) {
                result.append(String.format("%d. %s | White: %s | Black: %s\n", game_num, GameData.gameName(), GameData.whiteUsername(), GameData.blackUsername()));

            }
            state = state.PRELOGIN;
            authtoken = "";
            return result.toString();
        }
        catch(DataAccessException dataAccessException) {
            return String.format("Failed to listGames");
        }
    }

    public String playGame(String playercolor, int gameID) {

        try {
            ListGamesRequest listGamesRequest = new ListGamesRequest(authtoken);
            gameList = server.listGames(listGamesRequest);
            int game_num = 0;
            for (GameData game : gameList) {
                game_num++;
                if (game_num == gameID) {
                    int realID = game.gameID();
                    JoinGameRequest joinGameRequest = new JoinGameRequest(playercolor, gameID, authtoken);
                    server.join(joinGameRequest);
                    return String.format("You successfully joined a game");
                }
            }
        }
        catch(DataAccessException dataAccessException) {
            return String.format("Failed to join game");

        }





    }





    private void printPrompt() {
        System.out.println("\n" + RESET + ">>> " + GREEN);
    }
    public String help() {
        if(state == State.PRELOGIN){
            return """
                    - register <USERNAME> <PASSWORD> <EMAIL> - to create an account
                    - login <USERNAME> <PASSWORD> - to play chess
                    - quit - playing chess
                    - help - with possible commands            
                    
                    """;

        }
    }
}
