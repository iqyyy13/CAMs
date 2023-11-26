package main.boundary.mainpage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import javax.management.RuntimeErrorException;

import main.boundary.account.LoginUI;
import main.boundary.account.Logout;
import main.boundary.account.ResetPassword;
import main.boundary.account.ViewStaffProfile;
import main.boundary.modelviewer.CampViewer;
import main.boundary.modelviewer.ModelViewer;
import main.boundary.modelviewer.SuggestionViewer;
import main.controller.account.AccountManager;
import main.controller.camp.CampManager;
import main.controller.enquiry.EnquiryManager;
import main.controller.camp.CampDateClash;
import main.controller.request.StudentManager;
import main.database.camp.CampDatabase;
import main.database.enquiry.EnquiryDatabase;
import main.database.suggestion.SuggestionDatabase;
import main.database.user.StaffDatabase;
import main.database.user.StudentDatabase;
import main.model.user.*;
import main.model.Model;
import main.model.camp.Camp;
import main.model.suggestion.Suggestion;
import main.model.suggestion.SuggestionStatus;
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

            boolean shouldResetPassword = ResetPassword.promptUserForPasswordReset(UserType.STAFF, staff.getID(), staff.getPassword());

            if(shouldResetPassword)
            {
                try 
                {
                    ResetPassword.changePassword(UserType.STAFF, staff.getID());
                    StaffDatabase.getInstance().update(staff);
                    LoginUI.login();
                } catch (PageBackException e)
                {
                    try
                    {
                        LoginUI.login();
                    } catch (PageBackException e2)
                    {

                    }
                    
                } catch(UserErrorException e)
                {
                    System.err.println("User not found");
                }
            }

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

            EnquiryManager.refresh_enquiry_db();

            try {
                staff = StaffDatabase.getInstance().getByID(staff.getID());
            } catch (UserErrorException e) {
                e.printStackTrace();
            }

            try {
                switch (choice) {
                    case 1 -> ViewStaffProfile.viewUserProfilePage(staff);
                    case 2 -> ResetPassword.changePassword(UserType.STAFF, staff.getID());
                    case 3 -> createCamp(staff);
                    case 4 -> CampViewer.viewAllCamp();
                    case 5 -> CampViewer.generateCreatedCamp(staff);
                    case 6 -> CampViewer.viewRegisteredStudents(staff);
                    case 7 -> CampViewer.editCampDetails(staff);
                    case 8 -> deleteCamp(staff);
                    case 9 -> EnquiryManager.view_all_pending_enquiry(staff.getID());
                    case 10 -> EnquiryManager.reply_enquiry(null);
                    case 11 -> SuggestionViewer.viewSuggestions(staff);
                    case 12 -> replySuggestions(staff);
                    case 13 -> generateReport(staff);
                    case 14 -> generatePerformanceReport(staff);
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
        System.out.println("Is this camp open to the whole NTU? [y]/[n] : ");
        String option = new Scanner(System.in).nextLine().toLowerCase();
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
            if(option.equals("y"))
            {
                camp.setOpenToNTU("true");
                try
                {
                    CampDatabase.getInstance().update(camp);
                } catch(UserErrorException e)
                {
                    System.err.println("Camp ID not found");
                }
            }
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

    /**
     * Generates a performance report for the Staff member based on the chosen camp
     * 
     * @param staff The staff member for whom the performance report is generated
     */
    private static void generatePerformanceReport(Staff staff)
    {
        ChangePage.changePage();
        List<Camp> campList = CampDatabase.getInstance().findByRules(p -> p.getStaffID().equalsIgnoreCase(staff.getID()));
        ModelViewer.displayListOfDisplayable(campList);
        System.out.println("Enter the CampID to generate a performance report of:");
        Scanner scanner = new Scanner(System.in);
        String campID = scanner.nextLine().toUpperCase();

        Camp camp = CampViewer.findCampByID(campList, campID);
        String registeredStudentID = camp.getStudentID();

        if(registeredStudentID != null && !registeredStudentID.isEmpty())
        {
            String[] studentIDs = registeredStudentID.split(",");
            for(String studentID : studentIDs)
            {
                try
                {
                    Student student = StudentDatabase.getInstance().getByID(studentID.trim());
                    if(student.getCCId().equals(campID))
                    {
                        ModelViewer.displaySingleDisplayable(student);
                    }
                } catch (Exception e)
                {
                    System.out.println("");
                }
            }
        }
        System.out.println("Press Enter to go back");
        scanner.nextLine();
        StaffMainPage.staffMainPage(staff);
    }

    /**
     * Generates a report based on the Staff member's choice
     * 
     * @param staff The staff member for whom the report is generated
     */
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

    /**
     * Formats the data string to the proper format (YYYY-MM-DD)
     * 
     * @param date  The date string to be formatted
     * @return      The formatted date string
     */
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

    /**
     * Replies to suggestions by the staff member
     * 
     * @param staff The staff member replying to suggestions
     */
    private static void replySuggestions(Staff staff) 
    {
        ChangePage.changePage();
        SuggestionViewer.viewSuggestions(staff);
        System.out.println("");
        System.out.println("Enter the SuggestionID that you would want to approve/disapprove");
        Scanner scanner = new Scanner(System.in);
        String option = scanner.nextLine();
        try
        {
            Suggestion suggestion = SuggestionDatabase.getInstance().getByID(option);
            ChangePage.changePage();
            ModelViewer.displaySingleDisplayable(suggestion);
            System.out.println("");
            System.out.println("1. Approve");
            System.out.println("2. Disapprove");
            System.out.print("Enter your option : ");
            int reply = IntGetter.readInt();

            switch(reply)
            {
                case 1 -> 
                {
                    suggestion.setStatus(SuggestionStatus.APPROVED);
                    Student student = StudentDatabase.getInstance().getByID(suggestion.getCommitteeUserID());
                    student.incrementPoint();
                    StudentDatabase.getInstance().update(student);
                }
                case 2 -> suggestion.setStatus(SuggestionStatus.DISAPPROVED);
                default ->
                {
                    System.out.println("Invalid Choice. Please try again");
                    System.out.println("Press Enter to retry");
                    scanner.nextLine();
                    throw new PageBackException();
                }
            }
            SuggestionDatabase.getInstance().update(suggestion);
            System.out.println("Suggestion has been " + suggestion.getStatus());
            System.out.println("Press Enter to go back");
            scanner.nextLine();
            StaffMainPage.staffMainPage(staff);
        } catch(UserErrorException e)
        {
            System.err.println("Suggestion ID does not exist");
            System.out.println("Press Enter to retry");
            scanner.nextLine();
            replySuggestions(staff);
        } catch (PageBackException e)
        {
            replySuggestions(staff);
        }
    }
}

