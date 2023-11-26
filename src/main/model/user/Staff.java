package main.model.user;

import main.utils.parameters.EmptyID;
import main.utils.parameters.NotNull;

import java.util.Map;

/**
 * Represents a staff member, a type of user
 */
public class Staff implements User
{
    /**
     * ID of a staff member
     */
    private String staffID;

    /**
     * Name of a staff member
     */
    private String staffName;

    /**
     * Email of a staff member
     */
    private String email;

    /**
     * Faculty of a staff member
     */
    private String faculty;
    
    /**
     * Password of a staff member
     */
    private String password;

    /**
     * UserType of a staff member
     */
    private UserType userType;

    /**
     * Constructs a new Staff object with the specified parameters
     * @param userType      UserType of the staff member
     * @param staffID       ID of the staff member
     * @param staffName     Name of the staff member
     * @param email         Email of the staff member
     * @param faculty       Faculty of the staff member
     */
    public Staff(UserType userType, String staffID, String staffName, String email, String faculty) 
    {
        this.userType = UserType.STAFF;
        this.staffID = staffID;
        this.staffName = staffName;
        this.email = email;
        this.faculty = faculty;
    }
    
    /**
     * Constructs a new Staff object with the specified parameters, including a password
     * @param userType      UserType of the staff member
     * @param staffID       ID of the staff member
     * @param staffName     Name of the staff member
     * @param email         Email of the staff member
     * @param faculty       Faculty of the staff member
     * @param password      Password of the staff member
     */
    public Staff(UserType userType, String staffID, String staffName, String email, String faculty, @NotNull String password) 
    {
        this.userType  = UserType.STAFF;
        this.staffID = staffID;
        this.staffName = staffName;
        this.email = email;
        this.faculty = faculty;
        this.password = password;
    }

    /**
     * Constructs a new staff object with information from the provided map
     * @param map containing information to create a new Staff object
     */
    public Staff(Map<String, String> map) 
    {
        this.fromMap(map);
    }

    /**
     * Default constructor for a staff class
     */
    public Staff() 
    {
        super();
        this.email = EmptyID.EMPTY_ID;
        this.staffID = EmptyID.EMPTY_ID;
        this.staffName = EmptyID.EMPTY_ID;
        this.faculty = EmptyID.EMPTY_ID;
        this.userType = UserType.STAFF;
        this.password = EmptyID.EMPTY_ID;
    }

    
    /** 
     * Gets a User object based on the information in the map.
     * @param map containing information to create a new Staff object.
     * @return A new Staff object with the information provided in the map.
     */
    public static User getUser(Map<String, String> map) 
    {
        return new Staff(map);
    }

    /**
     * Gets the staff ID of the user
     * @return The staff ID of the user
     */
    @Override
    public String getID() 
    {
        return this.staffID;
    }

    /**
     * Gets the username of the user
     * @return The username of the user
     */
    @Override
    public String getUserName() 
    {
        return this.staffName;
    }

    /**
     * Gets the email of the user
     * @return the email of the user
     */
    @Override
    public String getEmail() 
    {
        return this.email;
    }

    /**
     * Gets the faculty of the user
     * @return the faculty of the user
     */
    @Override
    public String getFaculty() 
    {
        return this.faculty;
    }

    /**
     * Gets the password of the user
     * @return the password of the user
     */
    public String getPassword() 
    {
        return password;
    }

    /**
     * Sets the password of the user
     * @param password The password to set
     */
    public void setPassword(String password) 
    {
        this.password = password;
    }

    /**
     * Gets the UserType of the user
     * @return the UserType of the user
     */
    public UserType getUserType() 
    {
        return userType;
    }
}
