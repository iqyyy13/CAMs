package main.model.user;

import main.boundary.mainpage.StudentMainPage;
import main.database.camp.CampDatabase;
import main.database.user.StudentDatabase;
import main.model.Displayable;
import main.model.camp.Camp;
import main.utils.exception.PageBackException;
import main.utils.parameters.EmptyID;
import main.utils.parameters.NotNull;

import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a student, a type of user
 * Extends the User and Displayable class
 * Includes a student ID field
 */
public class Student implements User, Displayable
{
    /**
     * ID of a student
     */
    private String studentID;
    
    /**
     * Name of a student
     */
    private String studentName;

    /**
     * Email of a student
     */
    private String email;

    /**
     * Faculty of a student
     */
    private String faculty;

    /**
     * Status of a student
     */
    private StudentStatus status;

    /**
     * ID of a staff
     */
    private String staffID;

    /**
     * ID of a camp
     */
    private String campID;

    /**
     * Password of a student
     */
    private String password;

    /**
     * userType of a student
     */
    private UserType userType;

    /**
     * The campIDs that the student has registered
     */
    private String registeredCampIDs;

    /**
     * The campIDs that the student has deregistered
     */
    private String deregisteredCampIDs;

    /**
     * The campID that the student is a CC of
     */
    private String CC;

    /**
     * Constructs a new Student object with the student ID and default password
     * @param userType          usertype of the student
     * @param studentID         ID of the student
     * @param studentName       name of the student
     * @param email             email of the student
     * @param faculty           faculty of the student
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
        this.CC = "0";
    }

    /**
     * Constructs a new Student object with the specified student ID and password
     * @param userType          usertype of the student
     * @param studentID         ID of the student
     * @param studentName       name of the student
     * @param email             email of the student
     * @param faculty           faculty of the student
     * @param password          password of the student
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
        this.CC = "0";
        this.registeredCampIDs = "0";
        this.deregisteredCampIDs = "0";
    }

    /**
     * Constructs a new student object with the specified student ID and password
     * @param informationMap
     */
    public Student(Map<String, String> informationMap) 
    {
        fromMap(informationMap);
    }

    /**
     * default constructor for a Student class
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
        this.CC = "0";
    }

    /**
     * Creates a new Student object based on the information in the map
     * Map contains necessary information to create a Student object such as
     * the Student's name, email, studentID and faculty
     * @param informationMap a map containing information required to create a new Student object
     * @return a new Student object with information provided in the map
     */
    public static User getUser(Map<String, String> informationMap) 
    {
        return new Student(informationMap);
    }

    
    /** 
     * Gets the studentID of the user
     * @return the studentID of the user
     */
    @Override
    public String getID() 
    {
        return this.studentID;
    }

