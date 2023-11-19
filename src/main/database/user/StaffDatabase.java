package main.database.user;

import main.database.Database;
import main.model.user.Staff;
import main.utils.config.Location;

import java.util.List;
import java.util.Map;

public class StaffDatabase extends Database<Staff> {

    /**
     * The path of the repository file.
     */
    private static final String FILE_PATH = "/data/user/staff.txt";

    StaffDatabase() {
        super();
        load();
    }

    
    /** 
     * @return StaffDatabase
     */
    public static StaffDatabase getInstance() 
    {
        return new StaffDatabase();
    }

    @Override
    public String getFilePath() 
    {
        return Location.RESOURCE_LOCATION + FILE_PATH;
    }

    @Override
    public void setAll(List<Map<String, String>> listOfMappableObjects) 
    {
        for (Map<String, String> map : listOfMappableObjects) {
            getAll().add(new Staff(map));
        }
    }
}
