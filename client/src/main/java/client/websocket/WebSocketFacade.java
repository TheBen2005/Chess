package client.websocket;
import com.google.gson.Gson;
import websocket.messages.ServerMessage;
import websocket.commands.UserGameCommand;
import jakarta.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class WebSocketFacade extends Endpoint{

    Session session;
    ServerMessage serverMessage;

    public WebsocketFacade(String url, ServerMessage serverMessage) throws Exception{


    }




    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig){

    }

    public void Connect(){

    }

    public void makeMove(){

    }
    public void Leave(){

    }
    public void Resign(){

    }

}
