package main.utils.iocontrol;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import main.database.user.StudentDatabase;
import main.model.camp.Camp;
import main.model.user.Student;

import static main.utils.config.Location.RESOURCE_LOCATION;

/**
 * The CampReportGenerator class generates a camp attendance report and writes it to a file.
 */
public class CampReportGenerator 
{
    /**
     * The file path where the report will be stored.
     */
    private static final String FILE_PATH = "/data/report/CampAttendanceReport.txt";

    /**
     * Generates a camp attendance report based on the provided list of camps and writes it to a file
     * 
     * @param campList The list of camps for which the report will be generated
     */
    public static void generateReportAndWriteToFile(List<Camp> campList, String reportType)
    {
        StringBuilder reportBuilder = new StringBuilder();

        reportBuilder.append("Camp Attendance Report\n\n");
        reportBuilder.append("=============================================================================================\n");

        for(Camp camp : campList)
        {
            String campID = camp.getID();
            String campName = camp.getCampTitle();
            
            reportBuilder.append(String.format("%-15s %-30s\n", "Camp ID", "Camp Name"));
            reportBuilder.append(String.format("%-15s %-30s\n", campID, campName));
            reportBuilder.append("---------------------------------------------------------------------------------------------\n");
            reportBuilder.append(String.format("%-30s %-15s %-30s %-15s\n", "Student Name", "Student ID", "Email", "Role"));

            String registeredStudentID = camp.getStudentID();
            if(registeredStudentID != null && !registeredStudentID.isEmpty())
            {
                String[] studentIDs = registeredStudentID.split(",");
                for(String studentID : studentIDs)
                {
                    try
                    {
                        Student student = StudentDatabase.getInstance().getByID(studentID.trim());
                        String studentRole = student.getRoleDisplay(campID);

                        String studentInfo = String.format("%-30s %-15s %-30s %-15s",
                        student.getUserName(), 
                        student.getID(), 
                        student.getEmail(), 
                        student.getRoleDisplay(campID));

                        if("CAMP ATTENDEE".equalsIgnoreCase(reportType) && isCampAttendee(studentRole))
                        {
                            reportBuilder.append(studentInfo).append("\n");

                        }
                        else if("CC".equalsIgnoreCase(reportType) && isCC(studentRole))
                        {
                            reportBuilder.append(studentInfo).append("\n");
                        }
                        else if("ALL".equalsIgnoreCase(reportType))
                        {
                            reportBuilder.append(studentInfo).append("\n");
                        }
                    } catch (Exception e)
                    {
                        System.err.println("");
                    }
                }

                reportBuilder.append("---------------------------------------------------------------------------------------------\n");
                reportBuilder.append("=============================================================================================\n");
            }           
        } 

        writeToFile(reportBuilder.toString(), getFilePath());
    }
    
    public static void generateCCReportAndWriteToFile(Camp camp, String reportType)
    {
        StringBuilder reportBuilder = new StringBuilder();

        reportBuilder.append("Camp Attendance Report\n\n");
        reportBuilder.append("=============================================================================================\n");
        
        String campID = camp.getID();
        String campName = camp.getCampTitle();
            
        reportBuilder.append(String.format("%-15s %-30s\n", "Camp ID", "Camp Name"));
        reportBuilder.append(String.format("%-15s %-30s\n", campID, campName));
        reportBuilder.append("---------------------------------------------------------------------------------------------\n");
        reportBuilder.append(String.format("%-30s %-15s %-30s %-15s\n", "Student Name", "Student ID", "Email", "Role"));

        String registeredStudentID = camp.getStudentID();
        if(registeredStudentID != null && !registeredStudentID.isEmpty())
        {
            String[] studentIDs = registeredStudentID.split(",");
            for(String studentID : studentIDs)
            {
                try
                {
                    Student student = StudentDatabase.getInstance().getByID(studentID.trim());
                    String studentRole = student.getRoleDisplay(campID);

                    String studentInfo = String.format("%-30s %-15s %-30s %-15s",
                    student.getUserName(), 
                    student.getID(), 
                    student.getEmail(), 
                    student.getRoleDisplay(campID));

                    if("CAMP ATTENDEE".equalsIgnoreCase(reportType) && isCampAttendee(studentRole))
                    {
                        reportBuilder.append(studentInfo).append("\n");

                    }
                    else if("CC".equalsIgnoreCase(reportType) && isCC(studentRole))
                    {
                        reportBuilder.append(studentInfo).append("\n");
                    }
                    else if("ALL".equalsIgnoreCase(reportType))
                    {
                        reportBuilder.append(studentInfo).append("\n");
                    }
                } catch (Exception e)
                {
                        System.err.println("");
                }
            }

            reportBuilder.append("---------------------------------------------------------------------------------------------\n");
            reportBuilder.append("=============================================================================================\n");
        }           

        writeToFile(reportBuilder.toString(), getFilePath());
    }
    /**
     * Writes the content to a file with the specified file name
     * 
     * @param content   The content to be written to a file
     * @param fileName  The name of the file to which the content will be written
     */
    private static void writeToFile(String content, String fileName)
    {
        try(FileWriter fileWriter = new FileWriter(fileName))
        {
            fileWriter.write(content);
            System.out.println("Report has been written to " + fileName);
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Gets the full file path for the report file
     * 
     * @return  The full file path for the report file
     */
    public static String getFilePath()
    {
        return RESOURCE_LOCATION + FILE_PATH;
    }

    /**
     * Checks if the provided role string indicates a camp attendee
     * 
     * @param role  The role string to be checked.
     * @return {@code true} if the role indicates a camp attendee {@code false} otherwise.
     */
    private static boolean isCampAttendee(String role)
    {
        if(role.equals("CAMP ATTENDEE"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Checks if the provided role string indicates a CC (Camp Committee) member
     * 
     * @param role  The role string to be checked.
     * @return      {@code true} if the role indicates a CC member {@code false} otherwise.
     */
    private static boolean isCC(String role)
    {
        if(role.equals("CC"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
