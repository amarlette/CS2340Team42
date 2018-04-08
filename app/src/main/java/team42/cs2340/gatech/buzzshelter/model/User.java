package team42.cs2340.gatech.buzzshelter.model;

/**
 * An abstraction of a user of the application.
 */
public abstract class User {
    /** user's key in user db table */
    private final String uid;

    private final String name;
    private final String email;

    /**
     * Initializes a user
     * @param uid the user's user id
     * @param name the user's name
     * @param email the user's email
     */
    User(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }

    /**
     * @return a string of the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return a string of the user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return a string of the user's user id
     */
    public String getUid() {
        return uid;
    }
}
