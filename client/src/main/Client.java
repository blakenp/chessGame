import com.google.gson.Gson;
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

            if (userInput.startsWith("register")) {

            } else if (userInput.startsWith("login")) {

            } else if (userInput.equals("help")) {
                System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + "register <USERNAME> <PASSWORD> <EMAIL>" + EscapeSequences.RESET_TEXT_COLOR + " - to create an account");
                System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + "login <USERNAME> <PASSWORD>" + EscapeSequences.RESET_TEXT_COLOR + " - to login and play chess");
                System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + "help" + EscapeSequences.RESET_TEXT_COLOR + " - to display all possible commands");
                System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + "quit" + EscapeSequences.RESET_TEXT_COLOR + " - to exit the chess program");
            } else if (userInput.equals("quit")) {
                client.setUsingStatus(false);
            }
        }

        System.out.println("Goodbye!");
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

}