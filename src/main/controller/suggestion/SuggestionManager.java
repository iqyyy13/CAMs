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
     * Creates a suggestion based on user inputs
     * 
     * @param msg               The message of the suggestion
     * @param committeeUserID   The committee user ID associated with the suggestion
     * @param staffID           The staff ID associated with the suggestion
     * @param campID            The camp ID associated with the suggestion
     * @return                  The created suggestion
     * @throws UserAlreadyExistsException   If the suggestion already exists
     */
    public static Suggestion createSuggestion(String msg, String committeeUserID, String staffID, String campID)
            throws UserAlreadyExistsException 
            {
                // generates a unique id number
        Suggestion suggestion = new Suggestion(msg, getNewSuggestionID(), committeeUserID, staffID, campID);
        SuggestionDatabase.getInstance().add(suggestion);
        return suggestion;  
    }

    /**
     * Gets a suggestion by ID
     * @param suggestionID          The ID of the suggestion
     * @return                      The suggestion with the specified ID
     * @throws UserErrorException   If the suggestion is not found
     */
    public static Suggestion getByID(String suggestionID) throws UserErrorException
    {
        return SuggestionDatabase.getInstance().getByID(suggestionID);
    }

    /**
     * Generates a new unique suggestion ID
     * 
     * @return  The new suggestion ID
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
     * Updates the Suggestion list with the provided list of suggestion
     * 
     * @param suggestions The list of suggestions to update.
     */
    public static void updateAllSuggestions(List<Suggestion> suggestions) 
    {
        SuggestionDatabase.getInstance().updateAll(suggestions);
    }

    /**
     * Deletes a suggestion from the list
     * 
     * @param suggestionID          The ID of the suggestion to be deleted
     * @throws UserErrorException   If the suggestion is not found
     */
    public static void deleteSuggestion(String suggestionID) throws UserErrorException {
        SuggestionDatabase.getInstance().remove(suggestionID);
    }

    /**
     * Edits details of a suggestion from the list
     * 
     * @param student The student making the edit
     * @throws PageBackException If the user chooses to go back to the previous page
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
            //check valid suggestion id
            if (suggestionToEdit != null) {
                //prevent editing from approved suggestions
                ChangePage.changePage();
                SuggestionViewer.generateSuggestionToEdit(suggestionToEdit);
                if (suggestionToEdit.getStatus() == SuggestionStatus.PENDING) {
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
                    System.out.println("Unable to edit as suggestion is approved/disapproved.");
                    System.out.println("Press enter to go back.");
                    Scanner back = new Scanner(System.in);
                    back.nextLine();
                    throw new PageBackException();
                }
            } else {
                System.out.println("Suggestion not found with the specified Suggestion ID.");
                System.out.println("Press Enter to retry");
                Scanner scanner = new Scanner(System.in);
                scanner.nextLine();
                editSuggestionDetails(student);
            }
        }
    }
}


