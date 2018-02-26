package team42.cs2340.gatech.buzzshelter.model;

import android.support.compat.BuildConfig;

import java.util.ArrayList;
import java.util.List;

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


    public static Model getInstance() {
        return instance;
    }

    /**
     * create a new model
     */
    private Model() {
        shelters = new ArrayList<>();

        //comment this out after full app developed -- for homework leave in
        loadShelters();

    }

    /**
     * populate the model with currently existing shelter data from database
     */
    private void loadShelters() {
        // TODO: pull shelters from db, populate shelter list
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

    // TODO: update an existing shelter with new data (db concurrent)
}
