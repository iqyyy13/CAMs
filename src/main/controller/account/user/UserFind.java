package main.controller.account.user;

import main.database.user.StaffDatabase;
import main.database.user.StudentDatabase;
import main.model.user.User;
import main.model.user.UserType;
import main.utils.exception.UserErrorException;

public class UserFind 
{
    
    /** 
     * @param userID
     * @return User
     * @throws UserErrorException
     */
    public static User findStudent(String userID) throws UserErrorException 
    {
        return StudentDatabase.getInstance().getByID(userID);
    }

    public static User findStaff(String userID) throws UserErrorException 
    {
        return StaffDatabase.getInstance().getByID(userID);
    }

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
