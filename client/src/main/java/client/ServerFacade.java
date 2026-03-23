package client;

package server;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import model.*;

import java.net.*;
import java.net.http.*;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

public class ServerFacade {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }

    public void help(){

    }

    public void quit(){

    }

    public LoginResult login(LoginRequest request){

    }

    public RegisterResult register(RegisterRequest request){

    }

    public void logout(LogoutRequest request){

    }

    public CreateGameResult createGame(CreateGameRequest request){

    }

    public ListGamesResult listGames(ListGamesRequest request){

    }

    public void playGame(JoinGameRequest request){

    }

    public void observeGame(){

    }






}
