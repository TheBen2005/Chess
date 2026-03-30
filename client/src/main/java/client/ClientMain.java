package client;

import chess.*;
import ui.BoardDraw;


public class ClientMain {
    public static void main(String[] args) throws Exception{
        new LoginREPL("http://localhost:8080").run();
    }
}
