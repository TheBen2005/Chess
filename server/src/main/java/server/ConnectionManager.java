package server;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    private int gameId;
    public final ConcurrentHashMap<Integer, List<String>> connectionsGames = new ConcurrentHashMap<>();
    public final ConcurrentHashMap<String, Session> connectionsUsers = new ConcurrentHashMap<>();

    public void add(Session session, String userName, int gameId){
        connectionsUsers.put(userName, session);
        List<String> users = connectionsGames.get(gameId);
        if(users == null){
            users = new ArrayList<>();
            connectionsGames.put(gameId, users);

        }
        users.add(userName);
    }

    public void remove(Session session, String userName, int gameId){
        connectionsUsers.remove(userName, session);
        List<String> users = connectionsGames.get(gameId);
        if(users != null){
            users.remove(userName);
        }
    }

    public void specific_user(Session session, ServerMessage serverMessage, int gameId, String userName) throws IOException {
        Gson gson = new Gson();
        String msg = gson.toJson(serverMessage);
        List<String> userList = connectionsGames.get(gameId);
        if(userList == null){
            return;
        }
        for(String user: userList){
            if(user.equals(userName)){
                if(session != null && session.isOpen()){
                    session.getRemote().sendString(msg);
                }

            }
        }
    }



    public void broadcast(ServerMessage serverMessage, int gameId, String userName) throws IOException{
        Gson gson = new Gson();
        String msg = gson.toJson(serverMessage);
        List<String> userList = connectionsGames.get(gameId);
        if(userList == null){
            return;
        }
        for(String user: userList){
            if(!user.equals(userName)){
                Session session = connectionsUsers.get(user);
                if(session != null && session.isOpen()){
                    session.getRemote().sendString(msg);
                }
            }

        }
    }

    public void sendErrorNoUser(Session session, String message) throws IOException{
        ServerMessage error = new ServerMessage(ServerMessage.ServerMessageType.ERROR, null, message, null, null);
        Gson gson = new Gson();
        String msg = gson.toJson(error);
        if(session != null){
            if(session.isOpen()){
                session.getRemote().sendString(msg);
            }
        }
    }
}
