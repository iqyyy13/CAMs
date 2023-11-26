package main.boundary.account;

import main.model.user.Student;
import main.model.user.User;
import main.utils.exception.PageBackException;
import main.utils.ui.ChangePage;
import main.utils.ui.UserTypeGetter;

import java.util.Scanner;

/**
 * Provides methods for viewing and displaying student profiles.
 */
public class ViewStudentProfile 
{
    /**
     * Displays the profile of the given student
     * 
     * @param student   The student whose profile is to be displayed.
     */
    public static void viewStudentProfile(Student student) 
    {
        String CC = "";

        if(!student.getCCId().equals("0"))
        {
            CC = student.getCCId();
        }

        String userType = UserTypeGetter.getUserTypeInCamelCase(student);
        System.out.println("Welcome to View " + userType + " Profile");
        System.out.println("┌---------------------------------------------------------------------------------------------------------------------┐");
        System.out.printf("| %-15s | %-30s | %-15s | %-15s | %-15s | %-10s |\n", "Name", "Email", "Faculty", userType + " ID", "CC", "Points");
        System.out.println("|-----------------|--------------------------------|-----------------|-----------------|------------------------------|");
        System.out.printf("| %-15s | %-30s | %-15s | %-15s | %-15s | %-10s |\n", student.getUserName(), student.getEmail(), student.getFaculty(), student.getID(), CC, student.getPoints());
        System.out.println("└---------------------------------------------------------------------------------------------------------------------┘");
    }

    /**
     * Displays the student's profile and allows them to go back
     *
     * @param student               The student whose profile is to be displayed
     * @throws PageBackException    if the user chooses to go back to the previous page.
     */
    public static void viewStudentProfilePage(Student student) throws PageBackException 
    {
        ChangePage.changePage();
        viewStudentProfile(student);
        System.out.println("Press enter to go back.");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        throw new PageBackException();
    }
}
