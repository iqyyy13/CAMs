package main.controller.camp;

import main.controller.request.StaffManager;
import main.database.camp.CampDatabase;
import main.database.user.StaffDatabase;
import main.database.user.StudentDatabase;
import main.model.camp.Camp;
import main.model.camp.CampStatus;
import main.model.user.Student;
import main.model.user.StudentStatus;
import main.model.user.Staff;
import main.utils.config.Location;
import main.utils.exception.PageBackException;
import main.utils.exception.UserAlreadyExistsException;
import main.utils.exception.UserErrorException;
import main.utils.iocontrol.CSVReader;
import main.utils.iocontrol.IntGetter;
import main.utils.parameters.EmptyID;
import main.utils.ui.ChangePage;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Manages operations related to Camp entities, including creation, modification, and retrieval
 * of camp information. Also provides methods for loading camps from external sources and updating
 * the status of camps based on staff availability
 */
public class CampManager 
{
    
    /** 
     * Changes the title of a camp identified by its ID
     * 
     * @param campID                The ID of the camp to be modified
     * @param newTitle              The new title of the camp
     * @throws UserErrorException   If an error occurs during the operation
     */
    public static void changeCampTitle(String campID, String newTitle) throws UserErrorException 
    {
        Camp camp = CampDatabase.getInstance().getByID(campID);
        camp.setCampTitle(newTitle);
        CampDatabase.getInstance().update(camp);
        CampManager.updateCampsStatus();
    }

    /**
     * Changes the title of a camp identified by its ID, with user input for its new title
     * 
     * @param campID                The ID of the camp to be modified
     * @throws UserErrorException   If an error occurs during the operation
     */
    public static void changeCampTitle(String campID) throws UserErrorException 
    {
        ChangePage.changePage();
        Camp camp = CampDatabase.getInstance().getByID(campID);
        System.out.println("Enter the new title for your camp: ");
        String newTitle = new Scanner(System.in).nextLine().trim();
        camp.setCampTitle(newTitle);
        CampDatabase.getInstance().update(camp);
        CampManager.updateCampsStatus();
    }

    public static void changeCampLocation(String campID) throws UserErrorException 
    {
        ChangePage.changePage();
        Camp camp = CampDatabase.getInstance().getByID(campID);
        System.out.println("Enter the new location for your camp: ");
        String newLocation = new Scanner(System.in).nextLine().trim();
        camp.setLocation(newLocation);
        CampDatabase.getInstance().update(camp);
        CampManager.updateCampsStatus();
    }

    public static void changeStartDate(String campID) throws UserErrorException
    {
        ChangePage.changePage();
        Camp camp = CampDatabase.getInstance().getByID(campID);
        System.out.println("Enter the new start date for your camp (YYYYMMDD): ");
        String startDate = new Scanner(System.in).nextLine().trim();
        camp.setStartDateString(startDate);
        CampDatabase.getInstance().update(camp);
        CampManager.updateCampsStatus();
    }

    public static void changeEndDate(String campID) throws UserErrorException
    {
        ChangePage.changePage();
        Camp camp = CampDatabase.getInstance().getByID(campID);
        System.out.println("Enter the new end date for your camp (YYYYMMDD): ");
        String endDate = new Scanner(System.in).nextLine().trim();
        camp.setEndDateString(endDate);
        CampDatabase.getInstance().update(camp);
        CampManager.updateCampsStatus();
    }

    public static void changeClosingDate(String campID) throws UserErrorException
    {
        ChangePage.changePage();
        Camp camp = CampDatabase.getInstance().getByID(campID);
        System.out.println("Enter the new closing registration date for your camp (YYYYMMDD): ");
        String closingDate = new Scanner(System.in).nextLine().trim();
        camp.setClosingDateString(closingDate);
        CampDatabase.getInstance().update(camp);
        CampManager.updateCampsStatus();
    }
    /**
     * Retrieves a list of all camps
     * 
     * @return A list containing all camps
     */
    public static List<Camp> viewAllCamp()
    {
        return CampDatabase.getInstance().getList();
    }

