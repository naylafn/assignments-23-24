package assignments.assignment3.systemCLI;

import java.util.Scanner;

import assignments.assignment2.MainMenu;
import assignments.assignment3.User;

public abstract class UserSystemCLI {
    private static User userLoggedIn = assignments.assignment3.MainMenu.getUserLoggedIn();
    protected Scanner input;
    public void run() {
        boolean isLoggedIn = true;
        while (isLoggedIn) {
            displayMenu();
            int command = input.nextInt();
            input.nextLine();
            isLoggedIn = handleMenu(command);
        }
    }
    public abstract void displayMenu();
    public abstract boolean handleMenu(int command);
}
