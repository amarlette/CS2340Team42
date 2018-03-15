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

    public UserContainer(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user instanceof AdminUser
                ? "admin"
                : ((user instanceof ShelterEmployee) ? "employee" : "basic");
        if (user instanceof BasicUser) {
            this.currentShelter = ((BasicUser) user).getCurrentShelterId();
            this.numReservations = ((BasicUser) user).getNumReservations();
        }
    }

    public UserContainer() {

    }

}
