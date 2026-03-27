
package client;

import java.util.Arrays;
import java.util.Scanner;
import java.util.List;

import dataaccess.DataAccessException;
import model.GameData;
import service.CreateGameRequest;
import service.LoginRequest;
import service.RegisterRequest;
import service.RegisterResult;
import service.ListGamesRequest;
import service.JoinGameRequest;
import service.LoginResult;
import service.LogoutRequest;
import service.ListGamesResult;


import ui.EscapeSequences;

public class LoginREPL {
    private String visitorName = null;
    private final ServerFacade server;
    private State state = State.PRELOGIN;
    private String authtoken = "";
    private List<GameData> gamelist;


    public LoginREPL(String serverUrl, EscapeSequences escapeSequences) throws DataAccessException {
        server = new ServerFacade(serverUrl);
    }
    public enum State {
        PRELOGIN,
        POSTLOGIN,
        GAMEPLAY
    }

    public void run() {
        System.out.println("Welcome to Ben's Chess Server. Sign in to start.");
        System.out.print(help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();
            try {
                result = eval(line);
                System.out.print(result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();

    }

    public String eval(String input){
            String[] tokens = input.toLowerCase().split(" ");
            String cmd = (tokens.length > 0) ? tokens[0] : "help";
            String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
            try {
                return switch (cmd) {
                    case "help" -> help();
                    case "quit" -> quit();
                    case "login" -> login(params);
                    case "register" -> register(params);
                    case "createGame" -> createGame(params);
                    case "listGames" -> listGames();
                    case "playGame" -> playGame(params);
                    case "observeGame" -> observeGame(params);
                    case "logout" -> logout();
                    default -> help();

                };
            }
            catch (Exception e){
                return e.getMessage();
            }
    }


    public String login(String... params) {
        if (state != State.PRELOGIN){
            return("Logged in already.");
        }
        try {
            String name = params[0];
            String password = params[1];
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
        return "quit";

    }

    public String register(String... params) {
        if (state != State.PRELOGIN){
            return("Registered already.");
        }
        String username = params[0];
        String password = params[1];
        String email = params[2];
        try {
            RegisterRequest registerRequest = new RegisterRequest(username, password, email);
            RegisterResult registerResult = server.register(registerRequest);
            authtoken = registerResult.authToken();
            state = state.POSTLOGIN;
            return String.format("You signed in as %s.", username);
        }
        catch(DataAccessException dataAccessException) {
            return String.format("Failed to register");

        }

    }

    public String logout() {
        if (state != State.POSTLOGIN){
            return("Not logged in yet.");
        }
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
    public String createGame(String... params) {
        if (state != State.POSTLOGIN){
            return("Not logged in yet.");
        }
        String gameName = params[0];
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
        if (state != State.POSTLOGIN){
            return("Not logged in yet.");
        }
        try {
            ListGamesRequest listGamesRequest = new ListGamesRequest(authtoken);
            ListGamesResult listGamesResult = server.listGames(listGamesRequest);
            List<GameData> games = listGamesResult.games();
            int game_num = 1;
            var result = new StringBuilder();
            for(GameData game: games) {
                result.append(String.format("%d. %s | White: %s | Black: %s\n", game_num, game.gameName(), game.whiteUsername(), game.blackUsername()));
                game_num++;

            }
            return result.toString();
        }
        catch(DataAccessException dataAccessException) {
            return String.format("Failed to listGames");
        }
    }

    public String playGame(String... params) {
        if (state != State.POSTLOGIN){
            return("Not logged in yet.");
        }
        String playercolor = params[0];
        int gameID = Integer.parseInt(params[1]);
        try {
            ListGamesRequest listGamesRequest = new ListGamesRequest(authtoken);
            ListGamesResult listGamesResult = server.listGames(listGamesRequest);
            gamelist = listGamesResult.games();
            int game_num = 0;
            for (GameData game : gamelist) {
                game_num++;
                if (game_num == gameID) {
                    int realID = game.gameID();
                    JoinGameRequest joinGameRequest = new JoinGameRequest(playercolor, realID, authtoken);
                    server.joinGame(joinGameRequest);
                    state = state.GAMEPLAY;
                    return String.format("You successfully joined a game");
                }


            }

        }
        catch(DataAccessException dataAccessException) {
            return String.format("Failed to join game");

        }

        return String.format("Failed to join game");



    }

    public String observeGame(String... params) {
        if (state != State.POSTLOGIN){
            return("Not logged in yet.");
        }
        int gameID = Integer.parseInt(params[0]);
        try {
            ListGamesRequest listGamesRequest = new ListGamesRequest(authtoken);
            ListGamesResult listGamesResult = server.listGames(listGamesRequest);
            gamelist = listGamesResult.games();
            int game_num = 0;
            for (GameData game : gamelist) {
                game_num++;
                if (game_num == gameID) {
                    int realID = game.gameID();
                    JoinGameRequest joinGameRequest = new JoinGameRequest(null, realID, authtoken);
                    server.joinGame(joinGameRequest);
                    state = state.GAMEPLAY;
                    return String.format("You successfully joined a game");
                }
            }
        }
        catch(DataAccessException dataAccessException) {
            return String.format("Failed to join game");

        }
        return String.format("Failed to join game");



    }


    private void printPrompt() {
        System.out.println("\n" + ">>> ");
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
        if(state == State.POSTLOGIN) {
            return """
                    - createGame <gamename> - a game
                    - listGames - games
                    - playGame <COLOR><ID> - a game
                    - help - with possible commands
                    - observeGame <ID> - a game  
                    - logout - when you are done
                    - quit - playing chess         
                    
                    """;
        }

        if(state == State.GAMEPLAY){
            return "game";





        }
        return "";
    }
}