    /**
     * Retrieves a list of available camps
     * 
     * @return A list containing available camps
     */
    public static List<Camp> viewAvailableCamps() 
    {
        return CampDatabase.getInstance().findByRules(p -> p.getStatus() == CampStatus.AVAILABLE);
    }

    /**
     * Creates a new camp with the provided information and adds it to the database
     * 
     * @param campID                        The ID of the new camp
     * @param campTitle                     The title of the new camp
     * @param staffID                       The ID of the staff associated with the camp
     * @param faculty                       The faculty associated with the camp
     * @param location                      The location associated with the camp
     * @param description                   The description associated with the camp
     * @throws UserAlreadyExistsException   If the camp ID already exists
     */
    public static void createCamp(String campID, String campTitle, String staffID, String faculty, String location, String description, 
    String startDate, String endDate, String closingDate) 
    throws UserAlreadyExistsException 
    {
        Camp c = new Camp(campID, campTitle, staffID, faculty, location, description, startDate, endDate, closingDate);
        CampDatabase.getInstance().add(c);
        CampManager.updateCampsStatus();
    }

    /**
     * Creates a new camp with the provided information and adds it to the database
     * 
     * @param campTitle                     The title of the new camp
     * @param staffID                       The ID of the staff associated with the camp
     * @param faculty                       The faculty associated with the camp
     * @param location                      The location associated with the camp
     * @param description                   The description associated with the camp
     * @return                              The newly created camp object
     * @throws UserAlreadyExistsException   If the camp ID already exists
     */
    public static Camp createCamp(String campTitle, String staffID, String faculty, String location, String description, String startDate,
    String endDate, String closingDate) 
    throws UserAlreadyExistsException 
    {
        Camp c = new Camp(getNewCampID(), campTitle, staffID, faculty, location, description, startDate, endDate, closingDate);
        CampDatabase.getInstance().add(c);
        CampManager.updateCampsStatus();
        return c;
    }


    /**
     * Retrieves the list of all camps from the database
     * 
     * @return  A list containing all camps stored in the database
     */
    public static List<Camp> getAllCamp() 
    {
        return CampDatabase.getInstance().getList();
    }

    /**
     * Retrieves the list of camps associated with a specific status with the database
     * 
     * @param campStatus    The status of the camps to retrieve
     * @return              A list containing camps with the specified status
     */
    public static List<Camp> getAllCampByStatus(CampStatus campStatus) 
    {
        return CampDatabase.getInstance().findByRules(camp -> camp.getStatus().equals(campStatus));
    }

    /**
     * Generates a new unique camp ID based on the existing IDs in the database
     * 
     * @return  A new unique camp ID.
     */
    public static String getNewCampID() 
    {
        int max = 0;
        for (Camp c : CampDatabase.getInstance()) 
        {
            int id = Integer.parseInt(c.getID().substring(1));
            if (id > max) 
            {
                max = id;
            }
        }
        return "P" + (max + 1);
    }

    /**
     * Loads camp information from an external source and adds it to the database
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
                String location = camp.get(3);
                String description = camp.get(4);
                String startDate = camp.get(5);
                String endDate = camp.get(6);
                String closingDate = camp.get(7);
                System.out.println("Checks works");
                List<Staff> staffs = StaffDatabase.getInstance().findByRules(s -> s.checkUsername(staffName));
                if (staffs.size() == 0) 
                {
                    System.out.println("Load camp " + campName + " failed: staff " + staffName + "not found");
                } 
                else if (staffs.size() == 1) 
                {
                    CampManager.createCamp(campName, staffs.get(0).getID(), staffs.get(0).getFaculty(), location, 
                    description, startDate, endDate, closingDate);
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
     * Checks if the camp database is empty
     * 
     * @return True if the camp database is empty, false otherwise
     */
    public static boolean databaseIsEmpty() 
    {
        return CampDatabase.getInstance().isEmpty();
    }

