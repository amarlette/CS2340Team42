package team42.cs2340.gatech.buzzshelter.model;

/**
 * Created by ckadi on 2/26/2018.
 */

public abstract class User {
    private String name;
    private String email;
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
