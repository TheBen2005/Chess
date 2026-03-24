package ui;
package client;

import java.util.Arrays;
import java.util.Scanner;

import com.google.gson.Gson;
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
    public String login(String... params) {

    }

    public String quit() {

    }

    public String register(String... params) {

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
