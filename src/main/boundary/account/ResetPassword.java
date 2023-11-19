package main.boundary.account;

import main.controller.account.AccountManager;
import main.model.user.UserType;
import main.utils.exception.PasswordIncorrectException;
import main.utils.exception.UserErrorException;
import main.utils.exception.PageBackException;
import main.utils.ui.ChangePage;
import main.utils.ui.UserTypeGetter;

import java.util.Scanner;

public class ResetPassword 
{
    
    /** 
     * @param userType
     * @param userID
     * @throws PageBackException
     */
    public static void askToRetry(UserType userType, String userID) throws PageBackException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press Enter to try again or [b] to go back.");
        String choice = scanner.nextLine();
        if (choice.equals("b")) {
            throw new PageBackException();
        } else {
            System.out.println("Please try again.");
            changePassword(userType, userID);
        }
    }

    public static void changePassword(UserType userType, String userID) throws PageBackException {
        ChangePage.changePage();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter your old password: ");
        String oldPassword = AttributeGetter.getPassword1();

        try {
            AccountManager.login(userType, userID, oldPassword);
        } catch (UserErrorException e) {
            throw new RuntimeException(e);
        } catch (PasswordIncorrectException e) {
            System.out.println("Password incorrect.");
            askToRetry(userType, userID);
        }

        String newPassword;
        String newPasswordAgain;

        do {
            // read the new password
            System.out.print("Please enter a new password: ");
            newPassword = AttributeGetter.getPassword1();
            // read the new password again
            System.out.print("Please enter a new password again: ");
            newPasswordAgain = AttributeGetter.getPassword1();
            // if the new password is not the same as the new password again, ask the user to enter again

            if (!newPassword.equals(newPasswordAgain)) {
                System.out.println("The two passwords are not the same.");
                askToRetry(userType, userID);
            } else if (newPassword.equals(oldPassword)) {
                System.out.println("New password cannot be the same as the old password.");
                askToRetry(userType, userID);
            }
        } while (!newPassword.equals(newPasswordAgain));

        if (newPassword.length() < 8) {
            System.out.println("Password must be more than 8 characters.");
            askToRetry(userType, userID);
        }

        try 
        {
            AccountManager.changePassword(userType, userID, oldPassword, newPassword);

            System.out.println("Password changed successfully.");

            System.out.println("Press [Enter] to go back to the main page.");
            scanner.nextLine();
            throw new PageBackException();
        } catch (PasswordIncorrectException | UserErrorException e) {
            throw new RuntimeException(e);
        }
    }
}
