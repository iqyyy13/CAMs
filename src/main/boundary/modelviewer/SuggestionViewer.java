package main.boundary.modelviewer;

import main.boundary.account.Logout;
import main.boundary.account.ResetPassword;
import main.boundary.account.ViewUserProfile;
import main.boundary.mainpage.CCMainPage;
import main.boundary.mainpage.StaffMainPage;
import main.boundary.mainpage.StudentMainPage;
import main.controller.camp.CampManager;
import main.database.camp.CampDatabase;
import main.database.suggestion.SuggestionDatabase;
import main.database.user.StaffDatabase;
import main.database.user.StudentDatabase;
import main.model.Model;
import main.model.camp.Camp;
import main.model.camp.CampStatus;
import main.model.suggestion.Suggestion;
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

public class SuggestionViewer 
{
    public static void viewSuggestions(Staff staff)
    {
        ChangePage.changePage();
        String staffID = staff.getID();
        List<Suggestion> suggestionList = SuggestionDatabase.getInstance().findByRules(p-> p.getStaffID().equalsIgnoreCase(staffID));
        ModelViewer.displayListOfDisplayable(suggestionList);
        System.out.println("Press Enter to continue or [b] to go back");
        String userInput = new Scanner(System.in).nextLine().toLowerCase();
        if(userInput.equals("b"))
        {
            StaffMainPage.staffMainPage(staff);
        }
    }
    
    public static void viewOwnSuggestions(Student student)
    {
        ChangePage.changePage();
        String studentID = student.getID();
        List<Suggestion> suggestionList = SuggestionDatabase.getInstance().findByRules(p->p.getCommitteeUserID().equalsIgnoreCase(studentID));
        ModelViewer.displayListOfDisplayable(suggestionList);
        System.out.println("Press Enter to continue or [b] to go back.");
        String userInput = new Scanner(System.in).nextLine().toLowerCase();
        if(userInput.equals("b"))
        {
            CCMainPage.ccMainPage(student);
        }
    }

    public static Suggestion findSuggestionByID(List<Suggestion> suggestionList, String suggestionID)
    {
        for(Suggestion suggestion : suggestionList)
        {
            if(suggestion.getID().equalsIgnoreCase(suggestionID))
            {
                return suggestion;
            }
        }
        return null;
    }

    public static void generateSuggestionToEdit(Suggestion suggestionToEdit)
    {
        ModelViewer.displaySingleDisplayable(suggestionToEdit);
        return;
    }

    public static void editSuggestionDetails(Student student) throws PageBackException
    {
        ChangePage.changePage();
        viewOwnSuggestions(student);
        String studentID = student.getID();
        List<Suggestion> suggestionList = SuggestionDatabase.getInstance().findByRules(p->p.getCommitteeUserID().equalsIgnoreCase(studentID));
        System.out.println("");
        System.out.println("Enter the SuggestionID that you would like to edit: ");
        String option = new Scanner(System.in).nextLine().trim();

        if(!option.isEmpty())
        {
            Suggestion suggestionToEdit = findSuggestionByID(suggestionList, option);
            ChangePage.changePage();
            generateSuggestionToEdit(suggestionToEdit);
            if(suggestionToEdit != null)
            {
                System.out.println("Enter a new suggestion");
                Scanner scanner = new Scanner(System.in);
                String newSuggestionMessage = scanner.nextLine();
                suggestionToEdit.setMsg(newSuggestionMessage);
                try
                {
                    SuggestionDatabase.getInstance().update(suggestionToEdit);
                    System.out.println("Suggestion details edited successfully.");
                    System.out.println("Press enter to go back.");
                    scanner.nextLine();
                    throw new PageBackException();
                } catch (UserErrorException e)
                {
                    System.err.println("User Error: " + e.getMessage());
                }
            }
            else
            {
                System.out.println("Suggestion not found with the specified Suggestion ID.");
            }
        }
    }
}
