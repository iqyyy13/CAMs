package main.controller.account.user;

import main.model.user.User;
import main.model.user.UserType;
import main.repository.user.StudentDatabase;
import main.repository.user.StaffDatabase;
import main.utils.exception.UserErrorException;

/**
 * A class that provides a utility for finding users in the database.
 */
public class UserFind {
    /**
     * Finds the user with the specified ID.
     *
     * @param userID the ID of the user to be found
     * @return the user with the specified ID
     * @throws ModelNotFoundException if the user is not found
     */
    public static User findStudent(String userID) throws UserErrorException 
    {
        return StudentDatabase.getInstance().getByID(userID);
    }

    public static User findStaff(String userID) throws UserErrorException 
    {
        return StaffDatabase.getInstance().getByID(userID);
    }

    /**
     * Finds the user with the specified ID.
     *
     * @param userID   the ID of the user to be found
     * @param userType the type of the user to be found
     * @return the user with the specified ID
     * @throws ModelNotFoundException if the user is not found
     */
    public static User findUser(String userID, UserType userType) throws UserErrorException 
    {
        return switch (userType) 
        {
            case STUDENT -> findStudent(userID);
            case STAFF -> findStaff(userID);
            case CC -> findStudent(userID);
        };
    }
}
