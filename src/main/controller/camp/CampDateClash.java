package main.controller.camp;

import java.time.LocalDate;

import main.database.camp.CampDatabase;
import main.database.user.StudentDatabase;
import main.model.camp.Camp;
import main.model.user.Student;

/**
 * Utility class for checking date clashes between camps and student registration
 */
public class CampDateClash 
{
    /**
     * Checks if there is a date clash between the two camps.
     * 
     * @param campID1   The ID of the first camp
     * @param campID2   The ID of the second camp
     * @return          True if there is a date clash, false otherwise.
     */
    public static boolean testDateClash(String campID1, String campID2)
    {
        try
        {
            Camp camp1 = CampDatabase.getInstance().getByID(campID1);
            Camp camp2 = CampDatabase.getInstance().getByID(campID2);

            if(hasDateClash(camp1.getStartDate(), camp1.getEndDate(), camp2.getStartDate(), camp2.getEndDate()))
            {
                System.out.println("Date clash detected");
                return true;
            }
            else
            {
                System.out.println("No date clash");
                return false;
            }
        } catch (Exception e)
        {
            System.out.println("");
        }

        return false;
    }

    /**
     * Checks if there is a date clash between a student's registered camp and a given camp.
     * 
     * @param student   The student whose registrations are checked.
     * @param campID1   The ID of the camp to check for clashes
     * @return          The ID of a camp with which a clash occurs, or null if no clash is found
     */
    public static String registrationDateClash(Student student, String campID1)
    {
        String registeredCampIDs = student.getRegisteredCampIDs();

        try
        {
            if(registeredCampIDs != null && !registeredCampIDs.isEmpty())
            {
                String[] campIDs = registeredCampIDs.split(",");
                for(String registeredCampID : campIDs)
                {
                    if(testDateClash(registeredCampID, campID1) == true)
                    {
                        return registeredCampID;
                    }
                }
            }

        } catch (Exception e)
        {
            System.out.println("");
        }

        return null;

    }

    /**
     * Checks if there is a date clash between the two date ranges
     * 
     * @param startDate1    The start date of the first date range
     * @param endDate1      The end date of the first date range
     * @param startDate2    The start date of the second date range
     * @param endDate2      The end date of the second date range
     * @return              True if there is a date clash, false otherwise
     */
    private static boolean hasDateClash(LocalDate startDate1, LocalDate endDate1, LocalDate startDate2, LocalDate endDate2)
    {
        return !(endDate1.isBefore(startDate2) || startDate1.isAfter(endDate2));
    }
}
