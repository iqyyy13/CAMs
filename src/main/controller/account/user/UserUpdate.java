package main.controller.account.user;

import main.database.user.StaffDatabase;
import main.database.user.StudentDatabase;
import main.model.user.Staff;
import main.model.user.Student;
import main.model.user.User;
import main.utils.exception.UserErrorException;

/**
 * Handles the updating of user information in the respective databases
 */
public class UserUpdate 
{
    
    /** 
     * Updates the information of a student in the student database
     * 
     * @param student               The student to be updated
     * @throws UserErrorException   If an error occurs during the update process
     */
    private static void updateStudent(Student student) throws UserErrorException 
    {
        StudentDatabase.getInstance().update(student);
    }

    /**
     * Updates the information of a staff in the staff database
     * 
     * @param staff                 The staff to be updated
     * @throws UserErrorException   If an error occurs during the update process
     */
    private static void updateStaff(Staff staff) throws UserErrorException
    {
        StaffDatabase.getInstance().update(staff);
    }

    /**
     * Updates the information of a user based on the user's type. Supports
     * both students and staff
     * 
     * @param user                  The user to be updated
     * @throws UserErrorException   If an error occurs during the update process
     */
    public static void updateUser(User user) throws UserErrorException 
    {
        if(user instanceof Student student) 
        {
            updateStudent(student);
        }
        else if(user instanceof Staff staff)
        {
            updateStaff(staff);
        }
    }
}
