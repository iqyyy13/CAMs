package main.database.camp;

import main.database.Database;
import main.model.camp.Camp;

import java.util.List;
import java.util.Map;

import static main.utils.config.Location.RESOURCE_LOCATION;

/**
 * Manages the persistence and retrieval of Camp objects. Extends the Database class and
 * implements methods for loading and saving camp information to/from a file.
 */
public class CampDatabase extends Database<Camp> 
{
    /**
     * The file path for storing camp data
     */
    private static final String FILE_PATH = "/data/camp/camp.txt";

    /**
     * Constructs a new CampDatabase, initializing the database and loading stored data.
     */
    CampDatabase() 
    {
        super();
        load();
    }

    /** 
     * Retrieves an instance of the CampDatabase
     * 
     * @return An instance of the CampDatabase
     */
    public static CampDatabase getInstance()
    {
        return new CampDatabase();
    }

    /**
     * Retrieves the file path for storing camp data
     * 
     * @return The file path for storing file data
     */
    @Override
    public String getFilePath() 
    {
        return RESOURCE_LOCATION + FILE_PATH;
    }

    /**
     * Sets the list of mappable objects based on a list of maps
     * 
     * @param listOfMappableObjects The list of maps containing information about camps.
     */
    @Override
    public void setAll(List<Map<String, String>> listOfMappableObjects) 
    {
        for (Map<String, String> map : listOfMappableObjects) 
        {
            getAll().add(new Camp(map));
        }
    }
}
