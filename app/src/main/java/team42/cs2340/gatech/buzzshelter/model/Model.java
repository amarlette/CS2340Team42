package team42.cs2340.gatech.buzzshelter.model;

import android.support.compat.BuildConfig;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Facade to model.
 */

public class Model {
    /** singleton instance */
    private static final Model instance = new Model();

    /** holds the list of all shelters */
    private List<Shelter> shelters;

    /** the currently selected course, defaults to first shelter */
    private Shelter currentShelter;

    /** access to database */
    private DatabaseReference mDatabase;

    public static Model getInstance() {
        return instance;
    }

    /**
     * create a new model
     */
    private Model() {
        // connect to and read from database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference shelterRef = mDatabase.child("shelters");
        shelterRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Shelter shelter = dataSnapshot.getValue(Shelter.class);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Shelter shelter = dataSnapshot.getValue(Shelter.class);
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Shelter shelter = dataSnapshot.getValue(Shelter.class);
            }
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Shelter shelter = dataSnapshot.getValue(Shelter.class);
                Log.d("ADD", shelter.toString());
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        shelters = new ArrayList<>();
        loadShelters();
    }

    /**
     * populate the model with currently existing shelter data from database
     */
    private void loadShelters() {
        // TODO: pull shelters from db, populate shelter list
        // mDatabase.child()
        // shelters.add(new Shelter());
    }

    /**
     * get the shelters
     * @return a list of the shelters in the app
     */
    public List<Shelter> getShelters() {
        return shelters;
    }

    /**
     * add a shelter to the app.  checks if the course is already entered
     *
     * @param shelter  the shelter to be added
     * @return true if added, false if a duplicate
     */
    public boolean addShelter(Shelter shelter) {
        // TODO: check whether shelter exists (query db real time)
        // TODO: add shelter if not exists
        loadShelters(); // update shelter list with up to date data
        return true;
    }

    public boolean updateShelter(Shelter shelter) {

        // TODO: update an existing shelter with new data (db concurrent)
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

    /**
     * make a reservation on behalf of a user for the current shelter
     *
     * @param user the user to make the request on behalf of
     * @return true if reservation request successful, false otherwise
     */
//    public boolean addUser(User user) {
//        return user != null && currentShelter.reserve(user);
//    }

}
