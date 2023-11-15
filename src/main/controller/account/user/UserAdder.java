package main.controller.account.user;

import main.model.user.Student;
import main.model.user.User;
import main.utils.exception.UserAlreadyExistsException;

/**
 * The UserAdder class provides a utility for adding users to the database.
 */
public class UserAdder {
    /**
     * Adds the specified user to the database.
     *
     * @param user the user to be added
     * @throws ModelAlreadyExistsException if the user already exists in the database
     */
    public static void addUser(User user) throws UserAlreadyExistsException {
        if (user instanceof Student student) 
        {
            //addStudent(student);
        }
    }
}
