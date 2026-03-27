package client;

import dataaccess.DataAccessException;
import org.junit.jupiter.api.*;
import server.Server;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:" + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeEach
    public void setup() throws DataAccessException {
        facade.clear();
    }


    @Test
    void loginSuccess() throws Exception{

    }

    @Test
    void loginfailure() throws Exception{

    }

    @Test
    void registerSuccess() throws Exception{

    }

    @Test
    void registerfailure() throws Exception{

    }

    @Test
    void logoutSuccess() throws Exception{

    }

    @Test
    void logoutFailure() throws Exception{

    }

    @Test
    void createGameSuccess() throws Exception{

    }

    @Test
    void createGamefailure() throws Exception{

    }

    @Test
    void listGamesSuccess() throws Exception{

    }

    @Test
    void listGamesfailure() throws Exception{

    }

    @Test
    void joinGameSuccess() throws Exception{

    }

    @Test
    void joinGameFailure() throws Exception{

    }

    @Test
    void clear() throws Exception{

    }



}
