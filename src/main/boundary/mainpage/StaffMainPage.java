package main.boundary.mainpage;

import java.util.List;
import java.util.Scanner;

import javax.management.RuntimeErrorException;

import main.boundary.account.Logout;
import main.boundary.account.ResetPassword;
import main.boundary.account.ViewUserProfile;
import main.boundary.modelviewer.CampViewer;
import main.boundary.modelviewer.ModelViewer;
import main.controller.account.AccountManager;
import main.controller.camp.CampManager;
import main.controller.request.StudentManager;
import main.database.camp.CampDatabase;
import main.database.user.StaffDatabase;
import main.model.user.*;
import main.model.camp.Camp;
import main.utils.exception.UserErrorException;
import main.utils.exception.PageBackException;
import main.utils.exception.UserAlreadyExistsException;
import main.utils.iocontrol.IntGetter;
import main.utils.parameters.EmptyID;
import main.utils.ui.ChangePage;

/**
 * Represents the main page for a staff user, providing various options and functionalities
 */
public class StaffMainPage 
{
    /**
     * Displays the main page for a staff user, allowing them to choose from various options.
     *
     * @param user The user object representing the staff member
     */
    public static void staffMainPage(User user) {
        if (user instanceof Staff staff) {
            ChangePage.changePage();
            System.out.println("Welcome to Student Main Page");
            System.out.println("Hello, " + staff.getUserName() + "!");
            System.out.println();
            System.out.println("\t1. View my profile");
            System.out.println("\t2. Change my password");
            System.out.println("\t3. Create a camp");
            System.out.println("\t4. View all camps");
            System.out.println("\t5. View all created camps");
            System.out.println("\t6. View registered students");
            System.out.println("\t7. Edit camp");
            System.out.println("\t8. Delete camp");
            System.out.println("\t9. View all pending enquiries");
            System.out.println("\t10. Reply enquiries");
            System.out.println("\t11. View all pending suggestions");
            System.out.println("\t12. Approve/Reject suggestions");
            System.out.println("\t13. Generate report of students");
            System.out.println("\t14. Generate performance report of CCs");
            System.out.println("\t15. Logout");

            System.out.println();
            System.out.print("Please enter your choice: ");

            int choice = IntGetter.readInt();

            try {
                staff = StaffDatabase.getInstance().getByID(staff.getID());
            } catch (UserErrorException e) {
                e.printStackTrace();
            }

            try {
                switch (choice) {
                    case 1 -> ViewUserProfile.viewUserProfilePage(staff);
                    case 2 -> ResetPassword.changePassword(UserType.STAFF, staff.getID());
                    case 3 -> createCamp(staff);
                    case 4 -> CampViewer.viewAllCamp();
                    case 5 -> CampViewer.generateCreatedCamp(staff);
                    case 6 -> CampViewer.viewRegisteredStudents(staff);
                    case 7 -> CampViewer.editCampDetails(staff);
                    case 8 -> deleteCamp(staff);
                    //case 8 -> changeTitleForCamp(student);
                    case 15 -> Logout.logout();
                    default -> {
                        System.out.println("Invalid choice. Please press enter to try again.");
                        new Scanner(System.in).nextLine();
                        throw new PageBackException();
                    }
                }
            } catch (PageBackException e) {
                StaffMainPage.staffMainPage(staff);
            }


        } else {
            throw new IllegalArgumentException("User is not a student.");
        }
    }

    /** 
     * Creates a new camp with information provided by the staff member, displaying the details
     * and confirming the creation
     * 
     * @param staff                 The staff member creating the camp
     * @throws PageBackException    If the user chooses to go back during the operation
     */
    private static void createCamp(Staff staff) throws PageBackException
    {
        ChangePage.changePage();
        System.out.println("Creating a camp..");
        System.out.println("Please name your camp:");
        String campTitle = new Scanner(System.in).nextLine();
        System.out.println("Please enter the location to meet:");
        String location = new Scanner(System.in).nextLine();
        System.out.println("Please give a brief description for the camp:");
        String description = new Scanner(System.in).nextLine();
        Camp camp;
        try 
        {
            camp = CampManager.createCamp(campTitle, staff.getID(), staff.getFaculty(), location, description);
        } catch (UserAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
        System.out.println("The camp details are as follows:");
        ModelViewer.displaySingleDisplayable(camp);
        System.out.println("Are you sure you want to create this camp? (Y/N)");
        String input = new Scanner(System.in).nextLine();
        if (!input.equalsIgnoreCase("Y")) 
        {
            System.out.println("Camp creation cancelled!");
            try {
                CampDatabase.getInstance().remove(camp.getID());
                CampManager.updateCampsStatus();
            } catch (UserErrorException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Enter enter to continue");
            new Scanner(System.in).nextLine();
            throw new PageBackException();
        }
        System.out.println("Camp created successfully!");
        System.out.println("Enter enter to continue");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * Deletes a camp created by the staff member, confirming the deletion after displaying the camp details
     * 
     * @param staff                 The staff member deleting the camp
     * @throws PageBackException    If the user chooses to go back during the operation
     */
    public static void deleteCamp(Staff staff) throws PageBackException
    {
        ChangePage.changePage();
        System.out.println("View Created Camps");
        List<Camp> campList = CampDatabase.getInstance().findByRules(p -> p.getStaffID().equalsIgnoreCase(staff.getID()));
        ModelViewer.displayListOfDisplayable(campList);
        System.out.println("");
        System.out.println("Enter the CampID that you would like to delete:");
        String option = new Scanner(System.in).nextLine().trim().toUpperCase();
        System.out.println("You are about to delete Camp with Camp ID " + option + ". Are you sure?");
        System.out.println("Press enter to confirm or [b] to go back.");

        String userInput = new Scanner(System.in).nextLine().trim();

        if(userInput.isEmpty())
        {
            try
            {
            System.out.println("Confirmed... Deleting.");
            Camp campToDelete = CampViewer.findCampByID(campList, option);
            if(campToDelete != null)
            {
                CampDatabase.getInstance().remove(campToDelete.getID());
                System.out.println("Camp with Camp ID " + option + " deleted successfully.");
                System.out.println("Press Enter to go back.");
                new Scanner(System.in).nextLine();
                throw new PageBackException();
            }
            else
            {
                System.out.println("Camp not found with CampID " + option + ".");
            }

            throw new PageBackException();
            } catch (UserErrorException e)
            {
                System.out.println("Error Deleting Camp");
            }
        }
        else if(userInput.equalsIgnoreCase("b"))
        {
            System.out.println("Going back...");
            throw new PageBackException();
        }
        else
        {
            System.out.println("Invalid choice. Please press enter to confirm or [b] to go back.");
            deleteCamp(staff);
        }
    }
}

