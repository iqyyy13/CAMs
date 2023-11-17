package main.model.user;

import main.model.Model;

public interface User extends Model 
{
    String getID();

    String getUserName();

    String getPassword();

    void setPassword(String password);

    String getEmail();

    String getFaculty();

    UserType getUserType();

    /**
     * The function to check if username is equal to the user's username regardless of case
     *
     * @param username the username to be checked
     *
     * @return true if the username is equal to the user's username regardless of case
     */
    default boolean checkUsername(String username) 
    {
        return this.getUserName().equalsIgnoreCase(username);
    }
}
