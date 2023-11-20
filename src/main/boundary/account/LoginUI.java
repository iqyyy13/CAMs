package main.boundary.account;

import main.boundary.mainpage.StaffMainPage;
import main.boundary.mainpage.StudentMainPage;
import main.controller.account.AccountManager;
import main.model.user.Student;
import main.model.user.User;
import main.model.user.UserType;
import main.utils.ui.ChangePage;
import main.utils.exception.PageBackException;
import main.utils.exception.PasswordIncorrectException;
import main.utils.exception.UserErrorException;
import java.util.Scanner;

/**
 * Handles the user login process, allowing users to log in based on their roles (STUDENT or STAFF)
 */
public class LoginUI 
{
    
    /** 
     * Initiates the user's login process, allowing users to log in based on their roles (STUDENT or STAFF)
     * 
     * @throws PageBackException if the user chooses to go back to the previous page during the login process
     */
    public static void login() throws PageBackException 
    {
        ChangePage.changePage();
        
        UserType role = AttributeGetter.getRole();
        String userID = AttributeGetter.getUserID();
        String password = AttributeGetter.getPassword();

        try
        {
            User user = AccountManager.login(role, userID, password);
            //System.out.println("Login Successful");
            //System.out.println("Press enter to continue.");
            //Scanner scanner = new Scanner(System.in);
            //scanner.nextLine();
            switch (role) 
            {
                case STUDENT -> StudentMainPage.studentMainPage(user);
                case STAFF -> StaffMainPage.staffMainPage(user);
            }
            return;
        } 
        catch (PasswordIncorrectException e)
        {
            System.out.println("Password incorrect.");
        }
        catch (UserErrorException e)
        {
            System.out.println("User not found.");
        }

        System.out.println("Enter [b] to go back, or any other key to try again.");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        
        if(choice.equals("b"))
        {
            throw new PageBackException();
        }
        else
        {
            System.out.println("Please try again.");
            login();
        }
    }
}
