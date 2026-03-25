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
            return

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
            return

        }

    }

    public String logout(String auth) {
        try {
            LogoutRequest logoutRequest = new LogoutRequest(auth);
            server.logout(logoutRequest);
            state = state.PRELOGIN;
            return String.format("You successfully signed out.");
        }
        catch(DataAccessException dataAccessException) {
            return

        }


    }
    public String createGame(String gameName, String authToken) {
        try {
            CreateGameRequest createGameRequest = new CreateGameRequest(gameName, authToken);
            server.createGame(createGameRequest);
            return String.format("You successfully created a game");
        }
        catch(DataAccessException dataAccessException) {
            return

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
