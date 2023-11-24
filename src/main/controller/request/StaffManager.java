package main.controller.request;

import main.database.camp.CampDatabase;
import main.database.user.StaffDatabase;
import main.model.camp.CampStatus;
import main.model.user.Staff;
import main.utils.exception.UserAlreadyExistsException;
import main.utils.parameters.EmptyID;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StaffManager 
{

    public static int MAX_NUM_OF_STUDENTS_PER_STAFF = 30;

    public static int getNumOfStudents(String staffID) 
    {
        return CampDatabase.getInstance().findByRules(
                camp -> camp.getStaffID().equalsIgnoreCase(staffID),
                camp -> camp.getStatus() == CampStatus.ALLOCATED ||
                        camp.getStatus() == CampStatus.AVAILABLE
        ).size();
    }

    public static List<Staff> getAllUnavailableStaff() 
    {
        List<Staff> staffs = new ArrayList<>();
        for (Staff staff : StaffDatabase.getInstance()) 
        {
            if (getNumOfStudents(staff.getID()) >= MAX_NUM_OF_STUDENTS_PER_STAFF) 
            {
                staffs.add(staff);
            }
        }
        return staffs;
    }
}
