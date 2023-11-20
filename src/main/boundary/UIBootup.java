package main.boundary;

import main.boundary.welcome.Welcome;
import main.controller.account.AccountManager;
import main.controller.camp.CampManager;

/**
 * The UIBootup class manages the startup behaviour of the user interface
 */
public class UIBootup 
{
    /** 
     * Checks if the database is empty during the boot-up process
     * 
     * @return True if the database is empty, false otherwise
     */
    private static boolean onBootUp()
    {
        return CampManager.databaseIsEmpty();
    }

    /**
     * Starts the user interface, loading users and camps, and displaying the welcome message.
     */
    public static void start() 
    {
        AccountManager.loadUsers();
        if(onBootUp())
        {
            CampManager.loadCamps();
        }
        Welcome.welcome();
    }    
}