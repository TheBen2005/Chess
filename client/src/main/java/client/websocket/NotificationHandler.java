package client.websocket;

import websocket.messages.ServerMessage;


public interface NotificationHandler {
    void loadGame(ServerMessage serverMessage);

    void error(ServerMessage serverMessage);

    void notification(ServerMessage serverMessage);

}
