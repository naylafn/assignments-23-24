package assignments.assignment3.systemCLI;

import java.util.Scanner;
import assignments.assignment3.User;

public abstract class UserSystemCLI {
    protected Scanner input;
    public void run() {
        boolean isLoggedIn = true;
        System.out.println("OALAH JANCUK");
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
