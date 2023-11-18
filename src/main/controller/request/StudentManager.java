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
    public static String registerStudent(String campID, String studentID) throws UserErrorException
    {
        Camp camp = CampDatabase.getInstance().getByID(campID);
        Student student = StudentDatabase.getInstance().getByID(studentID);

        

    }
    
}
