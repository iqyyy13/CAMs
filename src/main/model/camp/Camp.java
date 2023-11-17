package main.model.camp;

import main.model.Displayable;
import main.model.Model;
import main.model.user.Student;
import main.model.user.Staff;
import main.repository.user.StaffRepository;
import main.repository.user.StudentRepository;
import main.utils.exception.UserErrorException;
import main.utils.parameters.EmptyID;

import java.util.Map;

/**
 * The class of the project
 */
public class Camp implements Model, Displayable 
{
    CampStatus status;

    private String campID;

    private String staffID;

    private String studentID;

    private String campTitle;

    private String faculty;

    /**
     * the constructor of the project
     *
     * @param campID    the ID of the project
     * @param campTitle the title of the project
     * @param staffID the supervisor of the project
     */
    public Camp(String campID, String campTitle, String staffID, String faculty) 
    {
        this.campID = campID;
        this.campTitle = campTitle;
        this.staffID = staffID;
        this.faculty = faculty;
        this.studentID = EmptyID.EMPTY_ID;
        this.status = CampStatus.AVAILABLE;
    }

    /**
     * Get the ID of the project
     * @param map the map of the project
     */
    public Camp(Map<String, String> map) 
    {
        fromMap(map);
    }

    private void displayStaffInformation(String staffID) 
    {
        try 
        {
            Staff staff = StaffRepository.getInstance().getByID(staffID);
            System.out.println("Staff Name: " + staff.getUserName());
            System.out.println("Staff Email Address: " + staff.getEmail());
        } catch (UserErrorException e) {
            System.out.println("No Staff Yet");
        }
    }

    /**
     * Display the information of the student
     */
    private void displayStudentInformation() {
        try {
            Student student = StudentRepository.getInstance().getByID(studentID);
            System.out.println("Student Name: " + student.getUserName());
            System.out.println("Student Email Address: " + student.getEmail());
        } catch (UserErrorException e) {
            System.out.println("No Student Yet");
        }
    }

    /**
     * Display the ID of the project
     */
    private void displayCampID() 
    {
        System.out.println("Camp ID: " + campID);
    }

    /**
     * Display the information of the project
     */
    private void displayCampInformation() 
    {
        System.out.println("Camp Title: " + campTitle);
        System.out.println("Camp Status: " + status);
    }

    /**
     * Display the whole information of the project
     */
    public void displayCamp() 
    {
        displayCampID();
        displayStaffInformation(this.staffID);
        if (status == CampStatus.ALLOCATED)
        {
            displayStudentInformation();
        }
        displayCampInformation();
    }

    /**
     * Assign a student to the project
     *
     * @param studentID the student to be assigned
     * @throws IllegalStateException if the project is not available for allocation
     */
    public void assignStudent(String studentID) throws IllegalStateException 
    {
        if (status != CampStatus.AVAILABLE) 
        {
            throw new IllegalStateException("Project is not available for allocation.");
        }
        this.studentID = studentID;
        this.status = CampStatus.ALLOCATED;
    }

    public String getStudentID() 
    {
        return studentID;
    }

    public void setStudentID(String studentID) 
    {
        this.studentID = studentID;
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

    public CampStatus getStatus() {
        return status;
    }

    public void setStatus(CampStatus status) {
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

    private String getCampStaffInformationString() {
        try {
            Staff staff = StaffRepository.getInstance().getByID(staffID);
            return String.format("| Supervisor Name             | %-30s |\n", staff.getUserName()) +
                    String.format("| Supervisor Email Address    | %-30s |\n", staff.getEmail());
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
            Student student = StudentRepository.getInstance().getByID(studentID);
            return String.format("| Student Name                | %-30s |\n", student.getUserName()) +
                    String.format("| Student Email Address       | %-30s |\n", student.getEmail());
        } catch (UserErrorException e) {
            throw new IllegalStateException("Cannot find the student.");
        }
    }

    private String getCampInformationString() 
    {
        return String.format("| Project Status              | %-39s |\n", getStatus().colorString());
    }

    private String getSingleCampString() 
    {
        String campTitle = getCampTitle();
        int maxTitleLength = 60;
        String titleLine1;
        String titleLine2;

        if (campTitle.length() <= maxTitleLength) 
        {
            int leftPadding = (maxTitleLength - campTitle.length()) / 2;
            int rightPadding = maxTitleLength - campTitle.length() - leftPadding;
            titleLine1 = String.format("| %-" + leftPadding + "s%-" + campTitle.length() + "s%-" + rightPadding + "s |\n", "", campTitle, "");
            titleLine2 = "";
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
        }

        return titleLine1 + titleLine2 +
                "|--------------------------------------------------------------|\n" +
                String.format("| Project ID                  | %-30s |\n", getID()) +
                getCampStaffInformationString() +
                getCampStudentInformationString() +
                getCampInformationString();
    }

    @Override
    public String getDisplayableString() 
    {
        return getSingleCampString();
    }

    @Override
    public String getSplitter() 
    {
        return "================================================================";
    }
}
