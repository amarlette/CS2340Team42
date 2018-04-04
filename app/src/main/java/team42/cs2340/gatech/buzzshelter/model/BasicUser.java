package team42.cs2340.gatech.buzzshelter.model;

/**
 * Represents a basic user with minimal privileges.
 */
public class BasicUser extends User {
    private boolean accountLocked;
    private String currentShelterId;
    private int numReservations;

    public BasicUser(String uid, String name, String email) {
        this(uid, name, email, null);
    }
    public BasicUser(String uid, String name, String email, String shelterId) {
        super(uid, name, email);
        this.currentShelterId = shelterId;
    }

    public String getCurrentShelterId() {
        return currentShelterId;
    }

    public void setCurrentShelterId(String currentShelterId) {
        this.currentShelterId = currentShelterId;
    }

    public boolean hasReservation() {
        return numReservations != 0;
    }

    public int getNumReservations() {
        return numReservations;
    }

    public void setNumReservations(int numReservations) {
        this.numReservations = numReservations;
        if (numReservations == 0) {
            this.currentShelterId = null;
        }
    }
}
