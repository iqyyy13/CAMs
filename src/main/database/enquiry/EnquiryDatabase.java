package main.database.enquiry;

import main.database.Database;
import main.model.enquiry.Enquiry;

import java.util.List;
import java.util.Map;

import static main.utils.config.Location.RESOURCE_LOCATION;

/**
 * Database class for managing enquiry objects
 */
public class EnquiryDatabase extends Database<Enquiry> 
{
    private static final String FILE_PATH = "/data/enquiry/enquiry.txt";

    /**
     * Loads data from the file
     */
    EnquiryDatabase() 
    {
        super();
        load();
    }


    /** 
     * Gets the instance of EnquiryDatabase
     * 
     * @return The EnquiryDatabase instance
     */
    public static EnquiryDatabase getInstance()
    {
        return new EnquiryDatabase();
    }

    /**
     * Retrieves the file path for storing Enquiry data
     */
    @Override
    public String getFilePath() 
    {
        return RESOURCE_LOCATION + FILE_PATH;
    }

    /**
     * Sets all Enquiry objects using a list of mappable objects
     * 
     * @param listOfMappableObjects List of maps representing mappable objects.
     */
    @Override
    public void setAll(List<Map<String, String>> listOfMappableObjects) 
    {
        for (Map<String, String> map : listOfMappableObjects) 
        {
            getAll().add(new Enquiry(map));
        }
    }
}