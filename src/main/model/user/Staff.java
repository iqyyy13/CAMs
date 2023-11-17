package main.model.user;

import main.utils.parameters.EmptyID;
import main.utils.parameters.NotNull;

import java.util.Map;

public class Staff implements User
{
    private String staffID;

    private String staffName;

    private String email;

    private String faculty;

    private String campID;
    private String password;

    private UserType userType;

    public Staff(UserType userType, String staffID, String staffName, String email, String faculty) 
    {
        this.userType = UserType.STAFF;
        this.staffID = staffID;
        this.staffName = staffName;
        this.email = email;
        this.faculty = faculty;
        campID = EmptyID.EMPTY_ID;
    }
    
    public Staff(UserType userType, String staffID, String staffName, String email, String faculty, @NotNull String password) 
    {
        this.userType  = UserType.STAFF;
        this.staffID = staffID;
        this.staffName = staffName;
        this.email = email;
        this.faculty = faculty;
        campID = EmptyID.EMPTY_ID;
        this.password = password;
    }

    public Staff(Map<String, String> informationMap) 
    {
        fromMap(informationMap);
    }

    public Staff() 
    {
        super();
        this.email = EmptyID.EMPTY_ID;
        this.staffID = EmptyID.EMPTY_ID;
        this.staffName = EmptyID.EMPTY_ID;
        this.faculty = EmptyID.EMPTY_ID;
        this.userType = UserType.STUDENT;
        //this.password = EmptyID.EMPTY_ID;
    }

    public static User getUser(Map<String, String> informationMap) 
    {
        return new Staff(informationMap);
    }

    @Override
    public String getID() 
    {
        return this.staffID;
    }

    @Override
    public String getUserName() 
    {
        return this.staffName;
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
}
