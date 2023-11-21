<<<<<<< HEAD
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

public class Suggestion implements Model, Displayable
{
    SuggestionStatus status;
    private String msg;
    private int suggestionID;
    private String committeeUserID;
    private String approve;
    private String staffID;
    private String campID;

    public Suggestion(String msg, int suggestionID, String approve, String committeeUserID, String staffID, String campID) 
    {
        this.msg = msg;
        this.suggestionID = suggestionID;
        this.approve = approve;
        this.committeeUserID = committeeUserID;
        this.staffID = staffID;
        this.campID = campID;
        this.status = SuggestionStatus.PENDING;
    }

    public Suggestion(String msg, String suggestionID, String approve, String committeeUserID, String staffID, String campID) 
    {
        this.msg = msg;
        this.suggestionID = Integer.parseInt(suggestionID);
        this.approve = approve;
        this.committeeUserID = committeeUserID;
        this.staffID = staffID;
        this.campID = campID;
        this.status = SuggestionStatus.PENDING;
    }

    public Suggestion(Map<String, String> map) 
    {
        fromMap(map);
    }

    //Getter and setter for staff id

    public String getStaffID() 
    {
        return staffID;
    }

    public void setStaffID(String staffID)
    {
        this.staffID = staffID;
    }

    // Getter and setter for campID
    public String getCampID() 
    {
        return campID;
    }

    public void setCampID(String campID) 
    {
        this.campID = campID;
    }
    // Getter for msg
    public String getMsg() 
    {
        return msg;
    }

    // Setter for msg
    public void setMsg(String msg) 
    {
        this.msg = msg;
    }

    // Getter for suggestionID
    public int getSuggestionID() 
    {
        return suggestionID;
    }

    // Setter for suggestionID
    public void setSuggestionID(int suggestionID) 
    {
        this.suggestionID = suggestionID;
    }

    // Getter for committeeUserID
    public String getCommitteeUserID() 
    {
        return committeeUserID;
    }

    // Setter for committeeUserID
    public void setCommitteeUserID(String committeeUserID) 
    {
        this.committeeUserID = committeeUserID;
    }

    // Getter for approve
    public String getApprove() 
    {
        return approve;
    }

    // Setter for approve
    public void setApprove(String approve) 
    {
        this.approve = approve;
    }

    public SuggestionStatus getStatus() 
    {
        return status;
    }


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

    private String getSuggestionInformationString()
    {
        return  String.format("| Suggestion Name               | %-65s |\n", getMsg()) + 
                String.format("| Suggestion ID                 | %-65s |\n", getSuggestionID()) +
                String.format("| Suggestion Status             | %-74s |\n", getStatus().colorString());
    }
    
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
=======
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

public class Suggestion implements Model, Displayable
{
    SuggestionStatus status;
    private String msg;
    private int suggestionID;
    private String committeeUserID;
    private String approve;
    private String staffID;
    private String campID;

    public Suggestion(String msg, int suggestionID, String approve, String committeeUserID, String staffID, String campID) 
    {
        this.msg = msg;
        this.suggestionID = suggestionID;
        this.approve = approve;
        this.committeeUserID = committeeUserID;
        this.staffID = staffID;
        this.campID = campID;
        this.status = SuggestionStatus.PENDING;
    }

    public Suggestion(String msg, String suggestionID, String approve, String committeeUserID, String staffID, String campID) 
    {
        this.msg = msg;
        this.suggestionID = Integer.parseInt(suggestionID);
        this.approve = approve;
        this.committeeUserID = committeeUserID;
        this.staffID = staffID;
        this.campID = campID;
        this.status = SuggestionStatus.PENDING;
    }

    public Suggestion(Map<String, String> map) 
    {
        fromMap(map);
    }

    //Getter and setter for staff id

    public String getStaffID() 
    {
        return staffID;
    }

    public void setStaffID(String staffID)
    {
        this.staffID = staffID;
    }

    // Getter and setter for campID
    public String getCampID() 
    {
        return campID;
    }

    public void setCampID(String campID) 
    {
        this.campID = campID;
    }
    // Getter for msg
    public String getMsg() 
    {
        return msg;
    }

    // Setter for msg
    public void setMsg(String msg) 
    {
        this.msg = msg;
    }

    // Getter for suggestionID
    public int getSuggestionID() 
    {
        return suggestionID;
    }

    // Setter for suggestionID
    public void setSuggestionID(int suggestionID) 
    {
        this.suggestionID = suggestionID;
    }

    // Getter for committeeUserID
    public String getCommitteeUserID() 
    {
        return committeeUserID;
    }

    // Setter for committeeUserID
    public void setCommitteeUserID(String committeeUserID) 
    {
        this.committeeUserID = committeeUserID;
    }

    // Getter for approve
    public String getApprove() 
    {
        return approve;
    }

    // Setter for approve
    public void setApprove(String approve) 
    {
        this.approve = approve;
    }

    public SuggestionStatus getStatus() 
    {
        return status;
    }


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

    private String getSuggestionInformationString()
    {
        return  String.format("| Suggestion Name               | %-65s |\n", getMsg()) + 
                String.format("| Suggestion ID                 | %-65s |\n", getSuggestionID()) +
                String.format("| Suggestion Status             | %-74s |\n", getStatus().colorString());
    }
    
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
>>>>>>> e925dc55e9a4cf686a647dc57b3433af9dd5fb1b
