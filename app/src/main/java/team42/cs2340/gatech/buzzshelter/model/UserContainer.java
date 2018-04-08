package team42.cs2340.gatech.buzzshelter.model;

/**
 * container for ferrying a User to and from db
 */

public class UserContainer {
    public String name;
    public String email;
    public String role;
    public String gender;
    public Integer age;
    public String currentShelter;
    public Integer numReservations;
    // ... more attributes for db

    /**
     * Initializes the user container which contains information about the user
     * @param user the user which is to be intialized
     */
    public UserContainer(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = (AdminUser.class.equals(user.getClass()))
                ? "admin"
                : (ShelterEmployee.class.equals(user.getClass()) ? "employee" : "basic");
        if (BasicUser.class.equals(user.getClass())) {
            this.currentShelter = ((BasicUser) user).getCurrentShelterId();
            this.numReservations = ((BasicUser) user).getNumReservations();
        }
    }

    /**
     * No arg constructor required by firebase
     */
    public UserContainer() {

    }

}
