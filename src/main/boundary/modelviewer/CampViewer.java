package main.boundary.modelviewer;

import main.boundary.account.Logout;
import main.boundary.account.ResetPassword;
import main.boundary.account.ViewUserProfile;
import main.boundary.mainpage.StaffMainPage;
import main.controller.camp.CampManager;
import main.database.camp.CampDatabase;
import main.database.user.StaffDatabase;
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

public class CampViewer 
{
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

    public static void generateDetailsByStudentID() throws PageBackException 
    {
        System.out.println("Enter the StudentID to search");
        String s1 = new Scanner(System.in).nextLine();
        ModelViewer.displayListOfDisplayable(CampDatabase.getInstance().findByRules(p -> Objects.equals(p.getStudentID(), s1)));
        System.out.println("Enter <Enter> to continue");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

    public static void generateDetailsByStatus() throws PageBackException 
    {
        CampStatus status = getCampStatus();
        ModelViewer.displayListOfDisplayable(CampDatabase.getInstance().findByRules(p -> Objects.equals(p.getStatus(), status)));
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

    public static void viewAllCamp() throws PageBackException 
    {
        ChangePage.changePage();
        System.out.println("View All Camps");
        ModelViewer.displayListOfDisplayable(CampManager.viewAllCamp());
        System.out.println("Press Enter to go back.");
        new Scanner(System.in).nextLine();
        throw new PageBackException();
    }

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

    private static Camp findCampByID(List<Camp> campList, String campID)
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

    public static void generateCampToEdit(Camp campToEdit)
    {
        ModelViewer.displaySingleDisplayable(campToEdit);
        return;
    }

    public static void deleteCamp(Staff staff) throws PageBackException
    {
        ChangePage.changePage();
        System.out.println("View Created Camps");
        List<Camp> campList = CampDatabase.getInstance().findByRules(p -> p.getStaffID().equalsIgnoreCase(staff.getID()));
        ModelViewer.displayListOfDisplayable(campList);
        System.out.println("");
        System.out.println("Enter the CampID that you would like to delete:");
        String option = new Scanner(System.in).nextLine().trim().toUpperCase();
        System.out.println("You are about to delete Camp with Camp ID " + option + ". Are you sure?");
        System.out.println("Press enter to confirm or [b] to go back.");

        String userInput = new Scanner(System.in).nextLine().trim();

        if(userInput.isEmpty())
        {
            try
            {
            System.out.println("Confirmed... Deleting.");
            Camp campToDelete = findCampByID(campList, option);
            if(campToDelete != null)
            {
                CampDatabase.getInstance().remove(campToDelete.getID());
                System.out.println("Camp with Camp ID " + option + " deleted successfully.");
                System.out.println("Press Enter to go back.");
                new Scanner(System.in).nextLine();
                throw new PageBackException();
            }
            else
            {
                System.out.println("Camp not found with CampID " + option + ".");
            }

            throw new PageBackException();
            } catch (UserErrorException e)
            {
                System.out.println("Error Deleting Camp");
            }
        }
        else if(userInput.equalsIgnoreCase("b"))
        {
            System.out.println("Going back...");
            throw new PageBackException();
        }
        else
        {
            System.out.println("Invalid choice. Please press enter to confirm or [b] to go back.");
            deleteCamp(staff);
        }
    }

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
                        ModelViewer.displaySingleDisplayable(camp);
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


}
