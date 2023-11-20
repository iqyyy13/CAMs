package main.controller.suggestion;

import main.database.suggestion.SuggestionDatabase;
import main.model.suggestion.Suggestion;
import main.utils.exception.UserAlreadyExistsException;
import main.utils.exception.UserErrorException;
import main.utils.ui.ChangePage;

import java.util.List;
import java.util.Scanner;

public class SuggestionManager {
    
    public static List<Suggestion> viewAllSuggestions() {
        return SuggestionDatabase.getInstance().getList();
    }

    public static void createSuggestion(String msg, int suggestionID, Boolean approve, String committeeUserID, String staffID, String campID)
        throws UserAlreadyExistsException {
    Suggestion suggestion = new Suggestion(msg, suggestionID, approve, committeeUserID, staffID, campID);
    SuggestionDatabase.getInstance().add(suggestion);
}

    public static Suggestion createSuggestion(String msg, Boolean approve, String committeeUserID, String staffID, String campID)
            throws UserAlreadyExistsException {
        ChangePage.changePage();
        System.out.println("Enter the suggestion message: ");
        String newMsg = new Scanner(System.in).nextLine().trim();
        Suggestion suggestion = new Suggestion(newMsg, getNewSuggestionID(), approve, committeeUserID, staffID, campID);
        SuggestionDatabase.getInstance().add(suggestion);
        return suggestion;  // Add this line if you want to return the created suggestion
    }

    public static List<Suggestion> getAllSuggestions() {
        return SuggestionDatabase.getInstance().getList();
    }

    public static boolean repositoryIsEmpty() {
        return SuggestionDatabase.getInstance().isEmpty();
    }

    public static boolean containsSuggestionByID(String suggestionID) {
        return SuggestionDatabase.getInstance().contains(suggestionID);
    }

    public static Suggestion getByID(String suggestionID) throws UserErrorException {
        return SuggestionDatabase.getInstance().getByID(suggestionID);
    }

    public static String getNewSuggestionID() {
        int max = 0;
        for (Suggestion s : SuggestionDatabase.getInstance()) {
            int id = s.getSuggestionID();
            if (id > max) {
                max = id;
            }
        }
        return String.valueOf(max + 1);
    }

    public static void updateAllSuggestions(List<Suggestion> suggestions) {
        SuggestionDatabase.getInstance().updateAll(suggestions);
    }
    
    // You can add more methods based on your requirements
}
