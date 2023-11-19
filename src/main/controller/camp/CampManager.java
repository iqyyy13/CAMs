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

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class CampManager 
{
    
    /** 
     * @param campID
     * @param newTitle
     * @throws UserErrorException
     */
    public static void changeCampTitle(String campID, String newTitle) throws UserErrorException 
    {
        Camp camp = CampDatabase.getInstance().getByID(campID);
        camp.setCampTitle(newTitle);
        CampDatabase.getInstance().update(camp);
        CampManager.updateCampsStatus();
    }

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

    public static List<Camp> viewAllCamp()
    {
        return CampDatabase.getInstance().getList();
    }

    public static List<Camp> viewAvailableCamps() 
    {
        return CampDatabase.getInstance().findByRules(p -> p.getStatus() == CampStatus.AVAILABLE);
    }

    public static void createCamp(String campID, String campTitle, String staffID, String faculty, String location, String description) 
    throws UserAlreadyExistsException 
    {
        Camp c = new Camp(campID, campTitle, staffID, faculty, location, description);
        CampDatabase.getInstance().add(c);
        CampManager.updateCampsStatus();
    }

    public static Camp createCamp(String campTitle, String staffID, String faculty, String location, String description) 
    throws UserAlreadyExistsException 
    {
        Camp c = new Camp(getNewCampID(), campTitle, staffID, faculty, location, description);
        CampDatabase.getInstance().add(c);
        CampManager.updateCampsStatus();
        return c;
    }

    public static List<Camp> getAllCamp() 
    {
        return CampDatabase.getInstance().getList();
    }

    public static List<Camp> getAllCampByStatus(CampStatus campStatus) 
    {
        return CampDatabase.getInstance().findByRules(camp -> camp.getStatus().equals(campStatus));
    }

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
                System.out.println("Checks works");
                List<Staff> staffs = StaffDatabase.getInstance().findByRules(s -> s.checkUsername(staffName));
                if (staffs.size() == 0) 
                {
                    System.out.println("Load camp " + campName + " failed: staff " + staffName + "not found");
                } 
                else if (staffs.size() == 1) 
                {
                    CampManager.createCamp(campName, staffs.get(0).getID(), staffs.get(0).getFaculty(), location, description);
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

    public static boolean repositoryIsEmpty() 
    {
        return CampDatabase.getInstance().isEmpty();
    }

    public static boolean notContainsCampByID(String campID) 
    {
        return !CampDatabase.getInstance().contains(campID);
    }

    public static boolean containsCampByID(String campID) 
    {
        return CampDatabase.getInstance().contains(campID);
    }

    public static Camp getStudentCamp(Student student)
    {
        if (EmptyID.isEmptyID(student.getCampID())) 
        {
            return null;
        } 
        else 
        {
            try 
            {
                return CampDatabase.getInstance().getByID(student.getCampID());
            } catch (UserErrorException e) {
                throw new IllegalStateException("Camp " + student.getCampID() + " not found");
            }
        }
    }

    public static Camp getByID(String campID) throws UserErrorException 
    {
        return CampDatabase.getInstance().getByID(campID);
    }

    public static List<Camp> getAllAvailableCamps() 
    {
        return CampDatabase.getInstance().findByRules(p -> p.getStatus() == CampStatus.AVAILABLE);
    }

    public static Camp getCampByID(String campID) throws UserErrorException 
    {
        return CampDatabase.getInstance().getByID(campID);
    }

    public static List<Camp> getAllCampsByStaff(String staffID) 
    {
        return CampDatabase.getInstance().findByRules(p -> p.getStaffID().equalsIgnoreCase(staffID));
    }

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

    //toggles visibility of the camp itself to students
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
