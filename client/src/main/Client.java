import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import chessImplementation.ChessGameImpl;
import chessImplementation.ChessPositionImpl;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import models.AuthToken;
import models.Game;
import requests.*;
import responses.*;
import typeAdapters.ChessGameAdapter;
import ui.EscapeSequences;

import com.google.gson.Gson;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;
import javax.websocket.*;
import java.net.URI;
import java.util.List;
import java.util.Scanner;

public class Client extends Endpoint {
    private boolean isLoggedIn = false;
    private boolean isStillUsing = true;
    private boolean isInGame = false;
    private AuthToken authToken;
    private String username;

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        Scanner scanner = new Scanner(System.in);
        Gson gson = new Gson();

        System.out.println(EscapeSequences.BLACK_KING + " Welcome to 240 chess. Type help to get started." + EscapeSequences.BLACK_KING);
        while (client.getUsingStatus()) {
            if (client.getInGameStatus()) {
                System.out.print(EscapeSequences.SET_TEXT_COLOR_LIGHT_GREY + "[IN GAME SESSION] >>> ");
                String userInput = scanner.nextLine();

                if (userInput.startsWith("redraw")) {
                    ChessGame newGame = new ChessGameImpl();
                    client.redrawBoardBlack(newGame);
                    System.out.print("\n");
                    client.redrawBoardWhite(newGame);
                } else if (userInput.startsWith("highlight")) {
                    System.out.println("Logic for highlighting valid moves goes here");
                } else if (userInput.startsWith("move")) {
                    System.out.println("Logic for making a move on the board goes here");
                } else if (userInput.startsWith("resign")) {
                    System.out.println("Logic for resigning a game goes here");
                } else if (userInput.startsWith("leave")) {
                    System.out.println("Logic for leaving a game goes here");
                    client.setInGameStatus(false);
                } else if (userInput.startsWith("help")) {
                    client.displayHelpMenuInGame();
                } else {
                    System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + "Invalid input. Type help + Enter to see possible commands" + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                }
            }
            else if (!client.getLoggedInStatus()) {
                System.out.print(EscapeSequences.SET_TEXT_COLOR_LIGHT_GREY + "[LOGGED OUT] >>> ");
                String userInput = scanner.nextLine();

                if (userInput.startsWith("register")) {
                    String[] commandArgs = userInput.split(" ");
                    if (commandArgs.length == 4) {
                        String username = commandArgs[1];
                        String password = commandArgs[2];
                        String email = commandArgs[3];

                        client.setUsername(username);
                        RegisterRequest registerRequest = new RegisterRequest(username, password, email);
                        RegisterResponse registerResponse = ServerFacade.handleClientRegister(registerRequest);
                        if (registerResponse.getMessage() != null) {
                            System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + registerResponse.getMessage() + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                        }
                        else {
                            client.setAuthToken(new AuthToken(registerResponse.getUsername(), registerResponse.getAuthToken()));
                            client.setLoggedInStatus(true);
                            System.out.println(EscapeSequences.SET_TEXT_COLOR_YELLOW + "Logged in as " + registerResponse.getUsername() + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                        }
                    } else {
                        System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + "Invalid register command. Command syntax is register <USERNAME> <PASSWORD> <EMAIL>" + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                    }
                } else if (userInput.startsWith("login")) {
                    String[] commandArgs = userInput.split(" ");
                    if (commandArgs.length == 3) {
                        String username = commandArgs[1];
                        String password = commandArgs[2];

                        client.setUsername(username);
                        LoginRequest loginRequest = new LoginRequest(username, password);
                        LoginResponse loginResponse = ServerFacade.handleClientLogin(loginRequest);
                        if (loginResponse.getMessage() != null) {
                            System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + loginResponse.getMessage() + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                        } else {
                            client.setAuthToken(new AuthToken(loginResponse.getUsername(), loginResponse.getAuthToken()));
                            client.setLoggedInStatus(true);
                            System.out.println(EscapeSequences.SET_TEXT_COLOR_YELLOW + "Logged in as " + loginResponse.getUsername() + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                        }
                    } else {
                        System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + "Invalid login command. Command syntax is login <USERNAME> <PASSWORD>" + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                    }
                } else if (userInput.startsWith("help")) {
                    client.displayHelpMenuLoggedOut();
                } else if (userInput.startsWith("quit")) {
                    client.setUsingStatus(false);
                } else {
                    System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + "Invalid input. Type help + Enter to see possible commands" + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                }
            } else {
                System.out.print(EscapeSequences.SET_TEXT_COLOR_LIGHT_GREY + "[LOGGED IN] >>> ");
                String userInput = scanner.nextLine();

                if (userInput.startsWith("create")) {
                    String[] commandArgs = userInput.split(" ");
                    if (commandArgs.length == 2) {
                        String gameName = commandArgs[1];

                        CreateGameRequest createGameRequest = new CreateGameRequest(gameName, client.getAuthToken().authToken());
                        CreateGameResponse createGameResponse = ServerFacade.handleClientCreateGame(createGameRequest);
                        if (createGameResponse.getMessage() != null) {
                            System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + createGameResponse.getMessage() + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                        } else {
                            System.out.println(EscapeSequences.SET_TEXT_COLOR_YELLOW + "Game ID: " + createGameResponse.getGameID() + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                        }
                    } else {
                        System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + "Invalid input. Type help + Enter to see possible commands" + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                    }
                } else if (userInput.startsWith("list")) {
                    ListGamesRequest listGamesRequest = new ListGamesRequest(client.getAuthToken().authToken());
                    ListGamesResponse listGamesResponse = ServerFacade.handleClientListGames(listGamesRequest);
                    if (listGamesResponse.getMessage() != null) {
                        System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + listGamesResponse.getMessage() + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                    } else {
                        List<Game> games = listGamesResponse.getGames();
                        if (!games.isEmpty()) {
                            for (var i = 0; i < games.size(); i++) {
                                System.out.print(EscapeSequences.SET_TEXT_COLOR_YELLOW + (i + 1) + ": ");
                                System.out.println(client.formatGameString(games.get(i)) + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                            }
                        } else {
                            System.out.println(EscapeSequences.SET_TEXT_COLOR_YELLOW + "No games found in the database" + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                        }
                    }
                } else if (userInput.startsWith("join")) {
                    String[] commandArgs = userInput.split(" ");
                    if (commandArgs.length == 3) {
                        try {
                            int gameID = Integer.parseInt(commandArgs[1]);
                            String color = commandArgs[2].toUpperCase();
                            ChessGame.TeamColor teamColor = ChessGame.TeamColor.WHITE;

                            if (!color.equals("WHITE") && !color.equals("BLACK")) {
                                System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + "Invalid Team Choice. Choices are WHITE or BLACK" + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                                continue;
                            } else if (color.equals("BLACK")) {
                                teamColor = ChessGame.TeamColor.BLACK;
                            }

                            JoinGameRequest joinGameRequest = new JoinGameRequest(teamColor, gameID, client.getAuthToken().authToken());
                            JoinGameResponse joinGameResponse = ServerFacade.handleClientJoinGame(joinGameRequest);
                            if (joinGameResponse.getMessage().startsWith("Error")) {
                                System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + joinGameResponse.getMessage() + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                            } else {
                                System.out.println(EscapeSequences.SET_TEXT_COLOR_YELLOW  + joinGameResponse.getMessage() + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                                client.setInGameStatus(true);

                                JoinPlayerCommand joinPlayerCommand = new JoinPlayerCommand(gameID, teamColor, UserGameCommand.CommandType.JOIN_PLAYER, client.getAuthToken().authToken());
                                String command = gson.toJson(joinPlayerCommand);
                                client.send(command);

//                                ChessGame newGame = new ChessGameImpl();
//                                client.redrawBoardBlack(newGame);
//                                System.out.print("\n");
//                                client.redrawBoardWhite(newGame);
                            }
                        } catch (NumberFormatException exception) {
                            System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + "Invalid input. Game ID must be a number");
                        }
                    } else {
                        System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + "Invalid input. Type help + Enter to see possible commands" + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                    }
                } else if (userInput.startsWith("observe")) {
                    String[] commandArgs = userInput.split(" ");
                    if (commandArgs.length == 2) {
                        int gameID = Integer.parseInt(commandArgs[1]);

                        JoinGameRequest joinGameRequest = new JoinGameRequest(null, gameID, client.getAuthToken().authToken());
                        JoinGameResponse joinGameResponse = ServerFacade.handleClientJoinGame(joinGameRequest);
                        if (joinGameResponse.getMessage().startsWith("Error")) {
                            System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + joinGameResponse.getMessage() + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                        } else {
                            System.out.println(EscapeSequences.SET_TEXT_COLOR_YELLOW + "Successfully joined as observer" + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                            client.setInGameStatus(true);
                            ChessGame newGame = new ChessGameImpl();
                            client.redrawBoardBlack(newGame);
                            System.out.print("\n");
                            client.redrawBoardWhite(newGame);
                        }
                    } else {
                        System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + "Invalid input. Type help + Enter to see possible commands" + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                    }
                } else if (userInput.startsWith("logout")) {
                    LogoutRequest logoutRequest = new LogoutRequest(client.authToken.authToken());
                    LogoutResponse logoutResponse = ServerFacade.handleClientLogout(logoutRequest);
                    if (logoutResponse.getMessage().startsWith("Error")) {
                        System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + logoutResponse.getMessage() + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                    } else {
                        client.setAuthToken(null);
                        client.setLoggedInStatus(false);
                        System.out.println(EscapeSequences.SET_TEXT_COLOR_YELLOW + logoutResponse.getMessage() + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                    }
                } else if (userInput.startsWith("help")) {
                    client.displayHelpMenuLoggedIn();
                } else if (userInput.startsWith("quit")) {
                    LogoutRequest logoutRequest = new LogoutRequest(client.authToken.authToken());
                    LogoutResponse logoutResponse = ServerFacade.handleClientLogout(logoutRequest);
                    if (logoutResponse.getMessage().startsWith("Error")) {
                        System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + logoutResponse.getMessage() + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                    } else {
                        client.setAuthToken(null);
                        client.setLoggedInStatus(false);
                        client.setUsingStatus(false);
                        System.out.println(EscapeSequences.SET_TEXT_COLOR_YELLOW + logoutResponse.getMessage() + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                    }
                } else {
                    System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + "Invalid input. Type help + Enter to see possible commands" + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                }
            }
        }

        System.out.println(EscapeSequences.SET_TEXT_COLOR_WHITE + "Goodbye!");
        System.exit(0);
    }

    public Session session;

    public Client() throws Exception {
        URI uri = new URI("ws://localhost:8080/connect");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {
                Gson gson = new Gson();
                System.out.println(message);

                JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
                ServerMessage.ServerMessageType serverMessageType = ServerMessage.ServerMessageType.valueOf(jsonObject.get("serverMessageType").getAsString());

                if (serverMessageType == ServerMessage.ServerMessageType.LOAD_GAME) {
                    JsonObject gameObject = jsonObject.getAsJsonObject("game");
                    JsonObject jsonChessGame = gameObject.get("chessGame").getAsJsonObject();
                    String whiteUsername = String.valueOf(gameObject.get("whiteUsername").getAsString());

                    var builder = new GsonBuilder();
                    builder.registerTypeAdapter(ChessGame.class, new ChessGameAdapter());

                    ChessGame chessGame = builder.create().fromJson(jsonChessGame, ChessGame.class);

                    if (whiteUsername.equals(getUsername())) {
                        redrawBoardWhite(chessGame);
                    } else {
                        redrawBoardBlack(chessGame);
                    }
                }
            }
        });
    }

    public void send(String msg) throws Exception {
        this.session.getBasicRemote().sendText(msg);
    }

    private boolean getUsingStatus() {
        return isStillUsing;
    }

    private void setUsingStatus(boolean status) {
        this.isStillUsing = status;
    }

    private boolean getLoggedInStatus() {
        return isLoggedIn;
    }

    private void setLoggedInStatus(boolean status) {
        this.isLoggedIn = status;
    }

    private AuthToken getAuthToken() {
        return authToken;
    }

    private void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    private boolean getInGameStatus() {
        return isInGame;
    }

    private void setInGameStatus(boolean status) {
        this.isInGame = status;
    }

    private void displayHelpMenuLoggedOut() {
        System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + "  register <USERNAME> <PASSWORD> <EMAIL>" + EscapeSequences.SET_TEXT_COLOR_MAGENTA + " - to create an account");
        System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + "  login <USERNAME> <PASSWORD>" + EscapeSequences.SET_TEXT_COLOR_MAGENTA + " - to login and play chess");
        System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + "  help" + EscapeSequences.SET_TEXT_COLOR_MAGENTA + " - to display all possible commands");
        System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + "  quit" + EscapeSequences.SET_TEXT_COLOR_MAGENTA + " - to exit the chess program");
    }

    private void displayHelpMenuLoggedIn() {
        System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + "  create <NAME>" + EscapeSequences.SET_TEXT_COLOR_MAGENTA + " - to create a game with the name specified");
        System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + "  list" + EscapeSequences.SET_TEXT_COLOR_MAGENTA + " - to list all chess games");
        System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + "  join <ID> [WHITE|BLACK]" + EscapeSequences.SET_TEXT_COLOR_MAGENTA + " - to join a chess game as the white or black team");
        System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + "  observe <ID>" + EscapeSequences.SET_TEXT_COLOR_MAGENTA + " - to join a chess game as an observer");
        System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + "  logout" + EscapeSequences.SET_TEXT_COLOR_MAGENTA + " - to logout when you are done");
        System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + "  help" + EscapeSequences.SET_TEXT_COLOR_MAGENTA + " - to display all possible commands");
        System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + "  quit" + EscapeSequences.SET_TEXT_COLOR_MAGENTA + " - to exit the chess program");
    }

    private void displayHelpMenuInGame() {
        System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + "  redraw" + EscapeSequences.SET_TEXT_COLOR_MAGENTA + " - to print out the chess board again");
        System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + "  highlight" + EscapeSequences.SET_TEXT_COLOR_MAGENTA + " - to highlight your team's legal moves");
        System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + "  move" + EscapeSequences.SET_TEXT_COLOR_MAGENTA + " - to move one of your pieces to another space");
        System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + "  resign" + EscapeSequences.SET_TEXT_COLOR_MAGENTA + " - to resign/forfeit the game");
        System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + "  leave" + EscapeSequences.SET_TEXT_COLOR_MAGENTA + " - to leave the game session");
        System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + "  help" + EscapeSequences.SET_TEXT_COLOR_MAGENTA + " - to display all possible commands");
    }

    private String formatGameString(Game game) {
        return "Game ID: " + game.gameID() + ", White Team Player: " + game.whiteUsername() + ", Black Team Player: " + game.blackUsername() + ", Game Name: " + game.gameName();
    }

    private void redrawBoardBlack(ChessGame game) {
        ChessBoard chessBoard = game.getBoard();
        StringBuilder boardString = new StringBuilder();

        boardString.append(EscapeSequences.SET_BG_COLOR_LIGHT_GREY + EscapeSequences.SET_TEXT_COLOR_BLACK + EscapeSequences.EMPTY + " h  g  f  e  d  c  b  a " + EscapeSequences.EMPTY + EscapeSequences.RESET_BG_COLOR);
        boardString.append("\n");
        for (var i = 1; i <= 8; i++) {
            boardString.append(EscapeSequences.SET_BG_COLOR_LIGHT_GREY + " " + i + " " + EscapeSequences.RESET_BG_COLOR);
            for (var j = 1; j <= 8; j++) {
                ChessPosition chessPosition = new ChessPositionImpl(i, j);
                ChessPiece chessPiece = chessBoard.getPiece(chessPosition);

                if (i % 2 != 0) {
                    if (j % 2 != 0) {
                        boardString.append(EscapeSequences.SET_BG_COLOR_WHITE);
                    } else {
                        boardString.append(EscapeSequences.SET_BG_COLOR_BLACK);
                    }
                } else {
                    if (j % 2 != 0) {
                        boardString.append(EscapeSequences.SET_BG_COLOR_BLACK);
                    } else {
                        boardString.append(EscapeSequences.SET_BG_COLOR_WHITE);
                    }
                }

                if (chessPiece == null) {
                    boardString.append(EscapeSequences.EMPTY);
                } else {
                    if (chessPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                        boardString.append(EscapeSequences.SET_TEXT_COLOR_BLUE);
                        switch (chessPiece.getPieceType()) {
                            case ROOK -> boardString.append(EscapeSequences.WHITE_ROOK);
                            case KNIGHT -> boardString.append(EscapeSequences.WHITE_KNIGHT);
                            case BISHOP -> boardString.append(EscapeSequences.WHITE_BISHOP);
                            case KING -> boardString.append(EscapeSequences.WHITE_KING);
                            case QUEEN -> boardString.append(EscapeSequences.WHITE_QUEEN);
                            default -> boardString.append(EscapeSequences.WHITE_PAWN);
                        }
                    } else {
                        boardString.append(EscapeSequences.SET_TEXT_COLOR_RED);
                        switch (chessPiece.getPieceType()) {
                            case ROOK -> boardString.append(EscapeSequences.BLACK_ROOK);
                            case KNIGHT -> boardString.append(EscapeSequences.BLACK_KNIGHT);
                            case BISHOP -> boardString.append(EscapeSequences.BLACK_BISHOP);
                            case KING -> boardString.append(EscapeSequences.BLACK_KING);
                            case QUEEN -> boardString.append(EscapeSequences.BLACK_QUEEN);
                            default -> boardString.append(EscapeSequences.WHITE_PAWN);
                        }
                    }
                }

                boardString.append(EscapeSequences.SET_TEXT_COLOR_BLACK);
            }
            boardString.append(EscapeSequences.SET_BG_COLOR_LIGHT_GREY + " " + i + " " + EscapeSequences.RESET_BG_COLOR);
            boardString.append("\n");
        }

        boardString.append(EscapeSequences.SET_BG_COLOR_LIGHT_GREY + EscapeSequences.SET_TEXT_COLOR_BLACK + EscapeSequences.EMPTY + " h  g  f  e  d  c  b  a " + EscapeSequences.EMPTY + EscapeSequences.RESET_BG_COLOR);
        System.out.println(boardString);
    }

    private void redrawBoardWhite(ChessGame game) {
        ChessBoard chessBoard = game.getBoard();
        StringBuilder boardString = new StringBuilder();

        boardString.append(EscapeSequences.SET_BG_COLOR_LIGHT_GREY + EscapeSequences.SET_TEXT_COLOR_BLACK + EscapeSequences.EMPTY + " a  b  c  d  e  f  g  h " + EscapeSequences.EMPTY + EscapeSequences.RESET_BG_COLOR);
        boardString.append("\n");
        for (var i = 8; i >= 1; i--) {
            boardString.append(EscapeSequences.SET_BG_COLOR_LIGHT_GREY + " " + i + " " + EscapeSequences.RESET_BG_COLOR);
            for (var j = 8; j >= 1; j--) {
                ChessPosition chessPosition = new ChessPositionImpl(i, j);
                ChessPiece chessPiece = chessBoard.getPiece(chessPosition);

                if (i % 2 != 0) {
                    if (j % 2 != 0) {
                        boardString.append(EscapeSequences.SET_BG_COLOR_WHITE);
                    } else {
                        boardString.append(EscapeSequences.SET_BG_COLOR_BLACK);
                    }
                } else {
                    if (j % 2 != 0) {
                        boardString.append(EscapeSequences.SET_BG_COLOR_BLACK);
                    } else {
                        boardString.append(EscapeSequences.SET_BG_COLOR_WHITE);
                    }
                }

                if (chessPiece == null) {
                    boardString.append(EscapeSequences.EMPTY);
                } else {
                    if (chessPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                        boardString.append(EscapeSequences.SET_TEXT_COLOR_BLUE);
                        switch (chessPiece.getPieceType()) {
                            case ROOK -> boardString.append(EscapeSequences.WHITE_ROOK);
                            case KNIGHT -> boardString.append(EscapeSequences.WHITE_KNIGHT);
                            case BISHOP -> boardString.append(EscapeSequences.WHITE_BISHOP);
                            case KING -> boardString.append(EscapeSequences.WHITE_KING);
                            case QUEEN -> boardString.append(EscapeSequences.WHITE_QUEEN);
                            default -> boardString.append(EscapeSequences.WHITE_PAWN);
                        }
                    } else {
                        boardString.append(EscapeSequences.SET_TEXT_COLOR_RED);
                        switch (chessPiece.getPieceType()) {
                            case ROOK -> boardString.append(EscapeSequences.BLACK_ROOK);
                            case KNIGHT -> boardString.append(EscapeSequences.BLACK_KNIGHT);
                            case BISHOP -> boardString.append(EscapeSequences.BLACK_BISHOP);
                            case KING -> boardString.append(EscapeSequences.BLACK_KING);
                            case QUEEN -> boardString.append(EscapeSequences.BLACK_QUEEN);
                            default -> boardString.append(EscapeSequences.WHITE_PAWN);
                        }
                    }
                }

                boardString.append(EscapeSequences.SET_TEXT_COLOR_BLACK);
            }
            boardString.append(EscapeSequences.SET_BG_COLOR_LIGHT_GREY + " " + i + " " + EscapeSequences.RESET_BG_COLOR);
            boardString.append("\n");
        }

        boardString.append(EscapeSequences.SET_BG_COLOR_LIGHT_GREY + EscapeSequences.SET_TEXT_COLOR_BLACK + EscapeSequences.EMPTY + " a  b  c  d  e  f  g  h " + EscapeSequences.EMPTY + EscapeSequences.RESET_BG_COLOR);
        System.out.println(boardString);
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
}
