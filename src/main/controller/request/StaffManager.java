package main.controller.request;

import main.database.camp.CampDatabase;
import main.database.request.RequestDatabase;
import main.database.user.StaffDatabase;
import main.model.camp.CampStatus;
import main.model.request.Request;
import main.model.request.RequestStatus;
import main.model.request.RequestType;
import main.model.user.Staff;
import main.utils.exception.UserAlreadyExistsException;
import main.utils.parameters.EmptyID;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * SupervisorManager class
 */
public class StaffManager 
{

    public static int MAX_NUM_OF_STUDENTS_PER_STAFF = 20;

    /**
     * View all requests
     *
     * @param supervisorID the supervisor ID of the supervisor that is going to view all requests
     */
    public static List<Request> viewRequest(String staffID) 
    {
        return RequestDatabase.getInstance().findByRules(request -> request.getID().equals(staffID));
    }

    /**
     * get all pending requests by supervisor
     * @param supervisorID the supervisor ID of the supervisor that is going to view all pending requests
     * @return list of pending requests
     */
    public static List<Request> getPendingRequestsByStaff(String staffID) 
    {
        if (!StaffDatabase.getInstance().contains(staffID)) {
            throw new IllegalArgumentException("Staff does not exist");
        }
        return RequestDatabase.getInstance().findByRules(
                request -> request.getStaffID().equals(staffID),
                request -> request.getStatus() == RequestStatus.PENDING,
                request -> request.getRequestType() != RequestType.STUDENT_EDIT_ENQUIRY
        );
    }

    /**
     * get all request history by supervisor
     * @param supervisor the supervisor that is going to view all request history
     *
     * @return list of request history
     */
    public static List<Request> getAllRequestHistory(Staff staff) 
    {
        return RequestDatabase.getInstance().findByRules(
                request -> Objects.equals(request.getStaffID(), staff.getID()),
                request -> request.getRequestType() != RequestType.STUDENT_REGISTRATION,
                request -> request.getRequestType() != RequestType.STUDENT_DEREGISTRATION
        );
    }

    /**
     * get number of students that a supervisor is supervising
     * @param supervisorID the supervisor ID of the supervisor
     *
     * @return number of students that a supervisor is supervising
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
     * get all supervisors that are not available
     *
     * @return list of supervisors that are not available
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
