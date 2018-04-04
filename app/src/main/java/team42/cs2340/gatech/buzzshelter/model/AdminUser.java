package team42.cs2340.gatech.buzzshelter.model;

/**
 * Represents a user with administrative privileges.
 */
public class AdminUser extends User {
    public AdminUser(String uid, String name, String email) {
        super(uid, name, email);
    }
}
