package main.model.user;

import main.database.user.StudentDatabase;
import main.utils.exception.PageBackException;
import main.utils.parameters.EmptyID;
import main.utils.parameters.NotNull;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a student, which is a type of user.
 * It extends the User class and includes a student ID field.
 */
public class Student implements User 
{
    private String studentID;

    private String studentName;

    private String email;

    private String faculty;

    private StudentStatus status;

    private String staffID;

    private String campID;
    private String password;

    private UserType userType;

    private String registeredCampIDs;
    private String deregisteredCampIDs;

    /**
     * Constructs a new Student object with the specified student ID and default password.
     *
     * @param studentID   the ID of the student.
     * @param studentName the name of the student.
     * @param email       the email of the student.
     * @param faculty     the faculty of the student
     */
    public Student(UserType userType, String studentID, String studentName, String email, String faculty) 
    {
        this.userType = UserType.STUDENT;
        this.studentID = studentID;
        this.studentName = studentName;
        this.email = email;
        this.faculty = faculty;
        this.status = StudentStatus.UNREGISTERED;
        staffID = EmptyID.EMPTY_ID;
        campID = EmptyID.EMPTY_ID;
        this.registeredCampIDs = "";
        this.deregisteredCampIDs = "";
    }

    /**
     * Constructs a new Student object with the specified student ID and password.
     *
     * @param studentID      the ID of the student.
     * @param studentName    the name of the student.
     * @param email          the email of the student.
     * @param faculty        the faculty of the student
     * @param password       the password of the student.
     */
    public Student(UserType userType, String studentID, String studentName, String email, String faculty, @NotNull String password) {
        this.userType  = UserType.STUDENT;
        this.studentID = studentID;
        this.studentName = studentName;
        this.email = email;
        this.faculty = faculty;
        this.status = StudentStatus.UNREGISTERED;
        staffID = EmptyID.EMPTY_ID;
        campID = EmptyID.EMPTY_ID;
        this.password = password;
    }

    /**
     * Constructs a new Student object with the specified student ID and password.
     *
     * @param informationMap the map
     */
    public Student(Map<String, String> informationMap) 
    {
        fromMap(informationMap);
    }

    /**
     * default constructor for Student class
     */
    public Student() 
    {
        super();
        this.email = EmptyID.EMPTY_ID;
        this.studentID = EmptyID.EMPTY_ID;
        this.studentName = EmptyID.EMPTY_ID;
        this.faculty = EmptyID.EMPTY_ID;
        this.userType = UserType.STUDENT;
        //this.password = EmptyID.EMPTY_ID;
        this.status = StudentStatus.UNREGISTERED;
    }

    /**

     Creates a new Student object based on the information in the provided map.
     The map should contain the necessary information to construct the Student object,
     such as the student's name, email, and ID.
     @param informationMap a map containing the information required to create a new Student object
     @return a new Student object with the information provided in the map
     */
    public static User getUser(Map<String, String> informationMap) 
    {
        return new Student(informationMap);
    }

    @Override
    public String getID() 
    {
        return this.studentID;
    }

    @Override
    public String getUserName() 
    {
        return this.studentName;
    }

    @Override
    public String getEmail() 
    {
        return this.email;
    }

    @Override
    public String getFaculty() 
    {
        return this.faculty;
    }

    public StudentStatus getStatus() 
    {
        return this.status;
    }

    public void setStatus(StudentStatus status) 
    {
        this.status = status;
    }

    public String getCampID() 
    {
        return campID;
    }

    public void setCampID(String campID) 
    {
        this.campID = campID;
    }

    public String getPassword() 
    {
        return password;
    }

    public void setPassword(String password) 
    {
        this.password = password;
    }

    public UserType getUserType() 
    {
        return userType;
    }

    public String getStaffID()
    {
        return staffID;
    }

    public void setStaffID(String staffID)
    {
        this.staffID = staffID;
    }

    public String getRegisteredCampIDs()
    {
        return registeredCampIDs;
    }

    public void registerCamp(Student student, String campID)
    {
        try
        {
            if(this.registeredCampIDs == null)
            {
                this.registeredCampIDs = campID;
                StudentDatabase.getInstance().update(student);
            }
            else if (!this.registeredCampIDs.contains(campID)) 
            {
                this.registeredCampIDs += "," + campID;
                StudentDatabase.getInstance().update(student);
                
            }

            //StudentDatabase.getInstance().update(student);
        } catch(Exception e)
        {
            System.out.println("CampID cannot find");
        }
    }

    public void deregisterCamp(Student student, String campID)
    {
        try
        {
            this.registeredCampIDs = this.registeredCampIDs.replace(campID, "").replace(",,",",").trim();

            if(this.deregisteredCampIDs == null)
            {
                this.deregisteredCampIDs = campID;
                StudentDatabase.getInstance().update(student);
            }
            else if(!this.deregisteredCampIDs.contains(campID))
            {
                this.deregisteredCampIDs += "," + campID;
                StudentDatabase.getInstance().update(student);
            }
        } catch(Exception e)
        {
            System.out.println("CampID cannot find");
        }
    }
}