    /**
     * Gets the username of the user
     * @return the username of the user
     */
    @Override
    public String getUserName() 
    {
        return this.studentName;
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
     * Gets the status of the user
     * @return the status of the user
     */
    public StudentStatus getStatus() 
    {
        return this.status;
    }

    /**
     * Sets the status of the user
     * @param status The status to set the user
     */
    public void setStatus(StudentStatus status) 
    {
        this.status = status;
    }

    /**
     * Gets the campID of the user
     * @return the campID of the user
     */
    public String getCampID() 
    {
        return campID;
    }

    /**
     * Sets the campID of the user
     * @param campID The campID to set for the user
     */
    public void setCampID(String campID) 
    {
        this.campID = campID;
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
     * @param password The password to set for the user
     */
    public void setPassword(String password) 
    {
        this.password = password;
    }

    /**
     * Gets the userType of the user
     */
    public UserType getUserType() 
    {
        return userType;
    }

    /**
     * Gets the staffID of the user
     * @return the staffID of the user
     */
    public String getStaffID()
    {
        return staffID;
    }

    /**
     * Sets the staffID of the user
     * @param staffID The staffID to set for the user
     */
    public void setStaffID(String staffID)
    {
        this.staffID = staffID;
    }

    /**
     * Gets the CampIDs that the student has registered
     * @return the CampIDs that the student has registered
     */
    public String getRegisteredCampIDs()
    {
        return registeredCampIDs;
    }

    /**
     * Gets the CampIDs that the student has deregistered from
     * @return the CampIDs that the student has deregistered
     */
    public String getDeregisteredCampIDs()
    {
        return deregisteredCampIDs;
    }

    /**
     * Gets the CampID that the student is a CC of
     * @return the CampID that the student is a CC of
     */
    public String getCCId()
    {
        return this.CC;
    }

    /**
     * Sets the CampID that the student is a cc of
     * @param campID the CampID that the student has registered to be a CC of
     * @return the CampID that the student is a CC of
     */
    public String setCCId(String campID)
    {
        return this.CC = campID;
    }

    /**
     * Registers the student for a camp
     * @param student The student to register
     * @param campID The ID of the camp to register for
     */
    public void registerCamp(Student student, String campID)
    {
        try
        {
            if(isCampAlreadyRegistered(campID))
            {
                System.out.println("You have already registered for this camp. Cannot register again.");
                System.out.println("Press Enter to go back.");
                Scanner scanner = new Scanner(System.in);
                scanner.nextLine();
                StudentMainPage.studentMainPage(student);
            }
            else if(isCampAlreadyDeregistered(campID))
            {
                System.out.println("You have already deregistered for this camp. Cannot register again.");
                System.out.println("Press Enter to go back.");
                Scanner scanner = new Scanner(System.in);
                scanner.nextLine();
                StudentMainPage.studentMainPage(student);                
            }
            else
            {
                if(registeredCampIDs.equals("0"))
                {
                    this.registeredCampIDs = campID;
                    Camp camp = CampDatabase.getInstance().getByID(campID);
                    camp.storeStudentID(student, camp);
                }
                else if (!this.registeredCampIDs.contains(campID)) 
                {
                    this.registeredCampIDs += "," + campID;
                    Camp camp = CampDatabase.getInstance().getByID(campID);
                    camp.storeStudentID(student, camp);
                }
                StudentDatabase.getInstance().update(student);
            }
        } catch(Exception e)
        {
            System.out.println("CampID cannot find");
        }
    }

    /**
     * Deregisters the student from a camp
     * @param student The student to deregister
     * @param campID The ID of a camp to deregister from
     */
    public void deregisterCamp(Student student, String campID)
    {
        try
        {
            this.registeredCampIDs = this.registeredCampIDs.replace(campID, "").replace(",,",",").trim();
            if(this.registeredCampIDs.isEmpty())
            {
                this.registeredCampIDs = "0";
            }

            if(deregisteredCampIDs.equals("0"))
            {
                this.deregisteredCampIDs = campID;
                Camp camp = CampDatabase.getInstance().getByID(campID);
                camp.removeStudentID(student, camp);
            }
            else if(!this.deregisteredCampIDs.contains(campID))
            {
                this.deregisteredCampIDs += "," + campID;
                Camp camp = CampDatabase.getInstance().getByID(campID);
                camp.removeStudentID(student, camp);
            }
            StudentDatabase.getInstance().update(student);
        } catch(Exception e)
        {
            System.out.println("CampID cannot find");
        }
    }

    /**
     * Verify whether the Student has registered the campID before
     * @param campID The ID of a camp to verify with
     * @return True if camp is registered
     */
    private boolean isCampAlreadyRegistered(String campID)
    {
        if(registeredCampIDs != null && !registeredCampIDs.isEmpty())
        {
            String[] campIDs = registeredCampIDs.split(",");
            for(String registeredCampID : campIDs)
            {
                if(registeredCampID.trim().equals(campID.trim()))
                {
                    System.out.println("Camp registered before: " + campID);
                    return true; //camp is registered
                }
            }
        }
        
        return false;
    }

    /**
     * Verify whether the Student has deregistered the campID before
     * @param campID The ID of a camp to check verify with
     * @return True if camp is deregistered
     */
    private boolean isCampAlreadyDeregistered(String campID)
    {
        if(deregisteredCampIDs != null && !deregisteredCampIDs.isEmpty())
        {
            String[] campIDs = deregisteredCampIDs.split(",");
            for(String deregisteredCampID : campIDs)
            {
                if(deregisteredCampID.trim().equals(campID.trim()))
                {
                    System.out.println("Camp deregistered before: " + campID);
                    return true; //camp is deregistered, student can't register for this camp
                }
            }
        }
        return false;
    }

    /**
     * Prints the relevant information of a student
     * @return the relevenat information of a student
     */
    private String getStudentInformationString() 
    {
        return "|------------------------------------------------------|\n" +
               String.format("| Name                    | %-26s |\n", getUserName()) +
               String.format("| StudentID               | %-26s |\n", getID()) +
               String.format("| Email                   | %-26s |\n", getEmail()) +
               String.format("| Role                    | %-26s |\n", getRoleDisplay());
    }
    
    /**
     * Gets the String to display
     */
    @Override
    public String getDisplayableString() 
    {
        return getStudentInformationString();
    }

    /**
     * Splits the String
     */
    @Override
    public String getSplitter() 
    {
        return "========================================================";
    }

    /**
     * Register a student as a committee member for a camp
     * @param student The student to register as a committee member
     * @param camp    The camp for which the student is registering as a committee member
     */
    public void registerAsCC(Student student, Camp camp)
    {
        if("0".equals(getCCId()))
        {
            try
            {
                String campID = camp.getID();
                student.setCCId(campID);
                StudentDatabase.getInstance().update(student);
                System.out.println("Student successfully registered as a committee member for CampID: " + campID);
            } catch(Exception e)
            {
                System.out.println("");
            }
        }
        else
        {
            System.out.println("Student is already registered as a committee member for CampID " + this.CC);
            System.out.println("Press enter to go back.");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            StudentMainPage.studentMainPage(student);
        }
    }

    /**
     * Gets the role display of the USER (either "STUDENT" or "CC")
     * @return The role display of the user.
     */
    public String getRoleDisplay()
    {
        if("0".equals(getCCId()))
        {
            String string = "STUDENT";
            return string;
        }
        else
        {
            String string = "CC";
            return string;
        }
    }
}
