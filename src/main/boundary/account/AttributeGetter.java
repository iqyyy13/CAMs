package main.boundary.account;

import java.util.Scanner;
import main.model.user.UserType;
import main.utils.iocontrol.IntGetter;

/**
 * A utility class for obtaining user attributes such as role, password and User ID.
 */
public class AttributeGetter 
{
    
    /** 
     * Gets the user type (role) from the user by displaying options and receiving input
     * @return UserType The user type (role) selected by the user (STUDENT or STAFF)
     */
    public static UserType getRole() 
    {
        System.out.println("1. Student");
        System.out.println("2. Staff");
        System.out.print("Please select your domain (1-2): ");
        UserType userType = null;
        while (userType == null) {
            Scanner scanner = new Scanner(System.in);
            int domain;
            try {
                domain = IntGetter.readInt();
            } catch (Exception e) {
                System.out.println("Please enter a number.");
                continue;
            }
            userType = switch (domain) 
            {
                case 1 -> UserType.STUDENT;
                case 2 -> UserType.STAFF;
                default -> null;
            };
            if (userType == null) 
            {
                System.out.println("Invalid domain. Please try again.");
            }
        }
        return userType;
    }

    /**
     * Gets the user's password from the user by receiving input
     * 
     * @return  The user's password entered by the user
     */
    public static String getPassword() 
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter your password: ");
        return scanner.nextLine();
    }

    /**
     * Gets a password input from the user without displaying a prompt
     * 
     * @return The password entered by the user
     */
    public static String getPassword1() 
    {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * Gets the user's UserID from the user by displaying a prompt and receiving input
     * 
     * @return  The user's UserID entered by the user
     */
    public static String getUserID() 
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter your UserID: ");
        return scanner.nextLine();
    }    
    
}
