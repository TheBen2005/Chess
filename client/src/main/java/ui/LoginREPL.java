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
        System.out.println(LOGO + )
        System.out.print(help());

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
