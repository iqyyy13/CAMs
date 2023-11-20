package main.boundary.mainpage;

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
import main.controller.camp.campClashTest;
import main.controller.suggestion.SuggestionManager;
import main.controller.request.StudentManager;
import main.controller.suggestion.SuggestionManager;
import main.database.user.StudentDatabase;
import main.database.camp.CampDatabase;
import main.model.camp.Camp;
import main.model.camp.CampStatus;
import main.model.suggestion.Suggestion;
import main.model.user.*;
import main.utils.exception.UserErrorException;
import main.utils.exception.PageBackException;
import main.utils.exception.UserAlreadyExistsException;
import main.utils.iocontrol.CampReportGenerator;
import main.utils.iocontrol.IntGetter;
import main.utils.parameters.EmptyID;
import main.utils.ui.ChangePage;

/**
 * Represents the main page for a Camp Committee (CC) member, providing various options and functionalities
 */
public class CCMainPage 
{
    /** 
     * Displays the main page for a CC member, allowing them to choose from various options.
     * 
     * @param user  The user object representing the CC member
     */
    public static void ccMainPage(User user) 
    {
        if (user instanceof Student student) 
        {
            ChangePage.changePage();
            System.out.println("Welcome to CC Main Page");
            System.out.println("Hello, " + student.getUserName() + "!");
            System.out.println();
            System.out.println("\t1. View my profile");
            System.out.println("\t2. Change my password");
            System.out.println("\t3. View camps");
            System.out.println("\t4. View registered camps");
            System.out.println("\t5. Register for a camp");
            System.out.println("\t6. Withdraw from a camp");
            System.out.println("\t7. View assigned camp");
            System.out.println("\t8. View enquiry");
            System.out.println("\t9. Edit enquiry");
            System.out.println("\t10. Reply enquiry");
            System.out.println("\t11. Delete enquiry");
            System.out.println("\t12. Submit suggestions");
            System.out.println("\t13. View suggestions");
            System.out.println("\t14. Edit suggestions");
            System.out.println("\t15. Delete suggestions");
            System.out.println("\t16. Generate report of students attending each camp");
            System.out.println("\t17. Logout");

            System.out.println();
            System.out.print("Please enter your choice: ");

            int choice = IntGetter.readInt();

            try {
                student = StudentDatabase.getInstance().getByID(student.getID());
            } catch (UserErrorException e) {
                e.printStackTrace();
            }

            try {
                switch (choice) {
                    case 1 -> ViewUserProfile.viewUserProfilePage(student);
                    case 2 -> ResetPassword.changePassword(UserType.STUDENT, student.getID());
                    case 3 -> CampViewer.viewAvailableCamps(student);
                    case 4 -> CampViewer.viewRegisteredCamps(student);
                    case 5 -> registerCamp(student);
                    case 6 -> deregisterCamp(student);
                    case 7 -> CampViewer.viewAssignedCamp(student);
                    case 12 -> createSuggestion(student);
                    case 13 -> SuggestionViewer.viewOwnSuggestions(student);
                    case 14 -> SuggestionViewer.editSuggestionDetails(student);
                   // case 15 -> deleteSuggestion(student);
                    case 16 -> generateReport(student);
                    case 17 -> Logout.logout();
                    default -> {
                        System.out.println("Invalid choice. Please press enter to try again.");
                        new Scanner(System.in).nextLine();
                        throw new PageBackException();
                    }
                }
            } catch (PageBackException e) {
                StudentMainPage.studentMainPage(student);
            }
        } else {
            throw new IllegalArgumentException("User is not a student.");
        }
    }

    /** 
     * Registers a student for a camp, providing information about available camps and handling the registration process
     * 
     * @param student               The student registering for the camp
     * @throws PageBackException    If the user chooses to go back during the operation
     */
    private static void registerCamp(Student student) throws PageBackException
    {
        ChangePage.changePage();
        System.out.println("Here is the list of camps that are available for you to join.");
        ModelViewer.displayListOfDisplayable(CampManager.getAllAvailableCamps());
        System.out.println("Please enter the Camp ID that you would like to register: ");
        String campID = new Scanner(System.in).nextLine().trim().toUpperCase();
        String clashValue = campClashTest.registrationDateClash(student, campID);
        if(clashValue != null)
        {
            System.out.println("The camp that you have registered for has date clashes with camp ID " + clashValue);
            System.out.println("Press Enter to go back");
            new Scanner(System.in).nextLine();
            StudentMainPage.studentMainPage(student);
        }

        if(CampManager.notContainsCampByID(campID))
        {
            System.out.println("Camp not found");
            System.out.println("Press Enter to go back or enter [r] to retry");
            String option = new Scanner(System.in).nextLine();
            if(option.equals("r"))
            {
                registerCamp(student);
            }
            throw new PageBackException();
        }
        Camp camp;
        try
        {
            camp = CampManager.getCampByID(campID);
            if(camp.getStatus() != CampStatus.AVAILABLE)
            {
                System.out.println("Camp not found.");
                System.out.println("Press Enter to go back or enter [r] to retry");
                String option = new Scanner(System.in).nextLine();
                if(option.equals("r"))
                {
                    registerCamp(student);
                }
                throw new PageBackException();
            }
        } catch (UserErrorException e)
        {
            throw new RuntimeException(e);
        }
        ChangePage.changePage();
        System.out.println("Here is the Camp Information");
        Camp camp1;
        try
        {
            camp1 = CampDatabase.getInstance().getByID(campID);
            ModelViewer.displaySingleDisplayable(camp1);
        } catch (UserErrorException e)
        {
            throw new RuntimeException(e);
        }
        System.out.println("\t1. Student");
        System.out.println("\t2. Camp Committee Member");
        System.out.println("Press 1 to register as a student or 2 as a camp committee member: ");
        int role = new Scanner(System.in).nextInt();
        System.out.println("Are you sure you want to register for this Camp? ([y]/[n])");
        String choice = new Scanner(System.in).nextLine();
        if(choice.equalsIgnoreCase("y"))
        {
            if(role == 2)
            {
                try
                {
                    student.registerCamp(student,campID);
                    student.registerAsCC(student, camp1);
                    camp1.decrementAvailableCCSlots();
                    CampDatabase.getInstance().update(camp1);
                    camp1.storeStudentID(student, camp1);
                    CampDatabase.getInstance().update(camp1);
                    System.out.println("Register Success!");
                    System.out.println("Press enter to go back.");
                    Scanner scanner = new Scanner(System.in);
                    scanner.nextLine();
                    throw new PageBackException();
                } catch (Exception e) {
                    System.out.println("Enter [b] to go back or press Enter to retry.");
                    String option = new Scanner(System.in).nextLine();
                    if(option.equals("b"))
                    {
                        throw new PageBackException();
                    }
                    else
                    {
                        registerCamp(student);
                    }
                }
            }
            else
            {
                try
                {
                    student.registerCamp(student,campID);
                    camp1.decrementAvailableSlots();
                    camp1.storeStudentID(student, camp1);
                    CampDatabase.getInstance().update(camp1);
                    System.out.println("Register Success!");
                    System.out.println("Press enter to go back.");
                    Scanner scanner = new Scanner(System.in);
                    scanner.nextLine();
                    throw new PageBackException();
                } catch (Exception e) {
                    System.out.println("Enter [b] to go back or press Enter to retry.");
                    String option = new Scanner(System.in).nextLine();
                    if(option.equals("b"))
                    {
                        throw new PageBackException();
                    }
                    else
                    {
                        registerCamp(student);
                    }
                }
            }
        }
    }

