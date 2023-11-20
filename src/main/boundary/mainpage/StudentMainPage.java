package main.boundary.mainpage;

import java.util.Scanner;

//import javax.management.RuntimeErrorException;

import main.boundary.account.Logout;
import main.boundary.account.ResetPassword;
import main.boundary.account.ViewUserProfile;
import main.boundary.modelviewer.CampViewer;
import main.boundary.modelviewer.ModelViewer;
import main.controller.enquiry.EnquiryManager;
//import main.controller.account.AccountManager;
import main.controller.camp.CampManager;
//import main.controller.request.StudentManager;
//import main.database.enquiry.EnquiryDatabase;
import main.database.user.StudentDatabase;
import main.database.camp.CampDatabase;
//import main.model.enquiry.Enquiry;
import main.model.camp.Camp;
import main.model.camp.CampStatus;
import main.model.enquiry.Enquiry;
import main.model.user.*;
import main.utils.exception.UserErrorException;
import main.utils.exception.PageBackException;
import main.utils.iocontrol.IntGetter;
//import main.utils.parameters.EmptyID;
import main.utils.ui.ChangePage;

public class StudentMainPage {
    /**
     * This method displays the main page of a student. It takes a User object as a parameter and displays a menu of options for the student to choose from. The user's choice is then processed using a switch statement, which calls different methods based on the choice.
     *
     * @param user The user object of the student.
     */
    public static void studentMainPage(User user) {
        if (user instanceof Student student) 
        {
            ChangePage.changePage();
            System.out.println("Welcome to Student Main Page");
            System.out.println("Hello, " + student.getUserName() + "!");
            System.out.println();
            System.out.println("\t1. View my profile");
            System.out.println("\t2. Change my password");
            System.out.println("\t3. View camps");
            System.out.println("\t4. View registered camps");
            System.out.println("\t5. Register for a camp");
            System.out.println("\t6. Withdraw from a camp");
            System.out.println("\t7. New enquiry");
            System.out.println("\t8. View enquiry");
            System.out.println("\t9. Edit enquiry");
            System.out.println("\t10. Delete Enquiry");
            System.out.println("\t11. Switch to CC Main Page");
            System.out.println("\t12. Logout");

            System.out.println();
            System.out.print("Please enter your choice: ");

            int choice = IntGetter.readInt();

            // refresh Enquiry DB
            EnquiryManager.refresh_enquiry_db();

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
                    case 7 -> EnquiryManager.new_enquiry(student.getRegisteredCampIDs(),student.getID());
                    case 8 -> EnquiryManager.view_enquiry(student.getID());
                    case 9 -> EnquiryManager.edit_enquiry(null);
                    case 10 -> EnquiryManager.delete_enquiry(null);
                    case 11 -> verifyCC(student);
                    case 12 -> Logout.logout();
                    // case 13 -> EnquiryManager.reply_enquiry(null);
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
     * @param student
     * @throws PageBackException
     */
    private static void registerCamp(Student student) throws PageBackException
    {
        ChangePage.changePage();
        System.out.println("Here is the list of camps that are available for you to join.");
        ModelViewer.displayListOfDisplayable(CampManager.getAllAvailableCamps());
        System.out.println("Please enter the Camp ID that you would like to register: ");
        String campID = new Scanner(System.in).nextLine().trim().toUpperCase();

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

    private static void verifyCC(Student student)
    {
        if(student.getCCId().equals("0"))
        {
            System.out.println("You are not a Camp Committee member of any camp.");
            System.out.print("Press Enter to go back: ");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            StudentMainPage.studentMainPage(student);
        }
        else
        {
            System.out.println("You are a Camp Committee member of Camp " + student.getCCId());
            System.out.print("Press Enter to go to CC Main Page: ");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            CCMainPage.ccMainPage(student);
        }
    }
}

