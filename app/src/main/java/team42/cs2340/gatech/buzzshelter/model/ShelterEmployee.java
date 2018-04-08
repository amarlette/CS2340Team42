package team42.cs2340.gatech.buzzshelter.model;

/**
 * Represents the admin user
 * @author ckadi
 * @version 1.0
 * @since 2/26/18
 */
public class ShelterEmployee extends User {
    /**
     * Initializes a new shelter employee
     * @param uid the shelter employee's user id
     * @param name the shelter employee's name
     * @param email the shelter employee's email
     */
    public ShelterEmployee(String uid, String name, String email) {
        super(uid, name, email);
    }
}
