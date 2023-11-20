package main.boundary.modelviewer;

import main.boundary.account.Logout;
import main.boundary.account.ResetPassword;
import main.boundary.account.ViewUserProfile;
import main.boundary.mainpage.CCMainPage;
import main.boundary.mainpage.StaffMainPage;
import main.boundary.mainpage.StudentMainPage;
import main.controller.camp.CampManager;
import main.database.camp.CampDatabase;
import main.database.user.StaffDatabase;
import main.database.user.StudentDatabase;
import main.model.camp.Camp;
import main.model.camp.CampStatus;
import main.model.user.Student;
import main.model.user.StudentStatus;
import main.model.user.UserType;
import main.model.user.Staff;
import main.utils.exception.UserErrorException;
import main.utils.exception.PageBackException;
import main.utils.iocontrol.IntGetter;
import main.utils.ui.ChangePage;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * The class provides methods for viewing and interacting with camp details
 */
public class CampViewer 
{
    
    /** 
     * Retrieves the CampStatus based on user input
     * @return CampStatus
     * @throws PageBackException if the user chooses to go back
     */
    public static CampStatus getCampStatus() throws PageBackException
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
                    yield getCampStatus();
                }
            }
        };
    }

    /**
     * Generates and displays details for a camp based on the provided CampID
     * 
     * @throws PageBackException    if the user chooses to go back during the operation
     */
    public static void generateDetailsByCampID() throws PageBackException 
    {
        System.out.println("Please Enter the CampID to search: ");
        String s1 = new Scanner(System.in).nextLine();
        try {
            Camp camp = CampDatabase.getInstance().getByID(s1);
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

    /**
     * Generates and displays details for a camp based on the provided StaffID
     * 
     * @throws PageBackException if the user chooses to go back during the operation
     */
    public static void generateDetailsByStaffID() throws PageBackException
    {
        System.out.println("Please enter the StaffID to search: ");
        String s1 = new Scanner(System.in).nextLine();
        if (!StaffDatabase.getInstance().contains(s1)) 
        {
            System.out.println("Staff Not Found.");
            System.out.println("Press Enter to retry");
            String input = new Scanner(System.in).nextLine().trim();
            if (input.equalsIgnoreCase("b")) 
            {
                throw new PageBackException();
            } 
            else 
            {
                generateDetailsByStaffID();
                return;
            }
        }
        List<Camp> campList = CampDatabase.getInstance().findByRules(p -> p.getStaffID().equalsIgnoreCase(s1));
        ModelViewer.displayListOfDisplayable(campList);
        System.out.println("Enter <Enter> to continue");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * Generates and displays details for a camp based on the provided StudentID
     * 
     * @throws PageBackException    if the user chooses to go back during the operation
     */
    public static void generateDetailsByStudentID() throws PageBackException 
    {
        System.out.println("Enter the StudentID to search");
        String s1 = new Scanner(System.in).nextLine();
        ModelViewer.displayListOfDisplayable(CampDatabase.getInstance().findByRules(p -> Objects.equals(p.getStudentID(), s1)));
        System.out.println("Enter <Enter> to continue");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * Generates and displays details for a camp based on the provided Status
     * 
     * @throws PageBackException    if the user chooses to go back during the operation
     */
    public static void generateDetailsByStatus() throws PageBackException 
    {
        CampStatus status = getCampStatus();
        ModelViewer.displayListOfDisplayable(CampDatabase.getInstance().findByRules(p -> Objects.equals(p.getStatus(), status)));
        System.out.println("Enter <Enter> to continue");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * Generates and displays camp details based on user-selected search criteria
     * 
     * @throws PageBackException    if the user chooses to go back during the operation
     */
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
                case 2 -> generateDetailsByStaffID();
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

    /**
     * Displays the available camps for a student and allows navigation to the previous page
     *   
     * @param student               the student for whom available camps are to be displayed
     * @throws PageBackException    if the user chooses to go back during the operation
     */
    public static void viewAvailableCamps(Student student) throws PageBackException 
    {
        ChangePage.changePage();
        if (student.getStatus() != StudentStatus.UNREGISTERED) 
        {
            System.out.println("You are not allowed to view available camps as you are registered to a camp.");
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
     * Displays all camps and allows navigation to the previous page
     * 
     * @throws PageBackException    if the user chooses to go back during the operation
     */
    public static void viewAllCamp() throws PageBackException 
    {
        ChangePage.changePage();
        System.out.println("View All Camps");
        ModelViewer.displayListOfDisplayable(CampManager.viewAllCamp());
        System.out.println("Press Enter to go back.");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * Displays the camp details for a student and allows navigation to the previous page
     * 
     * @param student               the student for whom the camp details are to be displayed
     * @throws PageBackException    if the user chooses to go back during the operation
     */
    public static void viewStudentCamp(Student student) throws PageBackException 
    {
        ChangePage.changePage();
        System.out.println("View Student Camp");
        Camp camp = CampManager.getStudentCamp(student);
        if (camp == null) 
        {
            System.out.println("Student has no camp yet.");
        } 
        else 
        {
            ModelViewer.displaySingleDisplayable(camp);
        }
        System.out.println("Press Enter to go back.");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * Displays the camps created by a staff member and allows navigation to the previous page
     * 
     * @param staff                 the staff member for whom the camp details are to be displayed
     * @throws PageBackException    if the user chooses to go back during the operation
     */
    public static void generateCreatedCamp(Staff staff) throws PageBackException
    {
        ChangePage.changePage();
        System.out.println("View Created Camps");
        List<Camp> campList = CampDatabase.getInstance().findByRules(p -> p.getStaffID().equalsIgnoreCase(staff.getID()));
        ModelViewer.displayListOfDisplayable(campList);
        System.out.println("Enter <Enter> to continue");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    /**
     * Views the camp assigned to the CC member
     * 
     * @param student               The CC member viewing the assigned camp
     * @throws PageBackException    If the user chooses to go back during the operation
     */
    public static void viewAssignedCamp(Student student) throws PageBackException
    {
        ChangePage.changePage();
        System.out.println("View Assigned Camp");
        String campID = student.getCCId();

        if(campID.equals(""))
        {
            System.out.println("No camp ID assigned. Returning to Student Main Page");
            System.out.println("Press Enter to go back");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            StudentMainPage.studentMainPage(student);
        }
        else
        {
            try
            {
                Camp assignedCamp = CampDatabase.getInstance().getByID(campID);
                ModelViewer.displaySingleDisplayable(assignedCamp);
                System.out.println("Press Enter to exit or 'b' to go back to the main page");
                Scanner scanner = new Scanner(System.in);
                String userInput = scanner.nextLine().trim().toLowerCase();
                if(userInput.equals("b"))
                {
                    CCMainPage.ccMainPage(student);
                }
                //CCMainPage.ccMainPage(student);
            } catch (Exception e)
            {
                System.out.println("Camp ID not found");
            }
        }
    }

    /**
     * Allows a staff member to edit details of a camp they have created and allows navigation to the previous page
     * 
     * @param staff                 the staff member initiating the edit
     * @throws PageBackException    if the user chooses to go back during the operation
     */
    public static void editCampDetails(Staff staff) throws PageBackException
    {
        ChangePage.changePage();
        System.out.println("View Created Camps");
        List<Camp> campList = CampDatabase.getInstance().findByRules(p -> p.getStaffID().equalsIgnoreCase(staff.getID()));
        ModelViewer.displayListOfDisplayable(campList);
        System.out.println("");
        System.out.println("Enter the CampID that you would like to edit: ");
        String option = new Scanner(System.in).nextLine().trim();

        if(!option.isEmpty())
        {
            Camp campToEdit = findCampByID(campList, option);
            ChangePage.changePage();
            generateCampToEdit(campToEdit);
            if(campToEdit != null)
            {
                modifyCampDetails(campToEdit);
                System.out.println("Camp details edited successfully.");
                System.out.println("Press enter to go back.");
                Scanner scanner = new Scanner(System.in);
                scanner.nextLine();
                throw new PageBackException();
            }
            else
            {
                System.out.println("Camp not found with the specified Camp ID.");
            }
        }
    }

    /**
     * Finds a camp in the list based on its unique ID
     * 
     * @param campList  the list of camps to search from
     * @param campID    the ID of the camp to find in the list
     * @return          the camp with the specified ID or null if not found
     */
    public static Camp findCampByID(List<Camp> campList, String campID)
    {
        for(Camp camp : campList)
        {
            if(camp.getID().equalsIgnoreCase(campID))
            {
                return camp;
            }
        }
        return null;
    }

    /**
     * Modifies specific details of a camp based on the user input and allows navigation to the previous page
     * 
     * @param campToEdit            the camp to be modified
     * @throws PageBackException    if the user chooses to go back during the operation
     */
    private static void modifyCampDetails(Camp campToEdit) throws PageBackException
    {
        System.out.println("Editing camp with CampID: " + campToEdit.getID());
        String ID = campToEdit.getID();
        System.out.println("\t1. Edit Camp Title");
        System.out.println("\t2. Change Camp Status");
        System.out.println("\t3. Edit Start Date");
        System.out.println("\t4. Edit End Date");

        System.out.println();
        System.out.print("Please enter your choice: ");

        int choice = IntGetter.readInt();

        try {
                switch (choice) {
                    case 1 -> CampManager.changeCampTitle(ID);
                    case 2 -> CampManager.changeCampStatus(ID);
                    default -> {
                        System.out.println("Invalid choice. Please press enter to try again.");
                        new Scanner(System.in).nextLine();
                        throw new PageBackException();
                    }
                }
            } catch (Exception e) {
                modifyCampDetails(campToEdit);
            }
    }

    /**
     * Displays details of a camp to be edited
     * 
     * @param campToEdit    the camp to be displayed for editing
     */
    public static void generateCampToEdit(Camp campToEdit)
    {
        ModelViewer.displaySingleDisplayable(campToEdit);
        return;
    }

    /**
     * Displays the camps that a student is registered for and allows navigation to the previous page
     * 
     * @param student               the student for whom registered camps are to be displayed
     * @throws PageBackException    if the user chooses to go back during the operation
     */
    public static void viewRegisteredCamps(Student student) throws PageBackException
    {
        ChangePage.changePage();
        String registeredCampID = student.getRegisteredCampIDs();

        if(registeredCampID != null && !registeredCampID.isEmpty())
        {
            String[] campIDs = registeredCampID.split(",");

            for(String campID : campIDs)
            {
                try
                {
                    Camp camp = CampDatabase.getInstance().getByID(campID.trim());
                    if(camp != null)
                    {
                        String role = student.getCCId();
                        String display;
                        if(role.equals(campID))
                        {
                            display = "You are a Camp Committee member of this camp";
                        }
                        else
                        {
                            display = "You are a Camp Attendee of this camp";
                        }
                        ModelViewer.displaySingleDisplayable(camp);
                        System.out.println("");
                        System.out.println(display);
                        throw new PageBackException();
                    }
                } catch (Exception e)
                {
                    System.out.println("");
                }
            }
            System.out.println("Press Enter to go back");
            new Scanner(System.in).nextLine();
            throw new PageBackException();
        }
    }
 
    /**
     * Displays the list of registered students for a specific camp, allowing navigation to the previous page
     * 
     * @param staff                 the staff member viewing the list of registered students
     * @throws PageBackException    if the user chooses to go back during the operation
     */
    public static void viewRegisteredStudents(Staff staff) throws PageBackException
    {
        try
        {
            ChangePage.changePage();
            List<Camp> campList = CampDatabase.getInstance().findByRules(p -> p.getStaffID().equalsIgnoreCase(staff.getID()));
            ModelViewer.displayListOfDisplayable(campList);
            System.out.println("");
            System.out.println("Enter the CampID that you would like to view the list of registered students: ");
            String campID = new Scanner(System.in).nextLine().trim().toUpperCase();
            
            if(!campID.isEmpty())
            {
                Camp campToEdit = findCampByID(campList, campID);
                ChangePage.changePage();
                generateCampToEdit(campToEdit);
                if(campToEdit != null)
                {
                    String registeredStudentID = campToEdit.getStudentID();

                    if(registeredStudentID != null && !registeredStudentID.isEmpty())
                    {
                        String[] studentIDs = registeredStudentID.split(",");
                        for(String studentID : studentIDs)
                        {
                            try
                            {
                                Student student = StudentDatabase.getInstance().getByID(studentID.trim());
                                if(student != null)
                                {
                                    ModelViewer.displaySingleDisplayable(student);
                                    //StaffMainPage.staffMainPage(staff);
                                }
                            } catch (Exception e)
                            {
                                System.out.println("");
                            }
                        }
                    }
                    System.out.println("Press Enter to go back.");
                    Scanner scanner = new Scanner(System.in);
                    scanner.nextLine();
                    StaffMainPage.staffMainPage(staff);
                }
                else
                {
                    System.out.println("Camp not found with the specified Camp ID.");
                }
            }
        } catch (Exception e)
        {
            System.out.println("");
        }
        
    }
}
