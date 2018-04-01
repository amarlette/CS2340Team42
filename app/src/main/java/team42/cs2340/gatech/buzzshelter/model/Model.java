package team42.cs2340.gatech.buzzshelter.model;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Facade to model.
 */

public final class Model {
    /** singleton instance */
    private static final Model instance = new Model();

    /** holds all shelters, mapped to their db key */
    private final Map<String, Shelter> shelters;

    /** holds the current filtered list of shelters */
    private List<Shelter> filteredShelters;

    /** the currently selected shelter, defaults to first shelter */
    private Shelter currentShelter;

    /** access to database */
    private final DatabaseReference mDatabase;

    private final FirebaseAuth mAuth;

    public static Model getInstance() {
        return instance;
    }

    /** current user */
    private User currentUser;

    /**
     * create a new model
     */
    private Model() {
        // TODO: read from database only if logged in
        // connect to and read from database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        filteredShelters = null;

        DatabaseReference shelterRef = mDatabase.child("shelters");
        shelterRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                shelters.remove(dataSnapshot.getKey());
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Shelter shelter = dataSnapshot.getValue(Shelter.class);
                shelter.setKey(dataSnapshot.getKey());
                shelters.put(shelter.getKey(), shelter);
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Shelter shelter = dataSnapshot.getValue(Shelter.class);
                shelter.setKey(dataSnapshot.getKey());
                shelters.put(shelter.getKey(), shelter);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        shelters = new HashMap<>();
    }

    /**
     * populate the model with currently existing shelter data from database
     */
    private void loadShelters() {
        // TODO: pull shelters from db, populate shelter list
        // likely not necessary, handle above with child listener, real time updates
        // remove function upon implementation
    }

    /**
     * get the shelters
     * @return a list of the shelters in the app
     */
    public List<Shelter> getShelters() {
        return new ArrayList<>(shelters.values());
    }

    public Map<String, Shelter> getShelterDictionary() { return shelters; }
    /**
     * add a shelter to the app. checks if duplicate
     *
     * @param shelter  the shelter to be added
     * @return true if added, false if a duplicate
     */
    public boolean addShelter(Shelter shelter) {
        // TODO: check whether shelter exists (query db real time)
        // TODO: add shelter if not exists, admin only

        loadShelters(); // update shelter list with up to date data
        return true;
    }

    public boolean updateShelter(Shelter shelter) {

        // TODO: update an existing shelter using its db key, employee only
        return true;
    }

    /**
     *
     * @return  the currently selected shelter
     */
    public Shelter getCurrentShelter() {
        return currentShelter;
    }

    public void setCurrentShelter(Shelter shelter) {
        currentShelter = shelter;
    }

    // TODO: get shelter by its details (search)

//    /**
//     * make a reservation on behalf of a user for the current shelter
//     *
//     * @param user the user to make the request on behalf of
//     * @return true if reservation request successful, false otherwise
//     */
//    public boolean addUserToShelter(User user) {
//        return user != null && currentShelter.reserve(user);
//    }
    public boolean makeReservation(int numReservations) {
        if (currentUserHasReservation() || (numReservations == 0)) {
            return false;
        }
        try {
            incrementShelterOccupancy(numReservations);
            ((BasicUser) currentUser).setNumReservations(numReservations);
            ((BasicUser) currentUser).setCurrentShelterId(currentShelter.getKey());
        } catch (IllegalStateException e) {
            return false;
        }
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
        Map<String, Object> userMap = new HashMap<>();

        UserContainer userDetails = new UserContainer(currentUser);
        userMap.put(currentUser.getUid(), userDetails); // additional details
        userRef.updateChildren(userMap); // update user in database

        return true;
    }
    public boolean cancelReservation() {
        if (!currentUserHasReservation()) {
            return false;
        }
        try {
            Shelter userHome = shelters.get(((BasicUser) currentUser).getCurrentShelterId());
            decrementShelterOccupancy(userHome, ((BasicUser) currentUser).getNumReservations());
            ((BasicUser) currentUser).setNumReservations(0);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return false;
        }

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
        Map<String, Object> userMap = new HashMap<>();

        UserContainer userDetails = new UserContainer(currentUser);
        userMap.put(currentUser.getUid(), userDetails); // additional details
        userRef.updateChildren(userMap); // update user in database

        return true;
    }
    public User getCurrentUser() {
        return currentUser;
    }

    public boolean setCurrentUser(User user) {
        this.currentUser = user;
        return true;
    }

    public void setCurrentUser(DataSnapshot dataSnapshot) {
        UserContainer userDetails = dataSnapshot.getValue(UserContainer.class);
        User user;
        String uid = mAuth.getCurrentUser().getUid();

        if ("admin".equals(userDetails.role)) {
            user = new AdminUser(uid, userDetails.name, userDetails.email);
        } else if ("employee".equals(userDetails.role)) {
            user = new ShelterEmployee(uid, userDetails.name, userDetails.email);
        } else {
            user = new BasicUser(uid, userDetails.name, userDetails.email, userDetails.currentShelter);
            ((BasicUser) user).setCurrentShelterId(userDetails.currentShelter);
            ((BasicUser) user).setNumReservations(userDetails.numReservations);
        }
        this.currentUser = user;
    }

    public void signoutUser() {
        mAuth.signOut();
        currentUser = null;
        currentShelter = null;
    }

    public void setFilteredShelters(List<Shelter> filteredShelters) {
        this.filteredShelters = filteredShelters;
    }

    public List<Shelter> getFilteredShelters() {
        return filteredShelters;
    }

    private void incrementShelterOccupancy(int step) {
        int cap = Integer.parseInt(currentShelter.getCapacity());
        int occ = Integer.parseInt(currentShelter.getOccupancy());
        if ((occ + step) > cap) {
            throw new IllegalStateException("Capacity cannot be exceeded!");
        } else {
            currentShelter.setOccupancy(Integer.toString(occ + step));

            int newOcc = Integer.parseInt(currentShelter.getOccupancy());
            newOcc += ((BasicUser) currentUser).getNumReservations();
            currentShelter.setOccupancy(Integer.toString(newOcc));

            DatabaseReference shelterRef = mDatabase.child("shelters");
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put(currentShelter.getKey(), currentShelter.toMap());
            shelterRef.updateChildren(childUpdates);
        }
    }

    private void decrementShelterOccupancy(Shelter shelter, int step) {
        int occ = Integer.parseInt(shelter.getOccupancy());
        if ((occ - step) < 0) {
            throw new IllegalStateException("Occupancy cannot be negative!");
        } else {
            if (shelter.getKey().equals(currentShelter.getKey())) {
                currentShelter.setOccupancy(Integer.toString(occ-step));
            } else {
                shelter.setOccupancy(Integer.toString(occ - step));
            }
            DatabaseReference shelterRef = mDatabase.child("shelters");
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put(shelter.getKey(), shelter.toMap());
            shelterRef.updateChildren(childUpdates);
        }
    }

    public int getMaxReservations(Shelter shelter) {
        int max = 5;
        return Math.min(shelter.getVacancies(), max);

    }

    public boolean currentUserHasReservation() {
        return ((BasicUser) currentUser).hasReservation();
    }
}
