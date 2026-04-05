package client.websocket;
import client.LoginREPL;
import com.google.gson.Gson;
import websocket.messages.ServerMessage;
import websocket.commands.UserGameCommand;
import jakarta.websocket.*;

import java.net.URI;


public class WebSocketFacade extends Endpoint{

    Session session;
    NotificationHandler notificationHandler;
    String url;


    public WebSocketFacade(String url, LoginREPL notificationHandler) throws Exception{
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/ws");
            this.notificationHandler = (NotificationHandler) notificationHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message){
                    ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                    if(serverMessage.getServerMessageType() == ServerMessage.ServerMessageType.LOAD_GAME){
                        notificationHandler.loadGame();
                    }
                    else if(serverMessage.getServerMessageType() == ServerMessage.ServerMessageType.ERROR){
                        notificationHandler.error();
                    }
                    else if(serverMessage.getServerMessageType() == ServerMessage.ServerMessageType.NOTIFICATION){
                        notificationHandler.notification();
                    }


                }
            });
        } catch(Exception exception){
            throw new Exception(exception.getMessage());
        }


    }




    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig){

    }

    public void connect(String authToken, Integer gameID) throws Exception{
        try{
            var action = new UserGameCommand(UserGameCommand.CommandType.CONNECT, authToken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (Exception exception){
            throw new Exception(exception.getMessage());
        }

    }

    public void makeMove(String authToken, Integer gameID) throws Exception{
        try{
            var action = new UserGameCommand(UserGameCommand.CommandType.MAKE_MOVE, authToken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (Exception exception){
            throw new Exception(exception.getMessage());
        }

    }
    public void leave(String authToken, Integer gameID) throws Exception{
        try{
            var action = new UserGameCommand(UserGameCommand.CommandType.LEAVE, authToken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (Exception exception){
            throw new Exception(exception.getMessage());
        }

    }
    public void resign(String authToken, Integer gameID) throws Exception{
        try{
            var action = new UserGameCommand(UserGameCommand.CommandType.RESIGN, authToken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (Exception exception){
            throw new Exception(exception.getMessage());
        }
    }

}
