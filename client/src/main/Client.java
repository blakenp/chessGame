import chess.ChessGame;
import models.AuthToken;
import models.Game;
import requests.*;
import responses.*;
import ui.EscapeSequences;

import java.util.List;
import java.util.Scanner;

public class Client {
    private boolean isLoggedIn = false;
    private boolean isStillUsing = true;
    private boolean isInGame = false;
    private AuthToken authToken;

    public static void main(String[] args) {
        Client client = new Client();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to 240 chess. Type help to get started.");
        while (client.getUsingStatus()) {
            if (client.getInGameStatus()) {
                System.out.print(EscapeSequences.SET_TEXT_COLOR_LIGHT_GREY + "[IN GAME SESSION] >>> ");
                String userInput = scanner.nextLine();

                if (userInput.equals("redraw")) {
                    System.out.println("Call game toString() function here");
                } else if (userInput.equals("highlight")) {
                    System.out.println("Logic for highlighting valid moves goes here");
                } else if (userInput.equals("move")) {
                    System.out.println("Logic for making a move on the board goes here");
                } else if (userInput.startsWith("resign")) {
                    System.out.println("Logic for resigning a game goes here");
                } else if (userInput.equals("leave")) {
                    System.out.println("Logic for leaving a game goes here");
                    client.setInGameStatus(false);
                } else if (userInput.equals("help")) {
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
                } else if (userInput.equals("help")) {
                    client.displayHelpMenuLoggedOut();
                } else if (userInput.equals("quit")) {
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
                            System.out.println(EscapeSequences.SET_TEXT_COLOR_YELLOW + "Successfully created game " + gameName + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                        }
                    } else {
                        System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + "Invalid input. Type help + Enter to see possible commands" + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                    }
                } else if (userInput.equals("list")) {
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
                        }
                    } else {
                        System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + "Invalid input. Type help + Enter to see possible commands" + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                    }
                } else if (userInput.equals("logout")) {
                    LogoutRequest logoutRequest = new LogoutRequest(client.authToken.authToken());
                    LogoutResponse logoutResponse = ServerFacade.handleClientLogout(logoutRequest);
                    if (logoutResponse.getMessage().startsWith("Error")) {
                        System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + logoutResponse.getMessage() + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                    } else {
                        client.setAuthToken(null);
                        client.setLoggedInStatus(false);
                        System.out.println(EscapeSequences.SET_TEXT_COLOR_YELLOW + logoutResponse.getMessage() + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                    }
                } else if (userInput.equals("help")) {
                    client.displayHelpMenuLoggedIn();
                } else if (userInput.equals("quit")) {
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

}
