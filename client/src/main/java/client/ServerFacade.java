package client;


import com.google.gson.Gson;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import model.*;
import service.*;

import java.net.*;
import java.net.http.*;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

public class ServerFacade {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }

    public LoginResult login(LoginRequest request) throws DataAccessException {
        var build = buildRequest("POST", "/session", request);
        var response = sendRequest(build);
        return handleResponse(response, LoginResult.class);

    }

    public RegisterResult register(RegisterRequest request) throws DataAccessException {
        var build = buildRequest("POST", "/user", request);
        var response = sendRequest(build);
        return handleResponse(response, RegisterResult.class);

    }


    public void logout(LogoutRequest request) throws DataAccessException {
        var build = buildRequest("DELETE", "/session", request);
        sendRequest(build);
    }

    public CreateGamesResult createGame(CreateGameRequest request) throws DataAccessException{
        var build = buildRequest("POST", "/game", request);
        var response = sendRequest(build);
        return handleResponse(response, CreateGamesResult.class);

    }

    public ListGamesResult listGames(ListGamesRequest request) throws DataAccessException{
        var build = buildRequest("GET", "/game", request);
        var response = sendRequest(build);
        return handleResponse(response, ListGamesResult.class);

    }

    public void joinGame(JoinGameRequest request) throws DataAccessException{
        var build = buildRequest("PUT", "/game", request);
        sendRequest(build);


    }

    public void clear() throws DataAccessException{
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

    private HttpResponse<String> sendRequest(HttpRequest request) throws DataAccessException{
        try{
            return client.send(request, BodyHandlers.ofString());
        } catch (Exception ex){
            throw new DataAccessException(ex.getMessage());
        }

    }

    private <T> T handleResponse(HttpResponse<String> response, Class<T> responseClass) throws DataAccessException{
        var status = response.statusCode();
        if (!isSuccessful(status)){
            var body = response.body();
            if (body != null){
                throw new DataAccessException("Wrong response");
            }

            throw new DataAccessException("other failure: " + status);

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
