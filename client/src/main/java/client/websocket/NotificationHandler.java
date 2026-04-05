package client.websocket;

import websocket.messages.ServerMessage;


public interface NotificationHandler {
    void loadGame();

    void error();

    void notification();

}
