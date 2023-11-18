package main.model.request;

import main.model.Displayable;
import main.model.Model;

public interface Request extends Model, Displayable {

    String getID();

    String getCampID();

    String getStudentID();

    RequestStatus getStatus();

    void setStatus(RequestStatus status);

    RequestType getRequestType();

    String getStaffID();

    default String getSplitter() {
        return "====================================================";
    }
}
