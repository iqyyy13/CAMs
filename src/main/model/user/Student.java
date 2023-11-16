package main.model.user;

import main.utils.parameters.EmptyID;
import main.utils.parameters.NotNull;

import java.util.Map;

/**
 * This class represents a student, which is a type of user.
 * It extends the User class and includes a student ID field.
 */
public class Student implements User {
    /**
     * The ID of the student.
     */
    private String studentID;
    /**
     * The name of a student
     */
    private String studentName;
    /**
     * The email of a student
     */
    private String email;
    /**
     * The faculty of a student
     */
    private String faculty;
    /**
     * The status of a student
     */
    private StudentStatus status;
    /**
     * The ID of the project
     */
    private String campID;
    private String password;

    private UserType userType;

    /**
     * Constructs a new Student object with the specified student ID and default password.
     *
     * @param studentID   the ID of the student.
     * @param studentName the name of the student.
     * @param email       the email of the student.
     * @param faculty     the faculty of the student
     */
    public Student(UserType userType, String studentID, String studentName, String email, String faculty) {
        this.userType = UserType.STUDENT;
        this.studentID = studentID;
        this.studentName = studentName;
        this.email = email;
        this.faculty = faculty;
        this.status = StudentStatus.UNREGISTERED;
        campID = EmptyID.EMPTY_ID;
    }

    /**
     * Constructs a new Student object with the specified student ID and password.
     *
     * @param studentID      the ID of the student.
     * @param studentName    the name of the student.
     * @param email          the email of the student.
     * @param faculty        the faculty of the student
     * @param password       the password of the student.
     */
    public Student(UserType userType, String studentID, String studentName, String email, String faculty, @NotNull String password) {
        this.userType  = UserType.STUDENT;
        this.studentID = studentID;
        this.studentName = studentName;
        this.email = email;
        this.faculty = faculty;
        this.status = StudentStatus.UNREGISTERED;
        campID = EmptyID.EMPTY_ID;
        this.password = password;
    }

    /**
     * Constructs a new Student object with the specified student ID and password.
     *
     * @param informationMap the map
     */
    public Student(Map<String, String> informationMap) {
        fromMap(informationMap);
    }

    /**
     * default constructor for Student class
     */
    public Student() {
        super();
        this.email = EmptyID.EMPTY_ID;
        this.studentID = EmptyID.EMPTY_ID;
        this.studentName = EmptyID.EMPTY_ID;
        this.faculty = EmptyID.EMPTY_ID;
        this.userType = UserType.STUDENT;
        //this.password = EmptyID.EMPTY_ID;
        this.status = StudentStatus.UNREGISTERED;
    }

    /**

     Creates a new Student object based on the information in the provided map.
     The map should contain the necessary information to construct the Student object,
     such as the student's name, email, and ID.
     @param informationMap a map containing the information required to create a new Student object
     @return a new Student object with the information provided in the map
     */
    public static User getUser(Map<String, String> informationMap) {
        return new Student(informationMap);
    }

    /**
     * Gets the email of the user
     *
     * @return the email of the user
     */
    @Override
    public String getID() {
        return this.studentID;
    }

    /**
     * Gets the username of the user
     *
     * @return the name of the user
     */
    @Override
    public String getUserName() {
        return this.studentName;
    }

    /**
     * Gets the email of the user
     *
     * @return the email of the user
     */
    @Override
    public String getEmail() {
        return this.email;
    }

    /**
     * Gets the faculty of the user
     *
     * @return the faculty of the user
     */
    @Override
    public String getFaculty() {
        return this.faculty;
    }

    /**
     * Gets the status of the student
     *
     * @return the status of the student
     */
    public StudentStatus getStatus() {
        return this.status;
    }

    /**
     * Sets the status of the student
     *
     * @param status the new status of the student
     */
    public void setStatus(StudentStatus status) {
        this.status = status;
    }

    /**
     * Gets the ID of the camp
     *
     * @return the ID of the camp
     */
    public String getCampID() {
        return campID;
    }

    /**
     * Sets the ID of the camp
     *
     * @param campID the ID of the camp
     */
    public void setCampID(String campID) {
        this.campID = campID;
    }

    /**
     * getter for the password
     *
     * @return 
     */
    public String getPassword() {
        return password;
    }

    /**
     * setter for the password
     *
     * @param password the password that to be set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }
}
