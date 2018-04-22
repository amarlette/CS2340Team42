package team42.cs2340.gatech.buzzshelter.model;

/**
 * Represents a shelter employee user.
 */
public class ShelterEmployee extends User {
    //private Shelter shelter;

    /**
     * Initializes a new shelter employee
     * @param uid the shelter employee's user id
     * @param name the shelter employee's name
     * @param email the shelter employee's email
     */
    public ShelterEmployee(String uid, String name, String email) {
        super(uid, name, email);

    }

    /*
    public ShelterEmployee(String uid, String name, String email, Shelter shelter) {
        super(uid, name, email);
        this.shelter = shelter;

    }


    public void changeShelterOccupancy() {}
    public void changeShelterCapacity(){}

    */
}
