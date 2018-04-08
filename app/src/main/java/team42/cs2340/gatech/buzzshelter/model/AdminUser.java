package team42.cs2340.gatech.buzzshelter.model;

/**
 * Represents a user with administrative privileges
 */
public class AdminUser extends User {
    /**
     * Initializes the uid, name, and email of the admin user
     * @param uid the admin's user id
     * @param name the admin's name
     * @param email the admin's email
     */
    public AdminUser(String uid, String name, String email) {
        super(uid, name, email);
    }
}
