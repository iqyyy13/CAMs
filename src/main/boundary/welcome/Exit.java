package main.boundary.welcome;

import main.utils.ui.ChangePage;

/**
 * The exit class provides a user interace for exitting the system
 * It displays an exit page and terminates the application when called
 */
public class Exit 
{
    /**
     * Displays an exit page and terminates the application
     */
    public static void exit() 
    {
        ChangePage.changePage();
        System.out.println("Thank you for using our system!");
        System.exit(0);
    }
}
