package team42.cs2340.gatech.buzzshelter.model;

/**
 * Created by ckadi on 2/26/2018.
 */

public abstract class User {
    /** user's key in user db table */
    private String uid;

    private String name;
    private String email;

    public User(String uid, String name, String email) {
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
