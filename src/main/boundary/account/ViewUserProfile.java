package main.boundary.account;

import main.model.user.Student;
import main.model.user.User;
import main.utils.exception.PageBackException;
import main.utils.ui.ChangePage;
import main.utils.ui.UserTypeGetter;

import java.util.Scanner;

/**
 * Handles the display of the user profiles, providing methods to view a single user's profile
 * or multiple user profiles
 */
public class ViewUserProfile 
{
    /**
     * Displays the user's profile.
     *
     * @param user The user whose profile is to be displayed.
     */
    public static void viewUserProfile(User user) 
    {
        String userType = UserTypeGetter.getUserTypeInCamelCase(user);
        System.out.println("Welcome to View " + userType + " Profile");
        System.out.println("┌--------------------------------------------------------------------------------------┐");
        System.out.printf("| %-15s | %-30s | %-15s | %-15s |\n", "Name", "Email", userType + " ID", "Role");
        System.out.println("|-----------------|--------------------------------|-----------------------------------|");
        System.out.printf("| %-15s | %-30s | %-15s | %-15s |\n", user.getUserName(), user.getEmail(), user.getID(), user.getUserType());
        System.out.println("└--------------------------------------------------------------------------------------┘");
    }

    /**
     * Displays the user's profile.
     *
     * @param user                  the user whose profile is to be displayed.
     * @throws PageBackException    if the user chooses to go back to the previous page.
     */
    public static void viewUserProfilePage(User user) throws PageBackException 
    {
        ChangePage.changePage();
        viewUserProfile(user);
        System.out.println("Press enter to go back.");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        throw new PageBackException();
    }

    public static void viewStudentProfile(Student student) 
    {
        String CC = "";

        if(!student.getCCId().equals("0"))
        {
            CC = student.getCCId();
        }

        String userType = UserTypeGetter.getUserTypeInCamelCase(student);
        System.out.println("Welcome to View " + userType + " Profile");
        System.out.println("┌--------------------------------------------------------------------------------------┐");
        System.out.printf("| %-15s | %-30s | %-15s | %-15s |\n", "Name", "Email", userType + " ID", "CC");
        System.out.println("|-----------------|--------------------------------|-----------------------------------|");
        System.out.printf("| %-15s | %-30s | %-15s | %-15s |\n", student.getUserName(), student.getEmail(), student.getID(), CC);
        System.out.println("└--------------------------------------------------------------------------------------┘");
    }

    /**
     * Displays the user's profile.
     *
     * @param user                  the user whose profile is to be displayed.
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