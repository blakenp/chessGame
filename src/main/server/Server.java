package server;

import spark.Spark;
import handlers.*;

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

        // Register handlers for each endpoint using the method reference syntax
//        Spark.post("/name/:name", this::addName);
//        Spark.get("/name", this::listNames);
//        Spark.delete("/name/:name", this::deleteName);
    }

    private static void createRoutes() {
        Spark.get("/hello", (req, res) -> "Hello BYU!");
    }

//    private Object addName(Request req, Response res) {
//        names.add(req.params(":name"));
//        return listNames(req, res);
//    }
//
//    private Object listNames(Request req, Response res) {
//        res.type("application/json");
//        return new Gson().toJson(Map.of("name", names));
//    }
//
//    private Object deleteName(Request req, Response res) {
//        names.remove(req.params(":name"));
//        return listNames(req, res);
//    }
}
