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

public class Student implements User, Displayable
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

    public Student(Map<String, String> informationMap) 
    {
        fromMap(informationMap);
    }

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

    public String getDeregisteredCampIDs()
    {
        return deregisteredCampIDs;
    }

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
                if(this.registeredCampIDs == null)
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

    public void deregisterCamp(Student student, String campID)
    {
        try
        {
            this.registeredCampIDs = this.registeredCampIDs.replace(campID, "").replace(",,",",").trim();

            if(this.deregisteredCampIDs == null)
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

    private String getStudentInformationString() 
    {
        return "|------------------------------------------------------|\n" +
               String.format("| Name                    | %-26s |\n", getUserName()) +
               String.format("| StudentID               | %-26s |\n", getID()) +
               String.format("| Email                   | %-26s |\n", getEmail()) +
               String.format("| Role                    | %-26s |\n", getUserType());
    }
    
    @Override
    public String getDisplayableString() 
    {
        return getStudentInformationString();
    }

    @Override
    public String getSplitter() 
    {
        return "========================================================";
    }
}
