package team42.cs2340.gatech.buzzshelter.model;

/**
 * An abstraction of a user of the application.
 */
public abstract class User {
    /** user's key in user db table */
    private final String uid;

    private final String name;
    private final String email;

    User(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUid() {
        return uid;
    }
}
