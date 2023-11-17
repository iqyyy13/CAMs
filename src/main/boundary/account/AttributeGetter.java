package main.boundary.account;

//import main.utils.ui.PasswordReader;

import java.util.Scanner;

import main.model.user.UserType;
import main.utils.iocontrol.IntGetter;

public class AttributeGetter 
{
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

    public static String getPassword() 
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter your password: ");
        return scanner.nextLine();
    }

    public static String getUserID() 
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter your UserID (Press enter if you forget): ");
        return scanner.nextLine();
    }    
    
}
