package main.database.user;

import main.database.Database;
import main.model.user.Student;

import java.util.List;
import java.util.Map;

import static main.utils.config.Location.RESOURCE_LOCATION;

public class StudentDatabase extends Database<Student> {

    private static final String FILE_PATH = "/data/user/student.txt";

    StudentDatabase() 
    {
        super();
        load();
    }

    public static StudentDatabase getInstance()
    {
        return new StudentDatabase();
    }

    @Override
    public String getFilePath()
    {
        return RESOURCE_LOCATION + FILE_PATH;
    }

    @Override
    public void setAll(List<Map<String, String>> listOfMappableObjects) 
    {
        for (Map<String, String> map : listOfMappableObjects)
        {
            getAll().add(new Student(map));
        }
    }
}
