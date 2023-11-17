package main.controller.account.user;

import main.database.user.StaffDatabase;
import main.database.user.StudentDatabase;
import main.model.user.Staff;
import main.model.user.Student;
import main.model.user.User;
import main.utils.exception.UserErrorException;

public class UserUpdate 
{
    private static void updateStudent(Student student) throws UserErrorException 
    {
        StudentDatabase.getInstance().update(student);
    }

    private static void updateStaff(Staff staff) throws UserErrorException
    {
        StaffDatabase.getInstance().update(staff);
    }

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
