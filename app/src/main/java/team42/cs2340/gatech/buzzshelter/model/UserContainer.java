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

    public UserContainer(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user instanceof AdminUser
                ? "admin"
                : ((user instanceof ShelterEmployee) ? "employee" : "basic");
    }

    public UserContainer() {

    }

}
