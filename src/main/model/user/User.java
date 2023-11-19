package main.model.user;

import main.model.Model;

/**
 * A class that represents a user
 */
public interface User extends Model 
{
    /**
     * Gets the user ID of a user
     * @return the ID of the user
     */
    String getID();

    /**
     * Gets the username of a user
     * @return the username of a user
     */
    String getUserName();

    /**
     * Gets the password of a user
     * @return the password of a user
     */
    String getPassword();

    /**
     * Sets the password of the user
     * @param password the password of the user
     */
    void setPassword(String password);

    /**
     * Gets the email of a user
     * @return the email of the user
     */
    String getEmail();

    /**
     * Gets the faculty of a user
     * @return the faculty of the user
     */
    String getFaculty();

    /**
     * Gets the usertype of a user
     * @return the usertype of the user
     */
    UserType getUserType();

    /**
     * Checks if username is equal to the user's username regardless of uppercase or lowercase
     *
     * @param username the username to be checked
     *
     * @return true if the username is equal to the user's username regardless of uppercase or lowercase
     */
    default boolean checkUsername(String username) 
    {
        return this.getUserName().equalsIgnoreCase(username);
    }
}
