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
    private List<String> users = new ArrayList<>();
    public final ConcurrentHashMap<Integer, List<String>> connectionsGames = new ConcurrentHashMap<>();
    public final ConcurrentHashMap<String, Session> connectionsUsers = new ConcurrentHashMap<>();

    public void add(Session session, String userName, int gameId){
        connectionsUsers.put(userName, session);
        users.add(userName);
        connectionsGames.put(gameId, users);
    }

    public void remove(Session session, String userName, int gameId){
        connectionsUsers.remove(userName, session);
        users.remove(userName);
        connectionsGames.put(gameId, users);
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
}
