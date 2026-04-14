
package client;

import java.util.Arrays;
import java.util.Scanner;
import java.util.List;


import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
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
    private String username = "";
    private int game_id;
    private String playerColor;
    private ChessGame game;


    public LoginREPL(String serverUrl) throws Exception {
        server = new ServerFacade(serverUrl);
        ws = new WebSocketFacade(serverUrl, this);
    }

    public void loadGame(ServerMessage serverMessage) {
        String color = serverMessage.getPlayerColor();
        game = serverMessage.getGame();

        if(color.equals("white")){
            ui.LiveBoard.drawBoard(true, game, null);
        }
        else if(color.equals("black")){
            ui.LiveBoard.drawBoard(false, game, null);
        }

        System.out.println("This is working");




    }

    public void error(ServerMessage serverMessage){
        String message = serverMessage.getServerMessage();
        System.out.println(message);
        printPrompt();

    }

    public void notification(ServerMessage serverMessage){
        String message = serverMessage.getServerMessage();
        System.out.println(message);
        printPrompt();

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
                    case "leave" -> leave();
                    case "resign" -> resign();
                    case "makemove" -> makeMove(params);
                    case "highlight" -> highlight(params);
                    case "redraw" -> redraw();
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
            username = loginResult.username();
            state = state.POSTLOGIN;
            return String.format("You signed in as %s.", name);
        }
        catch(Exception exception) {
            return String.format("Failed to login");

        }




    }

    public String highlight(String... params){
        String coordinate = params[0].toLowerCase();
        char colLetter = coordinate.charAt(0);
        int col = letterToColumn(colLetter);
        char rowLetter = coordinate.charAt(1);
        int row = Integer.parseInt(String.valueOf(rowLetter));
        ChessPosition position = new ChessPosition(row, col);
        ui.LiveBoard.drawBoard(true, game, position);
        return "You highlighted moves.";



    }

    public String makeMove(String... params) throws Exception{

        if (state != State.GAMEPLAY){
            throw new Exception("Not in game yet");
        }
        String moveOne = params[0];
        String moveTwo = params[1];

        char colLetterOne = moveOne.charAt(0);
        char colLetterTwo = moveTwo.charAt(0);
        int colOne = letterToColumn(colLetterOne);
        int colTwo = letterToColumn(colLetterTwo);
        char rowLetterOne = moveOne.charAt(1);
        char rowLetterTwo = moveTwo.charAt(1);
        int rowOne = Integer.parseInt(String.valueOf(rowLetterOne));
        int rowTwo = Integer.parseInt(String.valueOf(rowLetterTwo));

        ChessPosition positionOne = new ChessPosition(rowOne, colOne);
        ChessPosition positionTwo = new ChessPosition(rowTwo, colTwo);
        ChessMove move = new ChessMove(positionOne, positionTwo, null);
        if(params.length == 3) {
            String pieceType = params[2];
            if(pieceType.equals("pawn")){
                move = new ChessMove(positionOne, positionTwo, ChessPiece.PieceType.PAWN);
            }
            else if(pieceType.equals("rook")){
                move = new ChessMove(positionOne, positionTwo, ChessPiece.PieceType.ROOK);
            }
            else if(pieceType.equals("queen")){
                move = new ChessMove(positionOne, positionTwo, ChessPiece.PieceType.QUEEN);
            }
            else if(pieceType.equals("king")){
                move = new ChessMove(positionOne, positionTwo, ChessPiece.PieceType.KING);
            }
            else if(pieceType.equals("bishop")){
                move = new ChessMove(positionOne, positionTwo, ChessPiece.PieceType.BISHOP);
            }
            else if(pieceType.equals("knight")){
                move = new ChessMove(positionOne, positionTwo, ChessPiece.PieceType.KNIGHT);
            }

        }
        ws.makeMove(authtoken, game_id, username, move, playerColor, moveOne, moveTwo);



        return "move made.";

    }
    private int letterToColumn(char character){
        int column = 0;
        if(character == 'a'){
            column = 1;
        }
        else if(character == 'b'){
            column = 2;
        }
        else if(character == 'c'){
            column = 3;
        }
        else if(character == 'd'){
            column = 4;
        }
        else if(character == 'e'){
            column = 5;
        }
        else if(character == 'f'){
            column = 6;
        }
        else if(character == 'g'){
            column = 7;
        }
        else if(character == 'h'){
            column = 8;
        }
        return column;

    }


    public String redraw(){
        if(playerColor.equals("WHITE")){
            ui.LiveBoard.drawBoard(true, game, null);
        }
        else{
            ui.LiveBoard.drawBoard(false, game, null);
        }
        return "Board redrawn";



    }

    public String leave() throws Exception{
        if (state != State.GAMEPLAY){
            throw new Exception("Not in game yet");
        }
        ws.leave(authtoken, game_id, username);
        state = state.POSTLOGIN;
        return "You left.";




    }

    public String resign() throws Exception{
        if (state != State.GAMEPLAY){
            throw new Exception("Not in game yet");
        }
        ws.resign(authtoken, game_id, username);
        return "You resigned.";


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
            username = registerResult.username();
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
            username = "";
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
        playerColor = playercolor;
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
                    game_id = realID;
                    JoinGameRequest joinGameRequest = new JoinGameRequest(playercolor, realID, authtoken);
                    server.joinGame(joinGameRequest);
                    ws.connect(authtoken, realID, username, playercolor);
                    state = state.GAMEPLAY;
                    Boolean color = playercolor.equalsIgnoreCase("white");
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
            String playerColor = "observe";
            for (GameData game : gamelist) {
                game_num++;
                if (game_num == gameID) {
                    int realID = game.gameID();
                    game_id = realID;
                    state = state.GAMEPLAY;
                    ws.connect(authtoken, realID, username, playerColor);
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
            return """
                    - resign - ends game
                    - makemove <coordinate one> <coordinate two>- makes move
                    - highlight <coordinate>- highlights moves of coordinate
                    - leave - leaves game
                    - help - with possible commands
                    - redraw - redraws chessboard
                    
                    """;





        }
        return "";
    }
}
