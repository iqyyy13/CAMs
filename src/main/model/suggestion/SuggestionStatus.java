package main.model.suggestion;

/**
 * Enumeration representing the status of a suggestion
 */
public enum SuggestionStatus 
{
    /**
     * Suggestion is available
     */
    PENDING,

    /**
     * Suggestion is allocated
     */
    APPROVED,

    /**
     * Suggestion is unavailable
     */
    DISAPPROVED;

    /**
     * Returns a color-coded string representation of the suggestion status
     * @return A color-coded string representation.
     */
    public String colorString() 
    {
        return switch (this) 
        {
            case APPROVED -> "\u001B[32m" + this + "\u001B[0m"; // green
            case DISAPPROVED -> "\u001B[31m" + this + "\u001B[0m"; // red
            case PENDING -> "\u001B[34m" + this + "\u001B[0m"; // blue
        };
    }

}
