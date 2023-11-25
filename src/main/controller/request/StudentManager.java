package main.controller.request;

import main.controller.camp.CampManager;
import main.model.camp.Camp;
import main.model.camp.CampStatus;
import main.model.user.Student;
import main.model.user.StudentStatus;
import main.database.camp.CampDatabase;
import main.database.user.StudentDatabase;
import main.utils.exception.UserAlreadyExistsException;
import main.utils.exception.UserErrorException;

import java.util.List;

/**
 * Manages student-related operations
 */
public class StudentManager 
{
    
    /** 
     * Retrieves a list of all students
     * 
     * @return A list containing all student objects
     */
    public static List<Student> viewAllStudents()
    {
        return StudentDatabase.getInstance().getList();
    }
    
}
