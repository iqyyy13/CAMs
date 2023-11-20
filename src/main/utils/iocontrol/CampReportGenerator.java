package main.utils.iocontrol;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import main.boundary.modelviewer.ModelViewer;
import main.database.user.StudentDatabase;
import main.model.camp.Camp;
import main.model.user.Student;

import static main.utils.config.Location.RESOURCE_LOCATION;

public class CampReportGenerator 
{
    private static final String FILE_PATH = "/data/report/CampAttendanceReport.txt";

    public static void generateReportAndWriteToFile(List<Camp> campList)
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
            //reportBuilder.append("Camp ID: ").append(camp.getID()).append("\n");
            //reportBuilder.append("Camp: ").append(camp.getCampTitle()).append("\n");

            String registeredStudentID = camp.getStudentID();
            if(registeredStudentID != null && !registeredStudentID.isEmpty())
            {
                String[] studentIDs = registeredStudentID.split(",");
                for(String studentID : studentIDs)
                {
                    try
                    {
                        Student student = StudentDatabase.getInstance().getByID(studentID.trim());
                        String studentInfo = String.format("%-30s %-15s %-30s %-15s",
                        student.getUserName(), 
                        student.getID(), 
                        student.getEmail(), 
                        student.getRoleDisplay(campID));

                        reportBuilder.append(studentInfo).append("\n");
                    } catch (Exception e)
                    {
                        System.out.println("");
                    }
                }

                reportBuilder.append("---------------------------------------------------------------------------------------------\n");
                reportBuilder.append("=============================================================================================\n");
            }           
        } 
        writeToFile(reportBuilder.toString(), getFilePath());
    }
    

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

    public static String getFilePath()
    {
        return RESOURCE_LOCATION + FILE_PATH;
    }
}
