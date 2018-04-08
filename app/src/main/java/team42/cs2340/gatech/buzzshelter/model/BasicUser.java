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
        return hasReservation() ? currentShelterId : null;
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

    /**
     * sets the number of reservations the user currently holds,
     * does not reset currentShelterId on numReservations == 0
     *
     * @param numReservations new number of reservations held by the user
     */
    public void setNumReservations(int numReservations) {
        this.numReservations = numReservations;
    }
}
