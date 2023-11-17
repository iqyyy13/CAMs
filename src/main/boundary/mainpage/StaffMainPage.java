package main.boundary.mainpage;

import java.util.Scanner;

import main.boundary.account.Logout;
import main.boundary.account.ResetPassword;
import main.boundary.account.ViewUserProfile;
import main.boundary.modelviewer.ModelViewer;
import main.controller.account.AccountManager;
import main.controller.request.StudentManager;
import main.model.user.*;
import main.repository.user.StaffRepository;
import main.utils.exception.UserErrorException;
import main.utils.exception.PageBackException;
import main.utils.iocontrol.IntGetter;
import main.utils.parameters.EmptyID;
import main.utils.ui.ChangePage;

public class StaffMainPage {
    /**
     * This method displays the main page of a student. It takes a User object as a parameter and displays a menu of options for the student to choose from. The user's choice is then processed using a switch statement, which calls different methods based on the choice.
     *
     * @param user The user object of the student.
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
            System.out.println("\t6. Edit camp");
            System.out.println("\t7. Delete camp");
            System.out.println("\t8. View all pending enquiries");
            System.out.println("\t9. Reply enquiries");
            System.out.println("\t10. View all pending suggestions");
            System.out.println("\t11. Approve/Reject suggestions");
            System.out.println("\t12. Generate report of students");
            System.out.println("\t13. Generate performance report of CCs");
            System.out.println("\t14. Logout");

            System.out.println();
            System.out.print("Please enter your choice: ");

            int choice = IntGetter.readInt();

            try {
                staff = StaffRepository.getInstance().getByID(staff.getID());
            } catch (UserErrorException e) {
                e.printStackTrace();
            }

            try {
                switch (choice) {
                    case 1 -> ViewUserProfile.viewUserProfilePage(staff);
                    case 2 -> ResetPassword.changePassword(UserType.STAFF, staff.getID());
                    //case 3 -> ProjectViewer.viewAvailableProjectList(student);
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
                StudentMainPage.studentMainPage(staff);
            }


        } else {
            throw new IllegalArgumentException("User is not a student.");
        }
    }
}
