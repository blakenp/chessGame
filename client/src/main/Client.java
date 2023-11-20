import com.google.gson.Gson;
import requests.LoginRequest;
import requests.RegisterRequest;
import ui.EscapeSequences;

import java.util.Scanner;

public class Client {
    private boolean isLoggedIn = false;
    private boolean isStillUsing = true;

    public static void main(String[] args) {
        Client client = new Client();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to 240 chess. Type help to get started.");
        while (client.getUsingStatus()) {
            String userInput = scanner.nextLine();

            if (!client.getLoggedInStatus()) {
                if (userInput.startsWith("register")) {
                    String[] commandArgs = userInput.split(" ");
                    if (commandArgs.length == 4) {
                        String username = commandArgs[1];
                        String password = commandArgs[2];
                        String email = commandArgs[3];

                        RegisterRequest registerRequest = new RegisterRequest(username, password, email);
                    } else {
                        System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + "Invalid register command. Command syntax is register <USERNAME> <PASSWORD> <EMAIL>" + EscapeSequences.SET_TEXT_COLOR_WHITE);
                    }
                } else if (userInput.startsWith("login")) {
                    String[] commandArgs = userInput.split(" ");
                    if (commandArgs.length == 3) {
                        String username = commandArgs[1];
                        String password = commandArgs[2];

                        LoginRequest loginRequest = new LoginRequest(username, password);
                        client.setLoggedInStatus(true);
                    } else {
                        System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + "Invalid login command. Command syntax is login <USERNAME> <PASSWORD>" + EscapeSequences.SET_TEXT_COLOR_WHITE);
                    }
                } else if (userInput.equals("help")) {
                    client.displayHelpMenu();
                } else if (userInput.equals("quit")) {
                    client.setUsingStatus(false);
                } else {
                    System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + "Invalid input. Type help + Enter to see possible commands" + EscapeSequences.SET_TEXT_COLOR_WHITE);
                }
            }
        }

        System.out.println("Goodbye!");
        System.exit(0);
    }

    private boolean getUsingStatus() {
        return this.isStillUsing;
    }
    private void setUsingStatus(boolean status) {
        this.isStillUsing = status;
    }

    private boolean getLoggedInStatus() {
        return this.isLoggedIn;
    }

    private void setLoggedInStatus(boolean status) {
        this.isLoggedIn = status;
    }

    private void displayHelpMenu() {
        System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + "register <USERNAME> <PASSWORD> <EMAIL>" + EscapeSequences.SET_TEXT_COLOR_WHITE + " - to create an account");
        System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + "login <USERNAME> <PASSWORD>" + EscapeSequences.SET_TEXT_COLOR_WHITE + " - to login and play chess");
        System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + "help" + EscapeSequences.SET_TEXT_COLOR_WHITE + " - to display all possible commands");
        System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + "quit" + EscapeSequences.SET_TEXT_COLOR_WHITE + " - to exit the chess program");
    }

}
