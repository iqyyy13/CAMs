package main.controller.account;

import main.controller.account.password.PasswordManager;
import main.controller.account.user.UserAdd;
import main.controller.account.user.UserFind;
import main.controller.account.user.UserUpdate;
import main.model.user.User;
import main.model.user.UserType;
import main.model.user.UserCreate;
import main.utils.config.Location;
import main.utils.exception.PasswordIncorrectException;
import main.utils.exception.UserAlreadyExistsException;
import main.utils.exception.UserErrorException;
import main.utils.iocontrol.CSVReader;

import java.util.List;

/**
 * Manages user accounts, including password changes, login and registration
 */
public class AccountManager 
{
    
    /** 
     * Changes the password of a user
     * 
     * @param userType                      The type of the user (STUDENT or STAFF)
     * @param userID                        The ID of the user
     * @param oldPassword                   The old password
     * @param newPassword                   The new password
     * @throws PasswordIncorrectException   If the old password is incorrect
     * @throws UserErrorException           If an error occurs while updating the user
     */
    public static void changePassword(UserType userType, String userID, String oldPassword, String newPassword) 
    throws PasswordIncorrectException, UserErrorException 
    {
        User user = UserFind.findUser(userID, userType);
        PasswordManager.changePassword(user, oldPassword, newPassword);
        UserUpdate.updateUser(user);
    }

    /**
     * Logs in a user with the provided credentials
     * 
     * @param userType                      The type of the user (STUDENT or STAFF)
     * @param userID                        The ID of the user
     * @param password                      The password
     * @return                              The logged-in user
     * @throws PasswordIncorrectException   If the provided password is incorrect
     * @throws UserErrorException           If an error occurs while finding the user
     */
    public static User login(UserType userType, String userID, String password)
    throws PasswordIncorrectException, UserErrorException 
    {
        User user = UserFind.findUser(userID, userType);
        if (PasswordManager.checkPassword(user, password)) 
        {
            return user;
        } 
        else
        {
            throw new PasswordIncorrectException();
        }
    }

    /**
     * Registers a new user with the provided information
     * 
     * @param userType                      The type of the user (STUDENT or STAFF)
     * @param userID                        The ID of the user
     * @param password                      The password of the user
     * @param name                          The name of the user
     * @param email                         The email of the user
     * @param faculty                       The faculty of the user
     * @return                              The registered user
     * @throws UserAlreadyExistsException   If a user with the provided ID already exists
     */
    public static User register(UserType userType, String userID, String password, String name, String email, String faculty) 
    throws UserAlreadyExistsException 
    {
        User user = UserCreate.create(userType, userID, password, name, email, faculty);
        UserAdd.addUser(user);
        return user;
    }

    /**
     * Registers a new user with the provided information and a default password
     * @param userType                      The type of the user (STUDENT or STAFF)
     * @param userID                        The ID of the user
     * @param name                          The name of the user
     * @param email                         The email of the user
     * @param faculty                       The faculty of the user
     * @return                              The registered user
     * @throws UserAlreadyExistsException   If a user with the provided ID already exists
     */
    public static User register(UserType userType, String userID, String name, String email, String faculty) throws UserAlreadyExistsException 
    {
        return register(userType, userID, "password", name, email, faculty);
    }    


    /**
     * Extracts the user ID from an email address by removing the domain part
     * 
     * @param email The email address from which to extract the user ID
     * @return      The extracted user ID
     */
    private static String getID(String email) 
    {
        return email.split("@")[0];
    }

    /**
     * Loads student users from a CSV file
     */
    private static void loadStudents() 
    {
        List<List<String>> studentList = CSVReader.read(Location.RESOURCE_LOCATION + "/resources/StudentList.csv", true);
        for (List<String> row : studentList) 
        {
            String name = row.get(0);
            String email = row.get(1);
            String faculty = row.get(2);
            String userID = getID(email);

            //System.out.println("Registering student:");
            //System.out.println("Name: " + name);
            //System.out.println("Email: " + email);
            //System.out.println("Faculty: " + faculty);
            //System.out.println("UserID: " + userID);
            
            try 
            {
                register(UserType.STUDENT, userID, name, email, faculty);
                System.out.println("Registration Success");
                //System.out.println("");
            } 
            catch (UserAlreadyExistsException e) 
            {
                System.out.println("User already exists. Skipping...");
                e.printStackTrace();
            }
        }
    }

    /**
     * Loads staff isers from a CSV file
     */
    private static void loadStaff() 
    {
        List<List<String>> staffList = CSVReader.read(Location.RESOURCE_LOCATION + "/resources/StaffList.csv", true);
        for (List<String> row : staffList) 
        {
            String name = row.get(0);
            String email = row.get(1);
            String faculty = row.get(2);
            String userID = getID(email);

            //System.out.println("Registering staff:");
            //System.out.println("Name: " + name);
            //System.out.println("Email: " + email);
            //System.out.println("Faculty: " + faculty);
            //System.out.println("UserID: " + userID);
            
            try 
            {
                register(UserType.STAFF, userID, name, email, faculty);
                System.out.println("Registration Success");
                //System.out.println("");
            } 
            catch (UserAlreadyExistsException e) 
            {
                System.out.println("User already exists. Skipping...");
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Loads staff and students users from a CSV file
     */
    public static void loadUsers()
    {
        loadStaff();
        loadStudents();
    }
}
