package team42.cs2340.gatech.buzzshelter.model;

/**
 * Represents the admin user
 * @author ckadi
 * @version 1.0
 * @since 2/26/18
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
