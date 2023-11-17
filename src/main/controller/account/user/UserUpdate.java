package main.controller.account.user;

import main.model.user.Staff;
import main.model.user.Student;
import main.model.user.User;
import main.repository.user.StudentRepository;
import main.repository.user.StaffRepository;
import main.utils.exception.UserErrorException;

/**
 * The UserUpdater class provides a utility for updating users in the database.
 */
public class UserUpdate {
    /**
     * Updates the specified user in the database.
     *
     * @param student the user to be updated
     * @throws UserErrorException if the user is not found in the database
     */
    private static void updateStudent(Student student) throws UserErrorException 
    {
        StudentRepository.getInstance().update(student);
    }

    private static void updateStaff(Staff staff) throws UserErrorException
    {
        StaffRepository.getInstance().update(staff);
    }

    /**
     * Updates the specified user in the database.
     *
     * @param user the user to be updated
     * @throws ModelNotFoundException if the user is not found in the database
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
