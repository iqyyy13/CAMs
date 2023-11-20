package main.boundary.mainpage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import javax.management.RuntimeErrorException;

import main.boundary.account.Logout;
import main.boundary.account.ResetPassword;
import main.boundary.account.ViewUserProfile;
import main.boundary.modelviewer.CampViewer;
import main.boundary.modelviewer.ModelViewer;
import main.boundary.modelviewer.SuggestionViewer;
import main.controller.account.AccountManager;
import main.controller.camp.CampManager;
import main.controller.enquiry.EnquiryManager;
<<<<<<< HEAD
=======
import main.controller.camp.campClashTest;
>>>>>>> 1da6f7914d4097ac015857754b7a2de9dfc47f61
import main.controller.request.StudentManager;
import main.database.camp.CampDatabase;
import main.database.enquiry.EnquiryDatabase;
import main.database.user.StaffDatabase;
import main.database.enquiry.EnquiryDatabase;
import main.model.user.*;
import main.model.camp.Camp;
import main.model.suggestion.Suggestion;
import main.utils.exception.UserErrorException;
import main.utils.exception.PageBackException;
import main.utils.exception.UserAlreadyExistsException;
import main.utils.iocontrol.CampReportGenerator;
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
            System.out.println("\t13. Generate report of students attending each camp");
            System.out.println("\t14. Generate performance report of CCs");
            System.out.println("\t15. Logout");

            System.out.println();
            System.out.print("Please enter your choice: ");

            int choice = IntGetter.readInt();

<<<<<<< HEAD
            // refresh Enquiry DB
=======
>>>>>>> 1da6f7914d4097ac015857754b7a2de9dfc47f61
            EnquiryManager.refresh_enquiry_db();

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
                    case 9 -> EnquiryManager.view_all_pending_enquiry();
                    case 10 -> EnquiryManager.reply_enquiry(null);
<<<<<<< HEAD
                    //case 8 -> changeTitleForCamp(student);
=======
                    case 11 -> SuggestionViewer.viewSuggestions(staff);
                    case 13 -> generateReport(staff);
>>>>>>> 1da6f7914d4097ac015857754b7a2de9dfc47f61
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
        System.out.println("Enter the startDate (YYYYMMDD):");
        String startDate = new Scanner(System.in).nextLine();
        System.out.println("Enter the endDate (YYYYMMDD): ");
        String endDate = new Scanner(System.in).nextLine();
        System.out.println("Enter the closing registration date (YYYYMMDD): ");
        String closingDate = new Scanner(System.in).nextLine();
        Camp camp;
        try 
        {
            //LocalDate startDate1 = LocalDate.parse(startDate, DateTimeFormatter.BASIC_ISO_DATE);
            //LocalDate endDate1 = LocalDate.parse(endDate, DateTimeFormatter.BASIC_ISO_DATE);
            //LocalDate closingDate1 = LocalDate.parse(closingDate, DateTimeFormatter.BASIC_ISO_DATE);
            startDate = formatDataString(startDate);
            endDate = formatDataString(endDate);
            closingDate = formatDataString(closingDate);

            camp = CampManager.createCamp(campTitle, staff.getID(), staff.getFaculty(), location, description, startDate, endDate, closingDate);
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
    private static void deleteCamp(Staff staff) throws PageBackException
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

    private static void generateReport(Staff staff)
    {
        ChangePage.changePage();
        List<Camp> campList = CampDatabase.getInstance().findByRules(p -> p.getStaffID().equalsIgnoreCase(staff.getID()));
        System.out.println("1.Print all students");
        System.out.println("2.Print all camp attendees");
        System.out.println("3.Print all camp committee members");
        System.out.println("");
        System.out.println("Please Enter your choice: ");
        int choice = IntGetter.readInt();
        
        try
        {
            switch(choice)
            {
                case 1 -> CampReportGenerator.generateReportAndWriteToFile(campList, "ALL");
                case 2 -> CampReportGenerator.generateReportAndWriteToFile(campList, "CAMP ATTENDEE");
                case 3 -> CampReportGenerator.generateReportAndWriteToFile(campList, "CC");
                default -> 
                {
                    System.out.println("Invalid choice. Please try again.");
                    new Scanner(System.in);
                    throw new PageBackException();
                }
            }
        } catch (PageBackException e)
        {
            generateReport(staff);
        }
        //CampReportGenerator.generateReportAndWriteToFile(campList);
        System.out.println("File has been written");
        System.out.println("Press Enter to go back");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        StaffMainPage.staffMainPage(staff);
    }

    private static String formatDataString(String date)
    {
        if(date != null && date.length() == 8)
        {
            return date.substring(0, 4) + "-" + date.substring(4,6) + "-" 
            + date.substring(6, 8);
        }
        else
        {
            System.out.println("Invalid date format. Please enter a date in the date format YYYYMMDD.");
            return null;
        }
    }
}

