package team42.cs2340.gatech.buzzshelter.model;

import android.location.Location;
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
        // TODO: move login logic here
        // TODO: read from database only if logged in
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
                shelter.setKey(dataSnapshot.getKey());
                shelters.add(shelter);
                Log.d("SHELTER", shelter.toString());
                Log.d("KEY", shelter.getKey());
                Log.d("LOC", shelter.getLocation().toString());
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        shelters = new ArrayList<>();
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
        return shelters;
    }

    /**
     * add a shelter to the app.  checks if duplicate
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

        // TODO: update an existing shelter using its db key, admin only
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
