package team42.cs2340.gatech.buzzshelter.model;

/**
 * Represents the admin user
 * @author ckadi
 * @version 1.0
 * @since 2/26/18
 */

public class BasicUser extends User {
    private boolean accountLocked;

    /**
     * Initializes the uid, name, and email of the basic user
     * @param uid the basic users's user id
     * @param name the basic user's name
     * @param email the basic user's email
     */
    public BasicUser(String uid, String name, String email) {
        super(uid, name, email);
    }
}
