
package client;

import java.util.Arrays;
import java.util.Scanner;
import java.util.List;


import client.websocket.NotificationHandler;
import client.websocket.WebSocketFacade;
import model.GameData;
import recordClasses.CreateGameRequest;
import recordClasses.LoginRequest;
import recordClasses.RegisterRequest;
import recordClasses.RegisterResult;
import recordClasses.ListGamesRequest;
import recordClasses.JoinGameRequest;
import recordClasses.LoginResult;
import recordClasses.LogoutRequest;
import recordClasses.ListGamesResult;
import websocket.messages.ServerMessage;



public class LoginREPL implements NotificationHandler {
    private String visitorName = null;
    private final ServerFacade server;
    private State state = State.PRELOGIN;
    private String authtoken = "";
    private List<GameData> gamelist;
    private final WebSocketFacade ws;


    public LoginREPL(String serverUrl) throws Exception {
        server = new ServerFacade(serverUrl);
        ws = new WebSocketFacade(serverUrl, this);
    }

    public void notify(ServerMessage serverMessage) {

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
                    case "creategame" -> createGame(params);
                    case "listgames" -> listGames();
                    case "playgame" -> playGame(params);
                    case "observegame" -> observeGame(params);
                    case "logout" -> logout();
                    /*case "highlight" -> highlight();
                    case "resign" -> resign();
                    case "make move" -> makeMove();
                    case "leave" -> leave();
                    case "redraw" -> redraw();*/
                    default -> not_valid();

                };
            }
            catch (Exception e){
                return e.getMessage();
            }
    }

    private String not_valid(){
        return "not valid input";
    }


    public String login(String... params) {
        if(params.length != 2){
            return "Should have 2 arguments";
        }
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
        catch(Exception exception) {
            return String.format("Failed to login");

        }




    }

    public void highlight(){

    }

    public void makeMove(){


    }

    public void redraw(String... params){

    }

    public void leave() throws Exception{
        if (state != State.GAMEPLAY){
            throw new Exception("Not in game yet");
        }

        try{



        }
        catch(Exception exception){

        }



    }

    public void resign(){

    }

    public String quit() {
        return "quit";

    }

    public String register(String... params) {
        if(params.length != 3){
            return "Should have 3 arguments";
        }
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
        catch(Exception exception) {
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
        catch(Exception exception) {
            return String.format("Failed to logout");

        }


    }
    public String createGame(String... params) {
        if(params.length != 1){
            return "Should have 1 argument";
        }
        if (state != State.POSTLOGIN){
            return("Not logged in yet.");
        }
        String gameName = params[0];
        try {
            CreateGameRequest createGameRequest = new CreateGameRequest(gameName, authtoken);
            server.createGame(createGameRequest);
            return String.format("You successfully created a game");
        }
        catch(Exception exception) {
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
        catch(Exception exception) {
            return String.format("Failed to listGames");
        }
    }

    public String playGame(String... params) {
        if(params.length != 2){
            return "Should have 2 arguments";
        }
        if (state != State.POSTLOGIN){
            return("Not logged in yet.");
        }
        String playercolor = params[0].toUpperCase();
        try {
            int gameID = Integer.parseInt(params[1]);
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
                    ws.connect(authtoken, realID);
                    //state = state.GAMEPLAY;
                    Boolean color = playercolor.equalsIgnoreCase("white");
                    ui.BoardDraw.drawBoard(color);
                    return String.format("You successfully joined a game");
                }


            }

        }
        catch(Exception exception) {
            return String.format("Failed to join game");

        }

        return String.format("Failed to join game");



    }

    public String observeGame(String... params) {
        if(params.length != 1){
            return "Should have 1 argument";
        }
        if (state != State.POSTLOGIN){
            return("Not logged in yet.");
        }

        try {
            int gameID = Integer.parseInt(params[0]);
            ListGamesRequest listGamesRequest = new ListGamesRequest(authtoken);
            ListGamesResult listGamesResult = server.listGames(listGamesRequest);
            gamelist = listGamesResult.games();
            int game_num = 0;
            for (GameData game : gamelist) {
                game_num++;
                if (game_num == gameID) {
                    int realID = game.gameID();
                    //state = state.GAMEPLAY;
                    ui.BoardDraw.drawBoard(true);
                    return String.format("You successfully joined a game");
                }
            }
        }
        catch(Exception exception) {
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
                    - creategame <gamename> - a game
                    - listgames - games
                    - playgame <COLOR><ID> - a game
                    - help - with possible commands
                    - observegame <ID> - a game  
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
