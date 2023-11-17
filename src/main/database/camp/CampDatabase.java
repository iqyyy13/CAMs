package main.database.camp;

import main.database.Database;
import main.model.camp.Camp;

import java.util.List;
import java.util.Map;

import static main.utils.config.Location.RESOURCE_LOCATION;

public class CampDatabase extends Database<Camp> 
{
    private static final String FILE_PATH = "/data/camp/camp.txt";

    CampDatabase() 
    {
        super();
        load();
    }

    public static CampDatabase getInstance()
    {
        return new CampDatabase();
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
            getAll().add(new Camp(map));
        }
    }
}
