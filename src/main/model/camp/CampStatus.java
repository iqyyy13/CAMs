package main.model.camp;

/**
 * Enumeration representing the status of a camp
 */
public enum CampStatus 
{
    /**
     * Camp is available
     */
    AVAILABLE,

    /**
     * Camp is unavailable
     */
    UNAVAILABLE;

    /**
     * Returns a color-coded string representation of the camp status
     * @return A color-coded string representation.
     */
    public String colorString() 
    {
        return switch (this) 
        {
            case AVAILABLE -> "\u001B[32m" + this + "\u001B[0m"; // green
            case UNAVAILABLE -> "\u001B[31m" + this + "\u001B[0m"; // red
        };
    }

}
