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

/**
 * Manages staff related operations
 */
public class StaffManager 
{

    /**
     * Maximum number of students per staff
     */
    public static int MAX_NUM_OF_STUDENTS_PER_STAFF = 30;

    /**
     * Gets the number of students assigned to a staff
     * 
     * @param staffID   The ID of the staff member
     * @return          The number of students assigned to a staff
     */
    public static int getNumOfStudents(String staffID) 
    {
        return CampDatabase.getInstance().findByRules(
                camp -> camp.getStaffID().equalsIgnoreCase(staffID),
                camp -> camp.getStatus() == CampStatus.ALLOCATED ||
                        camp.getStatus() == CampStatus.AVAILABLE
        ).size();
    }

    /**
     * Retrieves a list of all staff members who are unavailable based on the maximum number of students per staff
     * 
     * @return  A list of staff members with the maximum number of students assigned.
     */
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
