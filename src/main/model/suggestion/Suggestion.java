package main.model.suggestion;

import main.database.suggestion.SuggestionDatabase;
import main.database.user.StaffDatabase;
import main.database.user.StudentDatabase;
import main.model.Displayable;
import main.model.Model;
import main.model.camp.CampStatus;
import main.model.user.Staff;
import main.model.user.Student;
import main.utils.exception.UserErrorException;

import java.util.Map;

/**
 * Represents a suggestion in the system
 */
public class Suggestion implements Model, Displayable
{
    //Attributes for a suggestion object
    SuggestionStatus status;
    private String msg;
    private int suggestionID;
    private String committeeUserID;
    private String staffID;
    private String campID;

    /**
     * Constructs a new suggestion object.
     * 
     * @param msg               The suggestion message
     * @param suggestionID      The suggestion ID
     * @param committeeUserID   The committee user ID
     * @param staffID           The staff ID
     * @param campID            The camp ID
     */
    public Suggestion(String msg, int suggestionID, String committeeUserID, String staffID, String campID) 
    {
        this.msg = msg;
        this.suggestionID = suggestionID;
        this.committeeUserID = committeeUserID;
        this.staffID = staffID;
        this.campID = campID;
        this.status = SuggestionStatus.PENDING;
    }
    
    /**
     * Constructs a new suggestion object with a string ID
     * 
     * @param msg                   The suggestion message
     * @param suggestionID          The suggestion ID as a string
     * @param committeeUserID       The committee user ID
     * @param staffID               The staff ID
     * @param campID                The camp ID
     */
    public Suggestion(String msg, String suggestionID, String committeeUserID, String staffID, String campID) 
    {
        this.msg = msg;
        this.suggestionID = Integer.parseInt(suggestionID);
        this.committeeUserID = committeeUserID;
        this.staffID = staffID;
        this.campID = campID;
        this.status = SuggestionStatus.PENDING;
    }

    /**
     * Constructs a new suggestion object with information from the specified map
     * 
     * @param map The map containing information about the suggestion
     */
    public Suggestion(Map<String, String> map) 
    {
        fromMap(map);
    }

    
    /**
     * Gets the staff ID
     * 
     * @return  The staff ID
     */
    public String getStaffID() 
    {
        return staffID;
    }
    
    /**
     * Sets the staff ID
     * 
     * @param staffID The staff ID to set.
     */
    public void setStaffID(String staffID)
    {
        this.staffID = staffID;
    }

    /**
     * Gets the camp ID
     * 
     * @return The camp ID
     */
    public String getCampID() 
    {
        return campID;
    }
    
    /**
     * Sets the camp ID
     * 
     * @param campID The camp ID to set.
     */
    public void setCampID(String campID) 
    {
        this.campID = campID;
    }
    
    /**
     * Gets the suggestion message.
     * 
     * @return The suggestion message
     */
    public String getMsg() 
    {
        return msg;
    }

    /**
     * Sets the suggestion message
     * 
     * @param msg The suggestion message to set
     */
    public void setMsg(String msg) 
    {
        this.msg = msg;
    }

    /**
     * Gets the suggestion ID
     * 
     * @return The suggestion ID
     */
    public int getSuggestionID() 
    {
        return suggestionID;
    }

    /**
     * Sets the suggestion ID
     * 
     * @param suggestionID The suggestion ID to set
     */
    public void setSuggestionID(int suggestionID) 
    {
        this.suggestionID = suggestionID;
    }

    /**
     * Gets the committee user ID
     * 
     * @return  The committee user ID
     */
    public String getCommitteeUserID() 
    {
        return committeeUserID;
    }

    /**
     * Sets the committee user ID
     * 
     * @param committeeUserID The committee user ID to set
     */
    public void setCommitteeUserID(String committeeUserID) 
    {
        this.committeeUserID = committeeUserID;
    }

    /**
     * Gets the suggestion status
     * 
     * @return The suggestion status
     */
    public SuggestionStatus getStatus() 
    {
        return status;
    }

    /**
     * Sets the suggestion status
     * 
     * @param status The suggestion status to set
     */
    public void setStatus(SuggestionStatus status) 
    {
        this.status = status;
    }

    @Override
    public String getID() 
    {
        // Assuming suggestionID is a unique identifier for the suggestion
        return String.valueOf(suggestionID);
    }
    
    /**
     * Generates a string representation of CC student information for the suggestion
     * 
     * @return The string representation of CC student information
     */
    private String getCCStudentInformationString() 
    {
        try {
            Student student = StudentDatabase.getInstance().getByID(committeeUserID);
            return  String.format("| Student Name                  | %-65s |\n", student.getUserName()) +
                    String.format("| Student Email Address         | %-65s |\n", student.getEmail()) +
                    String.format("| Staff Faculty                 | %-65s |\n", student.getFaculty()) +
                    String.format("| Camp ID                       | %-65s |\n", student.getCCId());
        } catch (UserErrorException e) {
            return "No Student Assigned";
        }
    }
    
    /**
     * Generates a string representation of suggestion information
     * 
     * @return The string representation of suggestion information
     */
    private String getSuggestionInformationString()
    {
        return  String.format("| Suggestion Name               | %-65s |\n", getMsg()) + 
                String.format("| Suggestion ID                 | %-65s |\n", getSuggestionID()) +
                String.format("| Suggestion Status             | %-74s |\n", getStatus().colorString());
    }
    
    /**
     * Returns a combined string of CC student information and suggestion information
     * 
     * @return  The combined string of information
     */
    private String getSingleSuggestionString()
    {
        return getCCStudentInformationString() +
               getSuggestionInformationString();
    }

    // Implementation of Displayable interface
    @Override
    public String getDisplayableString() 
    {
        // Format the string representation of the object
        //return "Message: " + msg + ", ID: " + suggestionID + ", Approve: " + approve + ", Committee User ID: " + committeeUserID;
        return getSingleSuggestionString();
    }

    @Override
    public String getSplitter() 
    {
        // Define the splitter used to separate fields in the formatted string representation
        return "====================================================================================================="; // You can use any delimiter you prefer
    }
}
