package main.boundary.welcome;

import main.boundary.account.LoginUI;
import main.utils.ui.ChangePage;
import main.utils.iocontrol.IntGetter;
import main.utils.exception.PageBackException;

/**
 * The welcome class provides a welcome screen for the Camp Application and Management System (CAMs)
 * Users are prompted to choose between logging in or exiting the application
 */
public class Welcome 
{
    /**
     * Displays the welcome screen and handles user input for login or exit options
     */
    public static void welcome() 
    {
        ChangePage.changePage();
        System.out.println("Welcome to the Camp Application and Management System (CAMSs)");
        System.out.println("Please enter your choice to continue.");
        System.out.println("\t1. Login");
        System.out.println("\t2. Exit");
        System.out.print("Your choice (1-2): ");

        try
        {
            while (true) 
            {
                int choice = IntGetter.readInt();
                switch (choice) 
                {
                    case 1 -> LoginUI.login();
                    case 2 -> Exit.exit();
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (PageBackException e) {
            welcome();
        }
    }
}
