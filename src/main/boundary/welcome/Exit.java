package main.boundary.welcome;

import main.utils.ui.ChangePage;

/**
 * This class provides a UI for the user to exit the system.
 */
public class Exit {
    /**
     * Displays an exit page.
     */
    public static void exit() {
        ChangePage.changePage();
        System.out.println("Thank you for using our system!");
        System.exit(0);
    }
}
