package main.utils.exception;

/**
 * The UserAlreadyExistsException class is a custom exception that is thrown when an attempt is made to add a model to a database 
 * that already contains a model with the same ID.
 * It extends the Exception class.
 */
public class UserAlreadyExistsException extends Exception 
{
    /**
     * Creates a new instance of the ModelAlreadyExistsException class with a default error message.
     * The default message is "Model already exists".
     */
    public UserAlreadyExistsException() 
    {
        super("User already exists");
    }

    /**
     * Creates a new instance of the ModelAlreadyExistsException class with a custom error message.
     *
     * @param message The custom error message to be used.
     */
    public UserAlreadyExistsException(String message) 
    {
        super(message);
    }
}
