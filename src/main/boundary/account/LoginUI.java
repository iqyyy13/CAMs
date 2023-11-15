package main.boundary.account;

import main.controller.account.AccountManager;
import main.model.user.User;
import main.utils.ui.ChangePage;
import main.utils.exception.PageBackException;
import main.utils.exception.PasswordIncorrectException;
import main.utils.exception.UserErrorException;
import java.util.Scanner;

public class LoginUI 
{
    public static void login() throws PageBackException 
    {
        ChangePage.changePage();
        String userID = AttributeGetter.getUserID();
        String password = AttributeGetter.getPassword();

        try
        {
            User user = AccountManager.login(userID,password);
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
