package main.model.camp;

import main.database.camp.CampDatabase;
import main.database.user.StaffDatabase;
import main.database.user.StudentDatabase;
import main.model.Displayable;
import main.model.Model;
import main.model.user.Student;
import main.model.user.Staff;
import main.utils.exception.UserErrorException;
import main.utils.parameters.EmptyID;

import java.util.Map;

public class Camp implements Model, Displayable 
{
    CampStatus status;

    private String campID;

    private String staffID;

    private String studentID;

    private String campTitle;

    private String faculty;

    private int maxSlots;

    private int maxCCSlots;

    private int availableSlots;

    private int availableCCSlots;
    
    private String location;

    private String description;

    public Camp(String campID, String campTitle, String staffID, String faculty, String location, String description) 
    {
        this.campID = campID;
        this.campTitle = campTitle;
        this.staffID = staffID;
        this.faculty = faculty;
        this.location = location;
        this.studentID = EmptyID.EMPTY_ID;
        this.status = CampStatus.AVAILABLE;
        this.maxSlots = 20;
        this.maxCCSlots = 10;
        this.description = description;
        this.availableSlots = maxSlots;
        this.availableCCSlots = maxCCSlots;

    }

    public Camp(Map<String, String> map) 
    {
        fromMap(map);
    }

    private void displayStaffInformation(String staffID) 
    {
        try 
        {
            Staff staff = StaffDatabase.getInstance().getByID(staffID);
            System.out.println("Staff Name: " + staff.getUserName());
            System.out.println("Staff Email Address: " + staff.getEmail());
        } catch (UserErrorException e) {
            System.out.println("No Staff Yet");
        }
    }

    private void displayStudentInformation() {
        try {
            Student student = StudentDatabase.getInstance().getByID(studentID);
            System.out.println("Student Name: " + student.getUserName());
            System.out.println("Student Email Address: " + student.getEmail());
        } catch (UserErrorException e) {
            System.out.println("No Student Yet");
        }
    }

    private void displayCampID() 
    {
        System.out.println("Camp ID: " + campID);
    }

    private void displayCampInformation() 
    {
        System.out.println("Camp Title: " + campTitle);
        System.out.println("Camp Status: " + status);
    }

    public void displayCamp() 
    {
        displayCampID();
        displayStaffInformation(this.staffID);
        displayCampInformation();
    }

    public void assignStudent(String studentID) throws IllegalStateException 
    {
        if (status != CampStatus.AVAILABLE) 
        {
            throw new IllegalStateException("Camp is not available for allocation.");
        }
        this.studentID = studentID;
        this.status = CampStatus.ALLOCATED;
    }

    public String getStudentID() 
    {
        return studentID;
    }

