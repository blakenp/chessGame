package server;

import spark.Spark;
import handlers.*;

/**
 * Object representation of the chess game server that handles client requests and helps
 * store game data and logic
 */
public class Server {

    public static void main(String[] args) {
        new Server().run();
    }

    private void run() {
        // Specify the port you want the server to listen on
        Spark.port(8080);

        // Register a directory for hosting static files
        Spark.externalStaticFileLocation("web");

        createRoutes();
    }

    private static void createRoutes() {
        Spark.delete("/db", (request, response) ->
                TestingHandler.handleClear()
        );

        Spark.post("/user", (request, response) ->
                UserHandler.handleRegisterUser(request, response)
        );

        Spark.post("/session", (request, response) ->
                AuthHandler.handleLogin(request, response)
        );

        Spark.delete("/session", (request, response) ->
                AuthHandler.handleLogout(request, response)
        );

        Spark.post("/game", (request, response) ->
                GameHandler.handleCreateGame(request, response)
        );

        Spark.put("/game", (request, response) ->
                GameHandler.handleJoinGame(request, response)
        );

        Spark.get("/game", (request, response) ->
                GameHandler.handleListGames(request, response)
        );
    }
}
