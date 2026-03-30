package client;


import com.google.gson.Gson;

import model.*;
import service.*;
import recordClasses.CreateGameRequest;
import recordClasses.LoginRequest;
import recordClasses.RegisterRequest;
import recordClasses.RegisterResult;
import recordClasses.ListGamesRequest;
import recordClasses.JoinGameRequest;
import recordClasses.LoginResult;
import recordClasses.LogoutRequest;
import recordClasses.ListGamesResult;

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

    public LoginResult login(LoginRequest request) throws Exception {
        var build = buildRequest("POST", "/session", request);
        var response = sendRequest(build);
        return handleResponse(response, LoginResult.class);

    }

    public RegisterResult register(RegisterRequest request) throws Exception {
        var build = buildRequest("POST", "/user", request);
        var response = sendRequest(build);
        return handleResponse(response, RegisterResult.class);

    }


    public void logout(LogoutRequest request) throws Exception {
        var build = buildRequest("DELETE", "/session", request);
        var response = sendRequest(build);
        handleResponse(response, null);
    }

    public CreateGamesResult createGame(CreateGameRequest request) throws Exception{
        var build = buildRequest("POST", "/game", request);
        var response = sendRequest(build);
        return handleResponse(response, CreateGamesResult.class);

    }

    public ListGamesResult listGames(ListGamesRequest request) throws Exception{
        var build = buildRequest("GET", "/game", request);
        var response = sendRequest(build);
        return handleResponse(response, ListGamesResult.class);

    }

    public void joinGame(JoinGameRequest request) throws Exception{
        var build = buildRequest("PUT", "/game", request);
        var response = sendRequest(build);
        handleResponse(response, null);


    }

    public void clear() throws Exception{
        var build = buildRequest("DELETE", "/db", null);
        sendRequest(build);

    }

    private HttpRequest buildRequest(String method, String path, Object body) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + path))
                .method(method, makeRequestBody(body));
        if(body != null) {
            request.setHeader("Content-Type", "application/json");
        }
        if(body instanceof CreateGameRequest createGameRequest){
            request.setHeader("authorization", createGameRequest.authToken());
        }
        else if(body instanceof LogoutRequest logoutRequest){
            request.setHeader("authorization", logoutRequest.authToken());
        }
        else if(body instanceof ListGamesRequest listGamesRequest){
            request.setHeader("authorization", listGamesRequest.authToken());
        }
        else if(body instanceof JoinGameRequest joinGameRequest){
            request.setHeader("authorization", joinGameRequest.authToken());
        }
        return request.build();
    }

    private BodyPublisher makeRequestBody(Object request){
        if (request != null){
            return BodyPublishers.ofString(new Gson().toJson(request));
        }
        else{
            return BodyPublishers.noBody();
        }
    }

    private HttpResponse<String> sendRequest(HttpRequest request) throws Exception{
        try{
            return client.send(request, BodyHandlers.ofString());
        } catch (Exception ex){
            throw new Exception(ex.getMessage());
        }

    }

    private <T> T handleResponse(HttpResponse<String> response, Class<T> responseClass) throws Exception{
        var status = response.statusCode();
        if (!isSuccessful(status)){
            var body = response.body();
            if (body != null){
                throw new Exception("Wrong response");
            }

            throw new Exception("other failure: " + status);

        }
        if (responseClass != null) {
            return new Gson().fromJson(response.body(), responseClass);
        }

        return null;
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }


}
