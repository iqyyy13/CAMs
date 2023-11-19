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

/**
 * Represents a camp, including its status, ID, staff, students, title, faculty,
 * location, description, maximum slots, available slots and related
 * functionalities
 */
public class Camp implements Model, Displayable 
{
    /**
     * The status of a camp
     */
    CampStatus status;

    /**
     * The unique identifier of a camp
     */
    private String campID;

    /**
     * The staffID associated with the camp
     */
    private String staffID;

    /**
     * The studentID associated with the camp
     */
    private String studentID;

    /**
     * The title of the camp
     */
    private String campTitle;

    /**
     * The faculty associated with the camp
     */
    private String faculty;

    /**
     * The maximum number of slots available for regular participants
     */
    private int maxSlots;

    /**
     * The maximum number of slots available for committee members
     */
    private int maxCCSlots;

    /**
     * The number of available slots for regular participants
     */
    private int availableSlots;

    /**
     * The number of available slots for committee members
     */
    private int availableCCSlots;
    
    /**
     * The location of the camp
     */
    private String location;

    /**
     * The description of the camp
     */
    private String description;

    /**
     * Constructs a new Camp object with the specified parameters.
     * 
     * @param campID        The unique identifier of a camp
     * @param campTitle     The title of the camp
     * @param staffID       The staffID associated with the camp
     * @param faculty       The faculty associated with the camp
     * @param location      The location of the camp
     * @param description   The description of the camp
     */
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

    /**
     * Constructs a new Camp object with information from the specified map
     * 
     * @param map The map containing information about the camp
     */
    public Camp(Map<String, String> map) 
    {
        fromMap(map);
    }

    
    /** 
     * Displays staff information based on the provided staff ID.
     * 
     * @param staffID The staff ID for which information should be displayed.
     */
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

    /**
     * Displays the camp ID.
     */
    private void displayCampID() 
    {
        System.out.println("Camp ID: " + campID);
    }

    /**
     * Displays camp information including its title and status
     */
    private void displayCampInformation() 
    {
        System.out.println("Camp Title: " + campTitle);
        System.out.println("Camp Status: " + status);
    }

    /**
     * Displays the entire camp information
     */
    public void displayCamp() 
    {
        displayCampID();
        displayStaffInformation(this.staffID);
        displayCampInformation();
    }


    /**
     * Retrieves the student ID associated with the camp
     * 
     * @return the student ID associated with the camp
     */
    public String getStudentID() 
    {
        return studentID;
    }

    /**
     * Retrieves the staff ID associated with the camp
     * 
     * @return the staff ID associated with the camp
     */
    public String getStaffID() 
    {
        return staffID;
    }

    /**
     * Sets the staff ID of the camp with the given parameter
     * 
     * @param staffID the staff ID to set with
     */
    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    /**
     * Retrieves the title of the camp
     * 
     * @return the title of the camp
     */
    public String getCampTitle() {
        return campTitle;
    }

    /**
     * Sets the title of the camp with the given parameter
     * 
     * @param campTitle the title of the camp to set with
     */
    public void setCampTitle(String campTitle) 
    {
        this.campTitle = campTitle;
    }

    /**
     * Gets the status of the camp
     * 
     * @return the status of the camp
     */
    public CampStatus getStatus() 
    {
        return status;
    }

    /**
     * Sets the status of the camp with the given parameter
     * @param status The status of the camp
     */
    public void setStatus(CampStatus status) 
    {
        this.status = status;
    }

    /**
     * Retrieves the unique identifier of the camp
     * @return the unique identifier of the camp
     */
    @Override
    public String getID() {
        return campID;
    }

    /**
     * Retrieves the faculty associated with the camp
     * @return the faculty of the camp
     */
    public String getFaculty() 
    {
        return this.faculty;
    }

    /**
     * Retrieves the max number of slots associated with the camp
     * @return the max number of slots associated with the camp
     */
    public int getMaxSlots()
    {
        return maxSlots;
    }

    /**
     * Retrieves the max number of camp committee slots associated with the camp
     * 
     * @return the max number of camp committee slots associated with the camp
     */
    public int getCCMaxSlots()
    {
        return maxCCSlots;
    }

    /**
     * Retrives the number of available slots associated with the camp
     * 
     * @return the number of available slots associated with the camp
     */
    public int getAvailableSlots()
    {
        return availableSlots;
    }

    /**
     * Retrieves the number of available Camp Committee slots associated with the camp
     * 
     * @return the number of available Camp Committee slots associated with the camp
     */
    public int getAvailableCCSlots()
    {
        return availableCCSlots;
    }

    /**
     * Retrieves the location associated with the camp
     * 
     * @return the location associated with the camp
     */
    public String getLocation()
    {
        return location;
    }

    /**
     * Retrieves the description associated with the camp
     * 
     * @return the description associated with the camp
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Decremets the available regular slots
     */
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

    /**
     * Decrements the available committee member slots
     */
    public void decrementAvailableCCSlots()
    {
        if(availableSlots > 0)
        {
            availableCCSlots--;
            System.out.println("The number of available slots now are : " + getAvailableCCSlots());

        }
        else
        {
            System.out.println("No available slots for regular registration");
        }
    }

    /**
     * Increments the available regular slots
     */
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

    /**
     * Increments the available committee member slots
     */
    public void incrementAvailableCCSlots()
    {
        if(availableSlots > 0)
        {
            availableCCSlots++;
            System.out.println("The number of available slots now are : " + getAvailableCCSlots());

        }
        else
        {
            System.out.println("No available slots for regular registration");
        }
    }

    /**
     * Stores the student ID associated with the camp
     * 
     * @param student   The student to be stored
     * @param camp      The camp to associate the student with     
     */
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
            System.out.println("Error storing student ID in camp");
        }
    }

    /**
     * Removes the student ID associated with the camp
     * 
     * @param student   The student to be removed
     * @param camp      The camp to dissociate the student from
     */
    public void removeStudentID(Student student, Camp camp)
    {
        try
        {
            String studentID = student.getID();
            this.studentID = this.studentID.replace(studentID, "").replace(",,",",").trim();
            CampDatabase.getInstance().update(camp);
        } catch (Exception e)
        {
            System.out.println("Error finding Student ID in camp");
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

    /**
     * Retrieves a displayable string representing the camp
     * 
     * @return The displayable string
     */
    @Override
    public String getDisplayableString() 
    {
        return getSingleCampString();
    }

    /**
     * Retrieves the splitter string for display formatting
     * 
     * @return The splitter string
     */
    @Override
    public String getSplitter() 
    {
        return "===================================================================================================";
    }
}
