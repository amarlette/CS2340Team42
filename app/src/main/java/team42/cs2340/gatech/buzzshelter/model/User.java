package team42.cs2340.gatech.buzzshelter.model;

/**
 * Represents the admin user
 * @author ckadi
 * @version 1.0
 * @since 2/26/18
 */
public abstract class User {
    /** user's key in user db table */
    private String uid;

    private String name;
    private String email;

    /**
     * Initializes a user
     * @param uid the user's user id
     * @param name the user's name
     * @param email the user's email
     */
    public User(String uid, String name, String email) {
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
