package main.database.user;

import main.database.Database;
import main.model.user.Staff;
import main.utils.config.Location;

import java.util.List;
import java.util.Map;

/**
 * Manages the persistence and retrieval of Staff objects. Extends the Database class
 * and implements methods for loading and saving staff information to/from a file
 */
public class StaffDatabase extends Database<Staff> 
{
    /**
     * The file path for storing staff data
     */
    private static final String FILE_PATH = "/data/user/staff.txt";
       
    /** 
     * Constructs a new StaffDatabase, initializing the database and loading stored data
     */
    StaffDatabase() 
    {
        super();
        load();
    }

    /**
     * Retrieves an instance of the StaffDatabase
     * 
     * @return  An instance of the StaffDatabase
     */
    public static StaffDatabase getInstance() 
    {
        return new StaffDatabase();
    }

    /**
     * Retrieves the file path for storing staff data
     * 
     * @return The file path for storing staff data
     */
    @Override
    public String getFilePath() 
    {
        return Location.RESOURCE_LOCATION + FILE_PATH;
    }

    /**
     * Sets the list of mappable objects based on a list of maps
     * 
     * @param listOfMappableObjects The list of maps containing information about staff.
     */
    @Override
    public void setAll(List<Map<String, String>> listOfMappableObjects) 
    {
        for (Map<String, String> map : listOfMappableObjects) {
            getAll().add(new Staff(map));
        }
    }
}
