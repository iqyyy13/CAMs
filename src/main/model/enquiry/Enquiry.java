package main.model.enquiry;

import main.model.Displayable;
import main.model.Model;

import java.util.ArrayList;
import java.util.Map;


public class Enquiry implements Model, Displayable {

    private String enquiryID;

    private String campID;

    private String studentID;

    private String message;

    private boolean pending = true;

    private ArrayList<String> replies = new ArrayList<>();

    public Enquiry(String camp_id, String student_id) {
		this.campID = camp_id;
		this.studentID = student_id;
	}

    public Enquiry(Map<String, String> map) 
    {
        fromMap(map);
    }
    
	public ArrayList<String> getReplies() {
		return replies;
	}
	
	public String getEnquiryID() {
		return enquiryID;
	}

	public void setEnquiryID(String enquiry_id) {
		this.enquiryID = enquiry_id;
	}
	
	public String getcampID() {
		return campID;
	}
	
	public void setCampID(String camp_id) {
		this.campID = camp_id;
	}
	
	public void setStudentID(String student_id) {
		this.studentID = student_id;
	}

	public boolean isPending() {
		return pending;
	}

	public String enq_message() {
		return message;
	}
	
	public String getStudentID() {
		return studentID;
	}
	
	public void new_enq_message(String message) {
		this.message = message;
	}
	
	public void set_status(boolean status) {
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