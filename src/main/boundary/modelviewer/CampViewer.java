package main.boundary.modelviewer;

import main.controller.camp.CampManager;
import main.model.camp.Camp;
import main.model.camp.CampStatus;
import main.model.user.Student;
import main.model.user.StudentStatus;
import main.repository.camp.CampRepository;
import main.repository.user.StaffRepository;
import main.utils.exception.UserErrorException;
import main.utils.exception.PageBackException;
import main.utils.iocontrol.IntGetter;
import main.utils.ui.ChangePage;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class CampViewer 
{
    public static CampStatus geCampStatus() throws PageBackException
    {
        System.out.println("\t1. Available");
        System.out.println("\t2. Allocated");
        System.out.println("\t3. Unavailable");
        System.out.print("Please enter your choice: ");
        int option = IntGetter.readInt();

        return switch (option)
        {
            case 1 -> CampStatus.AVAILABLE;
            case 2 -> CampStatus.ALLOCATED;
            case 3 -> CampStatus.UNAVAILABLE;
            default ->
            {
                System.out.println("Please enter a number between 1-3.");
                System.out.println("Press Enter to retry.");
                String input = new Scanner(System.in).nextLine().trim();
                if(input.equals("0"))
                {
                    throw new PageBackException();
                }
                else
                {
                    yield geCampStatus();
                }
            }
        };
    }

    public static void generateDetailsByCampID() throws PageBackException 
    {
        System.out.println("Please Enter the CampID to search: ");
        String s1 = new Scanner(System.in).nextLine();
        try {
            Camp camp = CampRepository.getInstance().getByID(s1);
            camp.displayCamp();
        } catch (UserErrorException e) {
            System.out.println("Cannot find the camp matching this ID");
            System.out.println("Press Enter to retry");
            String input = new Scanner(System.in).nextLine().trim();
            if (input.equals("b")) 
            {
                throw new PageBackException();
            } 
            else 
            {
                generateDetailsByCampID();
            }
        }
        System.out.println("Press Enter to continue");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    public static void generateDetailsByStaffID() throws PageBackException
    {
        System.out.println("Please enter the StaffID to search: ");
        String s1 = new Scanner(System.in).nextLine();
        if (!StaffRepository.getInstance().contains(s1)) {
            System.out.println("Staff Not Found.");
            System.out.println("Press Enter to retry");
            String input = new Scanner(System.in).nextLine().trim();
            if (input.equalsIgnoreCase("b")) 
            {
                throw new PageBackException();
            } 
            else 
            {
                generateDetailsBySupervisorID();
                return;
            }
        }
        List<Camp> campList = CampRepository.getInstance().findByRules(p -> p.getStaffID().equalsIgnoreCase(s1));
        ModelViewer.displayListOfDisplayable(campList);
        System.out.println("Enter <Enter> to continue");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    public static void generateDetailsByStudentID() throws PageBackException 
    {
        System.out.println("Enter the StudentID to search");
        String s1 = new Scanner(System.in).nextLine();
        ModelViewer.displayListOfDisplayable(CampRepository.getInstance().findByRules(p -> Objects.equals(p.getStudentID(), s1)));
        System.out.println("Enter <Enter> to continue");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    public static void generateDetailsByStatus() throws PageBackException 
    {
        CampStatus status = geCampStatus();
        ModelViewer.displayListOfDisplayable(CampRepository.getInstance().findByRules(p -> Objects.equals(p.getStatus(), status)));
        System.out.println("Enter <Enter> to continue");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    public static void generateCampDetails() throws PageBackException
    {
        ChangePage.changePage();
        System.out.println("Please select the way to search:");
        System.out.println("\t 1. By CampID");
        System.out.println("\t 2. By StaffID");
        System.out.println("\t 3. By Student");
        System.out.println("\t 4. By Status");
        System.out.println("\t 0. Go Back");
        System.out.print("Please enter your choice: ");
        int option = IntGetter.readInt();
        if (option == 0) 
        {
            throw new PageBackException();
        }
        try {
            switch (option) 
            {
                case 1 -> generateDetailsByCampID();
                case 2 -> generateDetailsBySupervisorID();
                case 3 -> generateDetailsByStudentID();
                case 4 -> generateDetailsByStatus();
                default -> 
                {
                    System.out.println("Invalid choice. Please enter again. ");
                    new Scanner(System.in).nextLine();
                    throw new PageBackException();
                }
            }
        } catch (PageBackException e) {
            generateCampDetails();
        }
    }
    public static void viewAvailableCamps(Student student) throws PageBackException 
    {
        ChangePage.changePage();
        if (student.getStatus() != StudentStatus.UNREGISTERED) 
        {
            System.out.println("You are not allowed to view available projects as you are registered to a project.");
        } 
        else 
        {
            System.out.println("View Available Camps");
            ModelViewer.displayListOfDisplayable(CampManager.viewAvailableCamps());
        }
        System.out.println("Press Enter to go back.");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * Displays the project details.
     *
     * @throws PageBackException if the user wants to go back
     */
    public static void viewAllCamps() throws PageBackException 
    {
        ChangePage.changePage();
        System.out.println("View All Camps");
        ModelViewer.displayListOfDisplayable(CampManager.viewAllCamps());
        System.out.println("Press Enter to go back.");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * Displays the project details.
     *
     * @param student the student
     * @throws PageBackException if the user wants to go back
     */
    public static void viewStudentCamp(Student student) throws PageBackException 
    {
        ChangePage.changePage();
        System.out.println("View Student Camp");
        Camp camp = CampManager.getStudentCamp(student);
        if (camp == null) 
        {
            System.out.println("Student has no project yet.");
        } 
        else 
        {
            ModelViewer.displaySingleDisplayable(camp);
        }
        System.out.println("Press Enter to go back.");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }
}
