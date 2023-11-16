package main.model.user;

import main.model.Model;

/**
 * A class that represents a user
 */
public interface User extends Model {
    /**
     * Gets the user ID of the user.
     *
     * @return the ID of the user.
     */
    String getID();

    /**
     * Gets the username of the user
     *
     * @return the name of the user
     */
    String getUserName();

    /**
     * Gets the hashed password of the user
     *
     * @return the hashed password of the user
     */
    String getPassword();

    /**
     * Sets the hashed password of the user
     *
     * @param hashedPassword the hashed password of the user
     */
    void setPassword(String password);

    /**
     * Gets the email of the user
     *
     * @return the email of the user
     */
    String getEmail();

    /**
     * Gets the faculty of the user
     *
     * @return the faculty of the user
     */
    String getFaculty();

    UserType getUserType();

    /**
     * The function to check if username is equal to the user's username regardless of case
     *
     * @param username the username to be checked
     *
     * @return true if the username is equal to the user's username regardless of case
     */
    default boolean checkUsername(String username) {
        return this.getUserName().equalsIgnoreCase(username);
    }
}
