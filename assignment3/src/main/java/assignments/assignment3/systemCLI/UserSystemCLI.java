package assignments.assignment3.systemCLI;

import java.util.Scanner;

import assignments.assignment3.User;

public abstract class UserSystemCLI {
    User userLoggedIn = assignments.assignment3.MainMenu.getUserLoggedIn();
    Scanner input = new Scanner(System.in);
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
