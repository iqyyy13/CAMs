package main.model.enquiry;

import main.model.Displayable;
import main.model.Model;

import java.util.ArrayList;
import java.util.Map;

/**
* Represents an enquiry made by a student for a specific camp
*/
public class Enquiry implements Model, Displayable 
{
    private String enquiryID;

    private String campID;

    private String studentID;

    private String message;

    private boolean pending = true;

    private ArrayList<String> replies = new ArrayList<>();

	/**
	 * Constructs an Enquiry object with the given camp ID and student ID
	 * 
	 * @param camp_id		The ID of the camp related to the enquiry
	 * @param student_id	The ID of the student making the enquiry
	 */
    public Enquiry(String camp_id, String student_id) 
	{
		this.campID = camp_id;
		this.studentID = student_id;
	}

	/**
	 * Constructs an Enquiry object from a map of string properties.
	 * 
	 * @param map A map containing properties to initialize the Enquiry object
	 */
    public Enquiry(Map<String, String> map) 
    {
        fromMap(map);
    }

	/**
	 * Gets the list of replies to the enquiry
	 * 
	 * @return	The list of replies
	 */
	public ArrayList<String> getReplies() 
	{
		return replies;
	}

	/**
	 * Gets the ID of the enquiry
	 * 
	 * @return The enquiry ID
	 */
	public String getEnquiryID() 
	{
		return enquiryID;
	}

	/**
	 * Sets the ID of the enquiry
	 * 
	 * @param enquiry_id	The enquiry ID to set
	 */
	public void setEnquiryID(String enquiry_id) 
	{
		this.enquiryID = enquiry_id;
	}

	/**
	 * Gets the camp ID related to an enquiry
	 * 
	 * @return	The camp ID
	 */
	public String getcampID() 
	{
		return campID;
	}

	/**
	 * Sets the camp ID related to the enquiry
	 * 
	 * @param camp_id	The camp ID to set
	 */
	public void setCampID(String camp_id) 
	{
		this.campID = camp_id;
	}

	/**
	 * Sets the student ID making the enquiry
	 * 
	 * @param student_id	The student ID to set
	 */
	public void setStudentID(String student_id) 
	{
		this.studentID = student_id;
	}

	/**
	 * Checks if the enquiry is pending
	 * 
	 * @return True if the enquiry is pending, false otherwise
	 */
	public boolean isPending() 
	{
		return pending;
	}

	/**
	 * Gets the message of the enquiry
	 * 
	 * @return	The enquiry message
	 */
	public String enq_message() 
	{
		return message;
	}

	/**
	 * Gets the student ID making the enquiry
	 * 
	 * @return	The student ID
	 */
	public String getStudentID() 
	{
		return studentID;
	}

	/**
	 * Sets a new enquiry message
	 * 
	 * @param message	The new enquiry message
	 */
	public void new_enq_message(String message) 
	{
		this.message = message;
	}

	/**
	 * Sets the status of the enquiry (pending or not pending
	 * )
	 * @param status	The status to set
	 */
	public void set_status(boolean status) 
	{
		this.pending = status;
	}

    @Override
    public String getDisplayableString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDisplayableString'");
    }

    @Override
    public String getSplitter() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSplitter'");
    }

    @Override
    public String getID() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getID'");
    }
}