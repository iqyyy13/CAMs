package main.model.user;

public class UserFactory {
    /**
     * Creates a new User object based on the given parameters.
     *
     * @param userType  The type of user to be created (student, faculty, or coordinator).
     * @param userID    The user's ID.
     * @param password  The user's password in plaintext.
     * @param name      The user's name.
     * @param email     The user's email address.
     * @param faculty   The user's faculty
     * @return          A new User object of the specified type.
     */
    public static User create(UserType userType, String userID, String password, String name, String email, String faculty) 
    {
        return switch (userType)
        {
            case STUDENT -> new Student(userType, userID, name, email, faculty, password);
            case STAFF -> new Student();
            case CC -> new Student();
        };
    }
}
