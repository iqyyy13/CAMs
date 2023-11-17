package main.repository.user;

import main.model.user.Staff;
import main.repository.Repository;

import java.util.List;
import java.util.Map;

import static main.utils.config.Location.RESOURCE_LOCATION;

public class StaffRepository extends Repository<Staff> {

    /**
     * The path of the repository file.
     */
    private static final String FILE_PATH = "/data/user/staff.txt";

    StaffRepository() {
        super();
        load();
    }

    public static StaffRepository getInstance() 
    {
        return new StaffRepository();
    }

    @Override
    public String getFilePath() 
    {
        return RESOURCE_LOCATION + FILE_PATH;
    }

    @Override
    public void setAll(List<Map<String, String>> listOfMappableObjects) 
    {
        for (Map<String, String> map : listOfMappableObjects) {
            getAll().add(new Staff(map));
        }
    }
}
