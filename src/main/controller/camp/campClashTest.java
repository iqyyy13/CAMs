package main.controller.camp;

import java.time.LocalDate;

import main.database.camp.CampDatabase;
import main.database.user.StudentDatabase;
import main.model.camp.Camp;
import main.model.user.Student;
public class campClashTest 
{
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

    public static boolean registrationDateClash(Student student, String campID1)
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
                        return true;
                    }
                }
            }

        } catch (Exception e)
        {
            System.out.println("");
        }

        return false;

    }

    private static boolean hasDateClash(LocalDate startDate1, LocalDate endDate1, LocalDate startDate2, LocalDate endDate2)
    {
        return !(endDate1.isBefore(startDate2) || startDate1.isAfter(endDate2));
    }
}
