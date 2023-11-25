package main.database.suggestion;
import main.database.Database;
import main.model.suggestion.Suggestion;
import java.util.List;
import java.util.Map;
import static main.utils.config.Location.RESOURCE_LOCATION;
/**
 * Manages the persistence and retrieval of suggestion objects. Extends the Database class and
 * implements methods for loading and saving suggestion information to/from a file.
 */

public class SuggestionDatabase extends Database<Suggestion> 
{
    /**
     * The file path for storing suggestion data
     */
    private static final String FILE_PATH = "/data/suggestions/suggestions.txt";

    /**
     * Constructs a new SuggestionDatabase, initializing the database and loading stored data.
     */
    SuggestionDatabase() {
        super();
        load();
    }
    /** 
     * Retrieves an instance of the SuggestionDatabase
     * 
     * @return An instance of the SuggestionDatabase
     */
    public static SuggestionDatabase getInstance() 
    {
        return new SuggestionDatabase();
    }
    /**
     * Retrieves the file path for storing suggestion data
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
     * @param listOfMappableObjects The list of maps containing information about suggestion.
     */
    @Override
    public void setAll(List<Map<String, String>> listOfMappableObjects) 
    {
        for (Map<String, String> map : listOfMappableObjects) 
        {
            getAll().add(new Suggestion(map));
        }
    }
}
