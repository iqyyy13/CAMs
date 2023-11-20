package main.model.suggestion;

import main.model.Displayable;
import main.model.Model;

import java.util.Map;

public class Suggestion implements Model, Displayable
{
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
    }

    public Suggestion(String msg, String suggestionID, String approve, String committeeUserID, String staffID, String campID) 
    {
        this.msg = msg;
        this.suggestionID = Integer.parseInt(suggestionID);
        this.approve = approve;
        this.committeeUserID = committeeUserID;
        this.staffID = staffID;
        this.campID = campID;
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

    @Override
    public String getID() 
    {
        // Assuming suggestionID is a unique identifier for the suggestion
        return String.valueOf(suggestionID);
    }

    // Implementation of Displayable interface
    @Override
    public String getDisplayableString() 
    {
        // Format the string representation of the object
        return "Message: " + msg + ", ID: " + suggestionID + ", Approve: " + approve + ", Committee User ID: " + committeeUserID;
    }

    @Override
    public String getSplitter() 
    {
        // Define the splitter used to separate fields in the formatted string representation
        return ";"; // You can use any delimiter you prefer
    }

   
}
