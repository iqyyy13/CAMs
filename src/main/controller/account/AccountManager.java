package main.controller.account;

import main.controller.account.password.PasswordManager;
import main.controller.account.user.UserAdd;
import main.controller.account.user.UserFind;
import main.controller.account.user.UserUpdate;
import main.model.user.User;
import main.model.user.UserType;
import main.model.user.UserFactory;
import main.utils.config.Location;
import main.utils.exception.PasswordIncorrectException;
import main.utils.exception.UserAlreadyExistsException;
import main.utils.exception.UserErrorException;
import main.utils.iocontrol.CSVReader;

import java.util.List;

public class AccountManager 
{
    public static void changePassword(UserType userType, String userID, String oldPassword, String newPassword)
            throws PasswordIncorrectException, UserErrorException {
        User user = UserFind.findUser(userID, userType);
        PasswordManager.changePassword(user, oldPassword, newPassword);
        UserUpdate.updateUser(user);
    }

    public static User login(UserType userType, String userID, String password)
            throws PasswordIncorrectException, UserErrorException {

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

    public static User register(UserType userType, String userID, String password, String name, String email, String faculty)
            throws UserAlreadyExistsException {
        User user = UserFactory.create(userType, userID, password, name, email, faculty);

        if(userType.equals(userType.STUDENT))
        {
            System.out.println("YAY");
        }

        if("password".equals(password))
        {
            System.out.println("WARNING");
        }
        UserAdd.addUser(user);
        return user;
    }

    public static User register(UserType userType, String userID, String name, String email, String faculty)
            throws UserAlreadyExistsException {

        return register(userType, userID, "password", name, email, faculty);
    }    

    private static String getID(String email) 
    {
        return email.split("@")[0];
    }

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

    private static void loadStaff() 
    {
        List<List<String>> staffList = CSVReader.read(Location.RESOURCE_LOCATION + "/resources/StaffList.csv", true);
        for (List<String> row : staffList) 
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
    
    public static void loadUsers()
    {
        loadStudents();
        loadStaff();
    }
}