    public String getStaffID() 
    {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getCampTitle() {
        return campTitle;
    }

    public void setCampTitle(String campTitle) 
    {
        this.campTitle = campTitle;
    }

    public CampStatus getStatus() 
    {
        return status;
    }

    public void setStatus(CampStatus status) 
    {
        this.status = status;
    }

    @Override
    public String getID() {
        return campID;
    }

    public String getFaculty() 
    {
        return this.faculty;
    }

    public int getMaxSlots()
    {
        return maxSlots;
    }

    public int getCCMaxSlots()
    {
        return maxCCSlots;
    }

    public int getAvailableSlots()
    {
        return availableSlots;
    }

    public int getAvailableCCSlots()
    {
        return availableCCSlots;
    }

    public String getLocation()
    {
        return location;
    }

    public String getDescription()
    {
        return description;
    }

    public void decrementAvailableSlots()
    {
        if(availableSlots > 0)
        {
            availableSlots--;
            System.out.println("The number of available slots now are : " + getAvailableSlots());

        }
        else
        {
            System.out.println("No available slots for regular registration");
        }
    }

    public void decrementAvailableCCSlots()
    {
        if(availableSlots > 0)
        {
            availableSlots--;
            System.out.println("The number of available slots now are : " + getAvailableCCSlots());

        }
        else
        {
            System.out.println("No available slots for regular registration");
        }
    }

    public void incrementAvailableSlots()
    {
        if(availableSlots > 0)
        {
            availableSlots++;
            System.out.println("The number of available slots now are : " + getAvailableSlots());
            
        }
        else
        {
            System.out.println("No available slots for regular registration");
        }
    }

    public void incrementAvailableCCSlots()
    {
        if(availableSlots > 0)
        {
            availableSlots++;
            System.out.println("The number of available slots now are : " + getAvailableCCSlots());

        }
        else
        {
            System.out.println("No available slots for regular registration");
        }
    }

    public void storeStudentID(Student student, Camp camp)
    {
        try
        {
            String studentID = student.getID();
            if(this.studentID == null)
            {
                this.studentID = studentID;
            }
            else if (!this.studentID.contains(studentID)) 
            {
                this.studentID += "," + studentID;
            }
            CampDatabase.getInstance().update(camp);
        } catch (Exception e)
        {
            System.out.println("Error storing student ID in camp: ");
        }
    }
    

    private String getCampStaffInformationString() {
        try {
            Staff staff = StaffDatabase.getInstance().getByID(staffID);
            return  String.format("| Staff Name                  | %-65s |\n", staff.getUserName()) +
                    String.format("| Staff Email Address         | %-65s |\n", staff.getEmail()) +
                    String.format("| Staff Faculty               | %-65s |\n", staff.getFaculty());
        } catch (UserErrorException e) {
            return "No Staff Assigned";
        }
    }

    private String getCampStudentInformationString() {
        if (EmptyID.isEmptyID(studentID)) 
        {
            return "";
        }
        try 
        {
            Student student = StudentDatabase.getInstance().getByID(studentID);
            return String.format("| Student Name                | %-65s |\n", student.getUserName()) +
                   String.format("| Student Email Address       | %-65s |\n", student.getEmail());
        } catch (UserErrorException e) {
            throw new IllegalStateException("Cannot find the student.");
        }
    }

    private String getCampInformationString() 
    {
        return String.format("| Location                    | %-65s |\n", getLocation()) +
               String.format("| Available Slots             | %-65s |\n", getAvailableSlots()) +
               String.format("| Available CC Slots          | %-65s |\n", getAvailableCCSlots()) +
               String.format("| Camp Status                 | %-74s |\n", getStatus().colorString());
    }

    private String getSingleCampString() 
    {
        String campTitle = getCampTitle();
        String description = getDescription();
        int maxTitleLength = 95;
        String titleLine1;
        String titleLine2;
        String titleLine3;
        String titleLine4;

        if (campTitle.length() <= maxTitleLength) 
        {
            int leftPadding = (maxTitleLength - campTitle.length()) / 2;
            int rightPadding = maxTitleLength - campTitle.length() - leftPadding;
            int rightPadding2 = maxTitleLength - description.length();
            titleLine1 = String.format("| %-" + leftPadding + "s%-" + campTitle.length() + "s%-" + rightPadding + "s |\n", "", campTitle, "");
            titleLine2 = "";
            titleLine3 = String.format("| %-" + leftPadding + "s%-" + campTitle.length() + "s%-" + rightPadding + "s |\n", "", "Camp Description", "");
            titleLine4 = String.format("| %" + "s%-" + campTitle.length() + "s%-" + rightPadding2 + "s |\n", "", description, "");
        } 
        else 
        {
            String[] words = campTitle.split("\\s+");
            String firstLine = "";
            String secondLine = "";
            int remainingLength = maxTitleLength;
            int i = 0;
            while (i < words.length) 
            {
                if (firstLine.length() + words[i].length() + 1 <= maxTitleLength) 
                {
                    firstLine += words[i] + " ";
                    remainingLength = maxTitleLength - firstLine.length();
                    i++;
                } 
                else 
                {
                    break;
                }
            }
            for (; i < words.length; i++) 
            {
                if (secondLine.length() + words[i].length() + 1 <= maxTitleLength) 
                {
                    secondLine += words[i] + " ";
                } 
                else 
                {
                    break;
                }
            }
            int leftPadding1 = (maxTitleLength - firstLine.length()) / 2;
            int leftPadding2 = (maxTitleLength - secondLine.length()) / 2;
            int rightPadding1 = maxTitleLength - firstLine.length() - leftPadding1;
            int rightPadding2 = maxTitleLength - secondLine.length() - leftPadding2;
            titleLine1 = String.format("| %-" + leftPadding1 + "s%-" + firstLine.length() + "s%-" + rightPadding1 + "s |\n", "", firstLine.trim(), "");
            titleLine2 = String.format("| %-" + leftPadding2 + "s%-" + secondLine.length() + "s%-" + rightPadding2 + "s |\n", "", secondLine.trim(), "");
            titleLine3 = String.format("| %-" + leftPadding2 + "s%-" + secondLine.length() + "s%-" + rightPadding2 + "s |\n", "", secondLine.trim(),"");
            titleLine4 = String.format("| %" + "s%-" + secondLine.length() + "s%-" + rightPadding2 + "s |\n", "", secondLine.trim(),"");
        }

        return titleLine1 + titleLine2 +
                "|-------------------------------------------------------------------------------------------------|\n" +
                String.format("| Camp ID                     | %-65s |\n", getID()) +
                getCampStaffInformationString() +
                getCampInformationString() +
                "|=================================================================================================|\n" +
                titleLine3 +
                "|-------------------------------------------------------------------------------------------------|\n" +
                titleLine4 +
                "|=================================================================================================|\n";
    }
    @Override
    public String getDisplayableString() 
    {
        return getSingleCampString();
    }

    @Override
    public String getSplitter() 
    {
        return "===================================================================================================";
    }
}
