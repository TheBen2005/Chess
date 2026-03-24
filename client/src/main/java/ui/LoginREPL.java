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
    private State state = State.SIGNEDOUT;

    public PetClient(String serverUrl) throws ResponseException {
        server = new ServerFacade(serverUrl);
        ws = new WebSocketFacade(serverUrl, this);
    }

    public void run() {
        System.out.println(LOGO + )


    }
}
