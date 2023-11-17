package main.model.camp;

public enum CampStatus {

    AVAILABLE,

    ALLOCATED,

    UNAVAILABLE;

    public String colorString() 
    {
        return switch (this) 
        {
            case AVAILABLE -> "\u001B[32m" + this + "\u001B[0m"; // green
            case UNAVAILABLE -> "\u001B[31m" + this + "\u001B[0m"; // red
            case ALLOCATED -> "\u001B[34m" + this + "\u001B[0m"; // blue
        };
    }

}
