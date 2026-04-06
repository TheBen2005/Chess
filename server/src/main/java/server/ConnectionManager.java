package server;

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
    public void broadcast(Session excludeSession, ServerMessage serverMessage) throws IOException{
        String msg = serverMessage.toString();
        for(Session c: connectionsUsers.values()){
            if (c.isOpen()){
                if (!c.equals(excludeSession)){
                    c.getRemote().sendString(msg);
                }
            }
        }
    }
}
