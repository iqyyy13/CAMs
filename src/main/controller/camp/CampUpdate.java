package main.controller.camp;

import java.util.Scanner;

import main.boundary.mainpage.StaffMainPage;
import main.database.camp.CampDatabase;
import main.database.user.StaffDatabase;
import main.model.camp.Camp;
import main.model.camp.CampStatus;
import main.model.user.Staff;
import main.utils.exception.UserErrorException;
import main.utils.iocontrol.IntGetter;
import main.utils.ui.ChangePage;

public class CampUpdate 
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

    /**
     * Changes the location of a camp
     * 
     * @param campID                The ID of the camp to be modified
     * @throws UserErrorException   If an error occurs during the operation
     */
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

    /**
     * Changes the start date of a camp
     * 
     * @param campID                The ID of the camp to be modified
     * @throws UserErrorException   If an error occurs during the operation
     */
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

    /**
     * Changes the end date of a camp
     * @param campID                The ID of the camp to be modified
     * @throws UserErrorException   If an error occurs during the operation
     */
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

    /**
     * Changes the closing date of a camp
     * @param campID                The ID of the camp to be modified
     * @throws UserErrorException   If an error occurs during the operation
     */
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
     * Toggles the visibility of a camp to students
     * 
     * @param campID                The ID of the camp to update its status.
     * @throws UserErrorException   If the camp with the given ID is not found.
     */
    public static void changeCampStatus(String campID) throws UserErrorException
    {
        
        ChangePage.changePage();
        Camp camp = CampDatabase.getInstance().getByID(campID);

        if(camp.getAvailableCCSlots() == camp.getCCMaxSlots() && camp.getAvailableSlots() == camp.getMaxSlots())
        {
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
        else
        {
            System.out.println("You are unable to change the status of this camp as students have already registered");
            System.out.println("Press Enter to go back");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            try
            {
                Staff staff = StaffDatabase.getInstance().getByID(camp.getStaffID());
                StaffMainPage.staffMainPage(staff);
            } catch (UserErrorException e)
            {
                System.err.println("Staff ID does not exist");
            }
        }
    }
}
