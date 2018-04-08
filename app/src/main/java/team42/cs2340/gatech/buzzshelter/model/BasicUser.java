package team42.cs2340.gatech.buzzshelter.model;

/**
 * Represents a basic user with minimal privileges.
 */
public class BasicUser extends User {
    private boolean accountLocked;
    private String currentShelterId;
    private int numReservations;

    /**
     * Initializes the uid, name, and email of the basic user
     * @param uid the basic users's user id
     * @param name the basic user's name
     * @param email the basic user's email
     */
    public BasicUser(String uid, String name, String email) {
        this(uid, name, email, null);
    }

    /**
     * Initializes a basic user
     * @param uid the user id
     * @param name the name
     * @param email the email
     * @param shelterId the shelter id
     */
    public BasicUser(String uid, String name, String email, String shelterId) {
        super(uid, name, email);
        this.currentShelterId = shelterId;

    }

    /**
     * @return the current shelter ID
     */
    public String getCurrentShelterId() {
        return hasReservation() ? currentShelterId : null;
    }

    /**
     * Set the current shelter id
     * @param currentShelterId the current shelter id to set
     */
    public void setCurrentShelterId(String currentShelterId) {
        this.currentShelterId = currentShelterId;
    }

    /**
     * Determine if there is a reservation
     * @return whether or not a reservation exists
     */
    public boolean hasReservation() {
        return numReservations != 0;
    }

    /**
     * @return the number of reservations
     */
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
