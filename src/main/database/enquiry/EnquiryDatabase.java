package main.database.enquiry;

import main.database.Database;
import main.model.enquiry.Enquiry;

import java.util.List;
import java.util.Map;

import static main.utils.config.Location.RESOURCE_LOCATION;

public class EnquiryDatabase extends Database<Enquiry> 
{
    private static final String FILE_PATH = "/data/enquiry/enquiry.txt";

    EnquiryDatabase() 
    {
        super();
        load();
    }

<<<<<<< HEAD
    
=======

>>>>>>> 1da6f7914d4097ac015857754b7a2de9dfc47f61
    /** 
     * @return EnquiryDatabase
     */
    public static EnquiryDatabase getInstance()
    {
        return new EnquiryDatabase();
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
            getAll().add(new Enquiry(map));
        }
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 1da6f7914d4097ac015857754b7a2de9dfc47f61
