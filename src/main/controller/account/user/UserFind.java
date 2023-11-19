package main.controller.account.user;

import main.database.user.StaffDatabase;
import main.database.user.StudentDatabase;
import main.model.user.User;
import main.model.user.UserType;
import main.utils.exception.UserErrorException;

/**
 * A utility class for finding user information in the respective databases
 */
public class UserFind 
{
    
    /** 
     * Finds a student based on the provided user ID
     * 
     * @param userID                The ID of the student to be find
     * @return                      The user (student) found with the given ID
     * @throws UserErrorException   If an error occurs during the user retrieval process
     */
    public static User findStudent(String userID) throws UserErrorException 
    {
        return StudentDatabase.getInstance().getByID(userID);
    }

    /**
     * Finds a staff based on the provided user ID
     * 
     * @param userID                The ID of the staff to be find
     * @return                      The user (staff) found with the given ID
     * @throws UserErrorException   If an error occurs during the user retrieval process
     */
    public static User findStaff(String userID) throws UserErrorException 
    {
        return StaffDatabase.getInstance().getByID(userID);
    }

    /**
     * Finds a user (either student or staff) based on the provided user ID and user type.
     * 
     * @param userID                The ID of the user to find
     * @param userType              The type of the user (STUDENT or STAFF)
     * @return                      The user found with the given ID and type
     * @throws UserErrorException   If an error occurs during the user retrieval process
     */
    public static User findUser(String userID, UserType userType) throws UserErrorException 
    {
        return switch (userType) 
        {
            case STUDENT -> findStudent(userID);
            case STAFF -> findStaff(userID);
        };
    }
}
