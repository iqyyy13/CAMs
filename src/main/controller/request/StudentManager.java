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

public class StudentManager 
{
    
    /** 
     * @return List<Student>
     */
    public static List<Student> viewAllStudents()
    {
        return StudentDatabase.getInstance().getList();
    }
    
}
