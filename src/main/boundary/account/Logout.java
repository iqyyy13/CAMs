package main.boundary.account;

import main.boundary.welcome.Welcome;

/**
 * Handles the process of logging out a user from the system
 */
public class Logout 
{
    /**
     * Logs out the user and returns to the welcome page.
     */
    public static void logout() 
    {
        Welcome.welcome();
        System.exit(0);
    }
}
