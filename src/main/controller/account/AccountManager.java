package main.controller.account;

import main.controller.account.user.UserAdder;
import main.model.user.User;
import main.model.user.UserFactory;
import main.utils.config.Location;
import main.utils.exception.PasswordIncorrectException;
import main.utils.exception.UserAlreadyExistsException;
import main.utils.exception.UserErrorException;
import main.utils.iocontrol.CSVReader;

import java.util.List;

public class AccountManager 
{

    public static User login(String userID, String password)
            throws PasswordIncorrectException, UserErrorException {
        User user = UserFinder.findUser(userID);
//        System.err.println("User found: " + user.getUserName() + " " + user.getID());
        if (PasswordManager.checkPassword(user, password)) {
            return user;
        } else {
            throw new PasswordIncorrectException();
        }
    }

    public static User register(String userID, String password, String name, String email, String faculty)
            throws UserAlreadyExistsException {
        User user = UserFactory.create(userID, password, name, email, faculty);

        if("password".equals(password))
        {
            System.out.println("WARNING");
        }
        UserAdder.addUser(user);
        return user;
    }

    public static User register(String userID, String name, String email, String faculty)
            throws UserAlreadyExistsException {
//        if (userType == UserType.COORDINATOR) {
//            System.err.println("Registering coordinator...");
//            System.err.println("Coordinator ID: " + userID);
//            System.err.println("Coordinator name: " + name);
//            System.err.println("Coordinator email: " + email);
//        }
        return register(userID, "password", name, email, faculty);
    }    
    private static String getID(String email) 
    {
        return email.split("@")[0];
    }

    private static void loadStudents() {
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
                register(userID, name, email, faculty);
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
    }
    
}
