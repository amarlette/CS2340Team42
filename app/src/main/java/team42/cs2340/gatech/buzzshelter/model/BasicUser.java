package team42.cs2340.gatech.buzzshelter.model;

/**
 * Created by ckadi on 2/26/2018.
 */

public class BasicUser extends User {
    private boolean accountLocked;

    public BasicUser(String uid, String name, String email) {
        super(uid, name, email);
    }
}
