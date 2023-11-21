package main.database.user;

import main.database.Database;
import main.model.user.Student;

import java.util.List;
import java.util.Map;

import static main.utils.config.Location.RESOURCE_LOCATION;

/**
 * Manages the persistence and retrieval of Students objects. Extends the Database class
 * and implements methods for loading and saving student information to/from a file
 */
public class StudentDatabase extends Database<Student> 
{
    /**
     * The file path for storing student data
     */
    private static final String FILE_PATH = "/data/user/student.txt";

    /**
     * Constructs a new StudentDatabase, initializing the database and loading stored data
     */
    StudentDatabase() 
    {
        super();
        load();
    }

    
    /** 
     * Retrieves an instance of the StudentDatabase
     * 
     * @return An instance of the StudentDatabase
     */
    public static StudentDatabase getInstance()
    {
        return new StudentDatabase();
    }

    /**
     * Retrieves the file path for storing student data
     * 
     * @return The file path for storing student data
     */
    @Override
    public String getFilePath()
    {
        return RESOURCE_LOCATION + FILE_PATH;
    }

    /**
     * Sets the list of mappable objects based on a list of maps
     * 
     * @param listofMappableObjects The list of maps containing information about students.
     */
    @Override
    public void setAll(List<Map<String, String>> listOfMappableObjects) 
    {
        for (Map<String, String> map : listOfMappableObjects)
        {
            getAll().add(new Student(map));
        }
    }
}
