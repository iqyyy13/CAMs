package main.controller.suggestion;

import main.database.suggestion.SuggestionDatabase;
import main.model.suggestion.Suggestion;
import main.model.suggestion.SuggestionStatus;
import main.model.user.Student;
import main.utils.exception.PageBackException;
import main.utils.exception.UserAlreadyExistsException;
import main.utils.exception.UserErrorException;
import main.utils.ui.ChangePage;
import main.boundary.modelviewer.SuggestionViewer;
import java.util.List;
import java.util.Scanner;
/**
 * The class provides methods for managing Suggestion list
 */

public class SuggestionManager 
{


    /**
     * Creates suggestion based on user inputs.
     */
    public static Suggestion createSuggestion(String msg, String approve, String committeeUserID, String staffID, String campID)
            throws UserAlreadyExistsException 
            {
                // generates a unique id number
        Suggestion suggestion = new Suggestion(msg, getNewSuggestionID(), approve, committeeUserID, staffID, campID);
        SuggestionDatabase.getInstance().add(suggestion);
        return suggestion;  
    }
    /**
     * Getting an id from list.
     */
    public static Suggestion getByID(String suggestionID) throws UserErrorException
    {
        return SuggestionDatabase.getInstance().getByID(suggestionID);
    }
    /**
     * Function to generate unique id.
     */
    public static String getNewSuggestionID() 
    {
        int max = 0;
        for (Suggestion s : SuggestionDatabase.getInstance()) 
        {
            int id = s.getSuggestionID();
            if (id > max) 
            {
                max = id;
            }
        }
        return String.valueOf(max + 1);
    }
    /**
     * Function to update list.
     */
    public static void updateAllSuggestions(List<Suggestion> suggestions) 
    {
        SuggestionDatabase.getInstance().updateAll(suggestions);
    }
    /**
     * Delete suggestion from list.
     */
    public static void deleteSuggestion(String suggestionID) throws UserErrorException {
        SuggestionDatabase.getInstance().remove(suggestionID);
    }
    /**
     * Edit suggestion from list.
     */
    public static void editSuggestionDetails(Student student) throws PageBackException {
        ChangePage.changePage();
        SuggestionViewer.viewOwnSuggestions(student);
        String studentID = student.getID();
        //find instance based on studentID
        List<Suggestion> suggestionList = SuggestionDatabase.getInstance().findByRules(p -> p.getCommitteeUserID().equalsIgnoreCase(studentID));
        System.out.println("");
        System.out.println("Enter the SuggestionID that you would like to edit: ");
        String option = new Scanner(System.in).nextLine().trim();
        //checks if empty
        if (!option.isEmpty()) {
            Suggestion suggestionToEdit = SuggestionViewer.findSuggestionByID(suggestionList, option);
            SuggestionViewer.generateSuggestionToEdit(suggestionToEdit);
            //check valid suggestion id
            if (suggestionToEdit != null) {
                //prevent editing from approved suggestions
                if (suggestionToEdit.getStatus() != SuggestionStatus.APPROVED) {
                    System.out.println("Enter a new suggestion");
                    Scanner scanner = new Scanner(System.in);
                    String newSuggestionMessage = scanner.nextLine();
                    suggestionToEdit.setMsg(newSuggestionMessage);
                    try {
                        SuggestionDatabase.getInstance().update(suggestionToEdit);
                        System.out.println("Suggestion details edited successfully.");
                        System.out.println("Press enter to go back.");
                        scanner.nextLine();
                        throw new PageBackException();
                    } catch (UserErrorException e) {
                        System.err.println("User Error: " + e.getMessage());
                    }
                } else {
                    System.out.println("Unable to edit as suggestion is approved.");
                    System.out.println("Press enter to go back.");
                    Scanner back = new Scanner(System.in);
                    back.nextLine();
                    throw new PageBackException();
                }
            } else {
                System.out.println("Suggestion not found with the specified Suggestion ID.");
            }
        }
    }
    
}


