package main.controller.account.user;

import main.database.user.StaffDatabase;
import main.database.user.StudentDatabase;
import main.model.user.Staff;
import main.model.user.Student;
import main.model.user.User;
import main.utils.exception.UserAlreadyExistsException;

/**
 * The UserAdder class provides a utility for adding users to the database.
 */
public class UserAdd {
    /**
     * Adds the specified user to the database.
     *
     * @param user the user to be added
     * @throws ModelAlreadyExistsException if the user already exists in the database
     */
    public static void addUser(User user) throws UserAlreadyExistsException 
    {
        if(user instanceof Student student) 
        {
            addStudent(student);
        }
        else if(user instanceof Staff staff)
        {
            addStaff(staff);
        }
    }

    private static void addStudent(Student student) throws UserAlreadyExistsException 
    {
        StudentDatabase.getInstance().add(student);
    }

    private static void addStaff(Staff staff) throws UserAlreadyExistsException 
    {
        StaffDatabase.getInstance().add(staff);
    }
}
