package client.websocket;

import websocket.messages.ServerMessage;

public class Notification implements NotificationHandler{

    public void loadGame(ServerMessage serverMessage) {
        ui.BoardDraw.drawBoard(color);

    }

    public void error(ServerMessage serverMessage) {

    }

    public void notification(ServerMessage serverMessage) {

    }
}
