package main.boundary.mainpage;

import java.util.Scanner;

import main.boundary.account.Logout;
import main.boundary.account.ResetPassword;
import main.boundary.account.ViewUserProfile;
import main.boundary.modelviewer.CampViewer;
import main.boundary.modelviewer.ModelViewer;
import main.controller.account.AccountManager;
import main.controller.request.StudentManager;
import main.model.user.*;
import main.repository.user.StudentDatabase;
import main.utils.exception.UserErrorException;
import main.utils.exception.PageBackException;
import main.utils.iocontrol.IntGetter;
import main.utils.parameters.EmptyID;
import main.utils.ui.ChangePage;

public class StudentMainPage {
    /**
     * This method displays the main page of a student. It takes a User object as a parameter and displays a menu of options for the student to choose from. The user's choice is then processed using a switch statement, which calls different methods based on the choice.
     *
     * @param user The user object of the student.
     */
    public static void studentMainPage(User user) {
        if (user instanceof Student student) {
            ChangePage.changePage();
            System.out.println("Welcome to Student Main Page");
            System.out.println("Hello, " + student.getUserName() + "!");
            System.out.println();
            System.out.println("\t1. View my profile");
            System.out.println("\t2. Change my password");
            System.out.println("\t3. View camps");
            System.out.println("\t4. Register for a camp");
            System.out.println("\t5. Withdraw from a camp");
            System.out.println("\t6. View enquiry");
            System.out.println("\t7. Edit enquiry");
            System.out.println("\t8. Delete Enquiry");
            System.out.println("\t9. Logout");

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
                    //case 4 -> ProjectViewer.viewStudentProject(student);
                    //case 5 -> viewMySupervisor(student);
                    //case 6 -> registerProject(student);
                    //case 7 -> deregisterForProject(student);
                    //case 8 -> changeTitleForProject(student);
                    case 9 -> Logout.logout();
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
}

