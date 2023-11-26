package main.utils.exception;

/**
 * The UserErrorException class is a custom exception that is thrown when a requested model cannot be found in a database.
 * It extends the Exception class.
 */
public class UserErrorException extends Exception 
{
    /**
     * Creates a new instance of the UserErrorException class with a default error message.
     * The default message is "User not found".
     */
    public UserErrorException() 
    {
        super("User not found");
    }

    /**
     * Creates a new instance of the UserErrorException class with a custom error message.
     *
     * @param message The custom error message to be used.
     */
    public UserErrorException(String message) 
    {
        super(message);
    }
}
