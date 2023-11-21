import chess.ChessGame;
import com.google.gson.Gson;
import models.AuthToken;
import requests.*;
import responses.ListGamesResponse;
import responses.LogoutResponse;
import responses.RegisterResponse;
import ui.EscapeSequences;

import java.util.Scanner;

public class Client {
    private boolean isLoggedIn = false;
    private boolean isStillUsing = true;
    private AuthToken authToken;

    public static void main(String[] args) {
        Client client = new Client();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to 240 chess. Type help to get started.");
        while (client.getUsingStatus()) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_LIGHT_GREY + "[LOGGED OUT] >>> ");
            String userInput = scanner.nextLine();

            if (!client.getLoggedInStatus()) {
                if (userInput.startsWith("register")) {
                    String[] commandArgs = userInput.split(" ");
                    if (commandArgs.length == 4) {
                        String username = commandArgs[1];
                        String password = commandArgs[2];
                        String email = commandArgs[3];

                        RegisterRequest registerRequest = new RegisterRequest(username, password, email);
                        RegisterResponse registerResponse = ServerFacade.handleClientRegister(registerRequest);
                        if (registerResponse.getMessage() != null) {
                            System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + "Error: an error occurred with the server or that username is already taken" + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
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
                        client.setLoggedInStatus(true);
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
                if (userInput.startsWith("create")) {
                    String[] commandArgs = userInput.split(" ");
                    if (commandArgs.length == 2) {
                        String gameName = commandArgs[1];

                        CreateGameRequest createGameRequest = new CreateGameRequest(gameName, client.getAuthToken().authToken());
                    } else {
                        System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + "Invalid input. Type help + Enter to see possible commands" + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                    }
                } else if (userInput.equals("list")) {
                    ListGamesRequest listGamesRequest = new ListGamesRequest(client.getAuthToken().authToken());
                } else if (userInput.startsWith("join")) {
                    String[] commandArgs = userInput.split(" ");
                    if (commandArgs.length == 3) {
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
                    } else {
                        System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + "Invalid input. Type help + Enter to see possible commands" + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                    }
                } else if (userInput.startsWith("observe")) {
                    String[] commandArgs = userInput.split(" ");
                    if (commandArgs.length == 2) {
                        int gameID = Integer.parseInt(commandArgs[1]);
                        
                        JoinGameRequest joinGameRequest = new JoinGameRequest(null, gameID, client.getAuthToken().authToken());
                    } else {
                        System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + "Invalid input. Type help + Enter to see possible commands" + EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                    }
                } else if (userInput.equals("logout")) {
                    LogoutResponse logoutResponse = new LogoutResponse(client.authToken.authToken());
                    client.setLoggedInStatus(false);
                } else if (userInput.equals("help")) {
                    
                } else if (userInput.equals("quit")) {
                    client.setUsingStatus(false);
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

}