    /**
     * Deregisters a student from a camp, providing information about available camps and handling the deregistration process
     * 
     * @param student   The student deregistering from the camp
     */
    private static void deregisterCamp(Student student)
    {
        ChangePage.changePage();
        System.out.println("Here is the list of camps that you have registered for.");

        try
        {
            CampViewer.viewRegisteredCamps(student);
        } catch (Exception e)
        {
            System.out.println("");
        }

        System.out.println("Please enter the Camp ID that you would like to remove yourself from: ");
        String campID = new Scanner(System.in).nextLine().trim().toUpperCase();

        if(student.getCCId().equals(campID))
        {
            System.out.println("You are a camp committee member of the Camp ID " + campID);
            System.out.println("You are unable to deregister yourself from this camp");
            System.out.println("Press Enter to continue");
            new Scanner(System.in).nextLine();
            StudentMainPage.studentMainPage(student);
        }
        
        student.deregisterCamp(student, campID);
        try
        {
            Camp camp = CampDatabase.getInstance().getByID(campID);
            camp.incrementAvailableSlots();
            CampDatabase.getInstance().update(camp);
            System.out.println("Deregistration is successful. Please do note that you are unable to register yourself to this camp again.");
            System.out.println("Press enter to go back.");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            StudentMainPage.studentMainPage(student);
        } catch (Exception e)
        {
            StudentMainPage.studentMainPage(student);
        }
    }

    private static void generateReport(Student student)
    {
        ChangePage.changePage();        
        System.out.println("1.Print all students");
        System.out.println("2.Print all camp attendees");
        System.out.println("3.Print all camp committee members");
        System.out.println("");
        System.out.println("Please Enter your choice: ");
        int choice = IntGetter.readInt();
        
        try
        {
            Camp camp = CampDatabase.getInstance().getByID(student.getCCId());
            switch(choice)
            {
                case 1 -> CampReportGenerator.generateCCReportAndWriteToFile(camp, "ALL");
                case 2 -> CampReportGenerator.generateCCReportAndWriteToFile(camp, "CAMP ATTENDEE");
                case 3 -> CampReportGenerator.generateCCReportAndWriteToFile(camp, "CC");
                default -> 
                {
                    System.out.println("Invalid choice. Please try again.");
                    new Scanner(System.in);
                    throw new PageBackException();
                }
            }
        } catch (PageBackException e)
        {
            generateReport(student);
        } catch (Exception e)
        {
            System.out.println("");
        }
        //CampReportGenerator.generateReportAndWriteToFile(campList);
        System.out.println("File has been written");
        System.out.println("Press Enter to go back");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        CCMainPage.ccMainPage(student);
    }

    private static void createSuggestion(Student student)
    {
        ChangePage.changePage();
        try
        {
            Camp camp = CampDatabase.getInstance().getByID(student.getCCId());

            CampViewer.viewAssignedCamp(student);
            System.out.println("");
            System.out.println("Enter the suggestion that you would like to suggest to the staff-in-charge: ");
            Scanner scanner = new Scanner(System.in);
            String suggestionMessage = scanner.nextLine();

            SuggestionManager.createSuggestion(suggestionMessage, "null", student.getID(), camp.getStaffID(), student.getCCId());
            System.out.println("Your suggestion has been created");
            System.out.println("Press Enter to continue");
            scanner.nextLine();
            CCMainPage.ccMainPage(student);
        } catch(PageBackException e)
        {
            CCMainPage.ccMainPage(student);

        } catch(UserAlreadyExistsException e)
        {
            System.out.println("");

        } catch(UserErrorException e)
        {
            System.out.println("User does not exist");
        }
    }

    /*private static void deleteSuggestion(Student student) throws PageBackException
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
    }*/
}

