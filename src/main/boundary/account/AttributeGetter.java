package main.boundary.account;

import main.utils.ui.PasswordReader;

import java.util.Scanner;

public class AttributeGetter 
{
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
