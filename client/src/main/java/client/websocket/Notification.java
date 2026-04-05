package client.websocket;

import websocket.messages.ServerMessage;

public class Notification implements NotificationHandler{

    public void loadGame() {
        ui.BoardDraw.drawBoard(color);

    }

    public void error() {

    }

    public void notification() {

    }
}
