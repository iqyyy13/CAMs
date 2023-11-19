package main.controller.account.password;

import main.model.user.User;
import main.utils.exception.PasswordIncorrectException;

/**
 * Manages user passwords, providing methods to check and change passwords.
 */
public class PasswordManager 
{
    /**
     * Checks if the provided password is correct for the user
     *
     * @param user      The user whose password is to be checked
     * @param password  The password to be checked
     * @return          True if the password is correct, false otherwise
     */
    public static boolean checkPassword(User user, String password) 
    {
        return user.getPassword().equals(password);
    }

    /**
     * Changes the password of the user
     *
     * @param user                          The user whose password is to be changed
     * @param oldPassword                   The old password
     * @param newPassword                   The new password
     * @throws PasswordIncorrectException   If the old password is incorrect
     */
    public static void changePassword(User user, String oldPassword, String newPassword) throws PasswordIncorrectException 
    {
        if (checkPassword(user, oldPassword)) 
        {
            user.setPassword(newPassword);
        } 
        else 
        {
            throw new PasswordIncorrectException();
        }
    }
}
