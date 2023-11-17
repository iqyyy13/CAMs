package main.controller.camp;

import main.controller.request.StaffManager;
import main.model.camp.Camp;
import main.model.camp.CampStatus;
import main.model.user.Student;
import main.model.user.StudentStatus;
import main.model.user.Staff;
import main.repository.camp.CampRepository;
import main.repository.user.StudentDatabase;
import main.repository.user.StaffDatabase;
import main.utils.config.Location;
import main.utils.exception.UserAlreadyExistsException;
import main.utils.exception.UserErrorException;
import main.utils.iocontrol.CSVReader;
import main.utils.parameters.EmptyID;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CampManager 
{
    public static void changeCampTitle(String campID, String newTitle) throws UserErrorException 
    {
        Camp camp = CampRepository.getInstance().getByID(campID);
        camp.setCampTitle(newTitle);
        CampRepository.getInstance().update(camp);
        CampManager.updateCampsStatus();
    }

    /**
     * Change the supervisor of a project
     *
     * @return the new supervisor
     */
    public static List<Camp> viewAllCamp()
    {
        return CampRepository.getInstance().getList();
    }

    /**
     * View all the projects that are available
     *
     * @return the list of available projects
     */
    public static List<Camp> viewAvailableCamps() 
    {
        return CampRepository.getInstance().findByRules(p -> p.getStatus() == CampStatus.AVAILABLE);
    }

    /**
     * create a new camp
     */
    public static void createCamp(String campID, String campTitle, String staffID, String faculty) throws UserAlreadyExistsException 
    {
        Camp c = new Camp(campID, campTitle, staffID, faculty);
        CampRepository.getInstance().add(c);
        CampManager.updateCampsStatus();
    }

    public static Camp createCamp(String campTitle, String staffID, String faculty) throws UserAlreadyExistsException 
    {
        Camp c = new Camp(getNewCampID(), campTitle, staffID, faculty);
        CampRepository.getInstance().add(c);
        CampManager.updateCampsStatus();
        return c;
    }

    public static List<Camp> getAllCamp() 
    {
        return CampRepository.getInstance().getList();
    }

    public static List<Camp> getAllCampByStatus(CampStatus campStatus) 
    {
        return CampRepository.getInstance().findByRules(camp -> camp.getStatus().equals(campStatus));
    }

    public static String getNewCampID() 
    {
        int max = 0;
        for (Camp c : CampRepository.getInstance()) {
            int id = Integer.parseInt(c.getID().substring(1));
            if (id > max) 
            {
                max = id;
            }
        }
        return "P" + (max + 1);
    }

    public static void deallocateCamp(String campID) throws UserErrorException 
    {
        Camp c = CampRepository.getInstance().getByID(campID);
        if (c.getStatus() != CampStatus.ALLOCATED) 
        {
            throw new IllegalStateException("The camp status is not ALLOCATED");
        }
        Student student;
        try 
        {
            student = StudentDatabase.getInstance().getByID(c.getStudentID());
        } catch (UserErrorException e) {
            throw new IllegalStateException("Student not found");
        }

        String staffID = c.getStaffID();
        Staff staff = StaffDatabase.getInstance().getByID(staffID);
        student.setCampID(EmptyID.EMPTY_ID);
        student.setStaffID(EmptyID.EMPTY_ID);
        student.setStatus(StudentStatus.UNREGISTERED);
        c.setStudentID(EmptyID.EMPTY_ID);
        c.setStatus(CampStatus.AVAILABLE);
        CampRepository.getInstance().update(c);
        StudentDatabase.getInstance().update(student);
        StaffDatabase.getInstance().update(staff);
        CampManager.updateCampsStatus();
    }

    /**
     * allocate a project
     *
     * @param projectID the ID of the project
     * @param studentID the ID of the student
     * @throws ModelNotFoundException if the project is not found
     */
    public static void allocateCamp(String campID, String studentID) throws UserErrorException 
    {
        Camp c = CampRepository.getInstance().getByID(campID);
        Student student;
        try 
        {
            student = StudentDatabase.getInstance().getByID(studentID);
        } catch (UserErrorException e) {
            throw new IllegalStateException("Student not found");
        }
        if (c.getStatus() == CampStatus.ALLOCATED) {
            throw new IllegalStateException("Camp is already allocated");
        }
        if (student.getStatus() == StudentStatus.REGISTERED) {
            throw new IllegalStateException("Student is already registered");
        }
        c.setStatus(CampStatus.ALLOCATED);
        c.setStudentID(studentID);
        student.setCampID(campID);
        student.setStaffID(c.getStaffID());
        student.setStatus(StudentStatus.REGISTERED);
        String staffID = c.getStaffID();
        Staff staff = StaffDatabase.getInstance().getByID(staffID);
        CampRepository.getInstance().update(c);
        StudentDatabase.getInstance().update(student);
        StaffDatabase.getInstance().update(staff);
        CampManager.updateCampsStatus();
    }

    /**
     * load projects from csv resource file
     */
    public static void loadCamps() 
    {
        List<List<String>> camps = CSVReader.read(Location.RESOURCE_LOCATION + "/resources/CampList.csv", true);
        System.out.println("TEST");
        for (List<String> camp : camps) {
            try {
                String staffName = camp.get(0);
                String campName = camp.get(1);
                String faculty = camp.get(2);
                System.out.println("Checks works");
                List<Staff> staffs = StaffDatabase.getInstance().findByRules(s -> s.checkUsername(staffName));
                if (staffs.size() == 0) 
                {
                    System.out.println("Load camp " + campName + " failed: staff " + staffName + "not found");
                } 
                else if (staffs.size() == 1) 
                {
                    CampManager.createCamp(campName, staffs.get(0).getID(), staffs.get(0).getFaculty());
                } 
                else 
                {
                    System.out.println("Load camp " + campName + " failed: multiple staffs found");
                }
            } catch (UserAlreadyExistsException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * check if the repository is empty
     *
     * @return true if the repository is empty
     */
    public static boolean repositoryIsEmpty() 
    {
        return CampRepository.getInstance().isEmpty();
    }

    /**
     * Check if the project is not in the repository
     *
     * @param projectID the ID of the project
     * @return true if the project is not in the repository
     */
    public static boolean notContainsCampByID(String campID) 
    {
        return !CampRepository.getInstance().contains(campID);
    }

    /**
     * Check if the project is in the repository
     *
     * @param projectID the ID of the project
     * @return true if the project is in the repository
     */
    public static boolean containsCampByID(String campID) 
    {
        return CampRepository.getInstance().contains(campID);
    }

    /**
     * get the project of a student
     *
     * @param student the student
     * @return the project of the student
     */
    public static Camp getStudentCamp(Student student) {
        if (EmptyID.isEmptyID(student.getCampID())) 
        {
            return null;
        } 
        else 
        {
            try 
            {
                return CampRepository.getInstance().getByID(student.getCampID());
            } catch (UserErrorException e) {
                throw new IllegalStateException("Project " + student.getCampID() + " not found");
            }
        }
    }

    /**
     * get the project of a supervisor
     *
     * @param projectID the ID of the project
     * @return the project of the supervisor
     * @throws ModelNotFoundException if the project is not found
     */
    public static Camp getByID(String campID) throws UserErrorException 
    {
        return CampRepository.getInstance().getByID(campID);
    }

    /**
     * get all available projects
     *
     * @return all available projects
     */
    public static List<Camp> getAllAvailableProject() 
    {
        return CampRepository.getInstance().findByRules(p -> p.getStatus() == CampStatus.AVAILABLE);
    }

    /**
     * get project by the project ID
     * @param projectID the ID of the project
     * @return the project
     * @throws ModelNotFoundException if the project is not found
     */
    public static Camp getCampByID(String campID) throws UserErrorException 
    {
        return CampRepository.getInstance().getByID(campID);
    }

    /**
     * get all projects by supervisor
     *
     * @param supervisorID the ID of the supervisor
     * @return all projects by supervisor
     */
    public static List<Camp> getAllProjectsByStaff(String staffID) {
        return CampRepository.getInstance().findByRules(p -> p.getStaffID().equalsIgnoreCase(staffID));
    }

    /**
     * update the status of all projects
     */
    public static void updateCampsStatus() {
        List<Staff> staffs = StaffManager.getAllUnavailableStaff();
        Set<String> staffIDs = new HashSet<>();
        for (Staff staff : staffs) 
        {
            staffIDs.add(staff.getID());
        }
        List<Camp> camps = CampRepository.getInstance().getList();
        for (Camp camp : camps) {
            if (staffIDs.contains(camp.getStaffID()) && camp.getStatus() == CampStatus.AVAILABLE) {
                camp.setStatus(CampStatus.UNAVAILABLE);
            }
            if (!staffIDs.contains(camp.getStaffID()) && camp.getStatus() == CampStatus.UNAVAILABLE) {
                camp.setStatus(CampStatus.AVAILABLE);
            }
        }
        CampRepository.getInstance().updateAll(camps);
    }
}