    /**
     * Checks if the camp database does not contain a camp with the specified ID.
     * 
     * @param campID    The ID of a camp to check
     * @return          True if the camp is not found, false otherwise
     */
    public static boolean notContainsCampByID(String campID) 
    {
        return !CampDatabase.getInstance().contains(campID);
    }

    /**
     * Checks if the camp database contains a camp with the specified ID.
     * 
     * @param campID    The ID of a camp to check
     * @return          True if the camp is found, false otherwise
     */
    public static boolean containsCampByID(String campID) 
    {
        return CampDatabase.getInstance().contains(campID);
    }

    /**
     * Retrieves a camp by its unique identifier
     * 
     * @param campID                The ID of the camp to retrieve
     * @return                      The camp with the specified ID
     * @throws UserErrorException   If the camp with the given ID is not found
     */
    public static Camp getByID(String campID) throws UserErrorException 
    {
        return CampDatabase.getInstance().getByID(campID);
    }

    /**
     * Retrieves a list of all available camps
     * 
     * @return  A list containing all camps with the status "AVAILABLE"
     */
    public static List<Camp> getAllAvailableCamps() 
    {
        return CampDatabase.getInstance().findByRules(p -> p.getStatus() == CampStatus.AVAILABLE);
    }

    /**
     * Retrieves a camp by its unique identifier
     * 
     * @param campID                The ID of the camp to retrieve
     * @return                      The camp with the specified ID
     * @throws UserErrorException   If the camp with the given ID is not found
     */
    public static Camp getCampByID(String campID) throws UserErrorException 
    {
        return CampDatabase.getInstance().getByID(campID);
    }

    /**
     * Retrieves a list of camps associated with a staff member
     * 
     * @param staffID   The ID of the staff member
     * @return          A list containing all camps associated with the specified staff member
     */
    public static List<Camp> getAllCampsByStaff(String staffID) 
    {
        return CampDatabase.getInstance().findByRules(p -> p.getStaffID().equalsIgnoreCase(staffID));
    }

    /**
     * Updates the status of camps based on the availability of staff members. If a camp's associated
     * staff is unavailable, the camp's status is set to UNAVAILABLE, otherwise it is set to AVAILABLE
     */
    public static void updateCampsStatus() 
    {
        List<Staff> staffs = StaffManager.getAllUnavailableStaff();
        Set<String> staffIDs = new HashSet<>();
        for (Staff staff : staffs) 
        {
            staffIDs.add(staff.getID());
        }
        List<Camp> camps = CampDatabase.getInstance().getList();
        for (Camp camp : camps) 
        {
            if (staffIDs.contains(camp.getStaffID()) && camp.getStatus() == CampStatus.AVAILABLE) 
            {
                camp.setStatus(CampStatus.UNAVAILABLE);
            }
            if (!staffIDs.contains(camp.getStaffID()) && camp.getStatus() == CampStatus.UNAVAILABLE) 
            {
                camp.setStatus(CampStatus.AVAILABLE);
            }
        }
        CampDatabase.getInstance().updateAll(camps);
    }

    /**
     * Toggles the visibility of a camp to students
     * 
     * @param campID                The ID of the camp to update its status.
     * @throws UserErrorException   If the camp with the given ID is not found.
     */
    public static void changeCampStatus(String campID) throws UserErrorException
    {
        ChangePage.changePage();
        Camp camp = CampDatabase.getInstance().getByID(campID);
        System.out.println("\t1. AVAILABLE");
        System.out.println("\t2. UNAVAILABLE");
        System.out.println("Current Camp Status: " + camp.getStatus());
        System.out.println("Please enter your choice for the new status of your camp:");
        int option = IntGetter.readInt();
        try
        {        
            switch(option)
            {
                case 1 -> camp.setStatus(CampStatus.AVAILABLE);
                case 2 -> camp.setStatus(CampStatus.UNAVAILABLE);
                default -> throw new IllegalArgumentException("Invalid option");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Camp not found");
        }

        CampDatabase.getInstance().update(camp);
        //CampManager.updateCampsStatus();
    }
}
