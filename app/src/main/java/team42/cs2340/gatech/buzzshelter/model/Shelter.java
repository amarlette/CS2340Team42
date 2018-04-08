package team42.cs2340.gatech.buzzshelter.model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ckadi on 2/25/2018.
 */

public class Shelter {
    /** this shelter's firebase key */
    private String key;

    /** this shelter's name */
    private String name;

    /** the shelter's address */
    private String address;

    /** the shelter's capacity */
    private String capacity;

    /** the shelter's latitude */
    private String latitude;

    /** the shelter's longitude */
    private String longitude;

    /** the shelter's location (coordinates) */
    private Location location;

    /** the shelter's phone number */
    private String phone;

    /** restrictions pertaining to this shelter */
    private String restrictions;

    /** notes pertaining to this shelter */
    private String notes;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return a string containing the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return a string of the capacity
     */
    public String getCapacity() {
        return capacity;
    }

    /**
     * Sets the capactiy
     * @param capacity the capacity to set
     */
    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    /**
     * @return a string of the latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Sets the latitude
     * @param latitude the latitude to set
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
        getLocation().setLatitude(Double.parseDouble(latitude));
    }

    /**
     * @return a string of the longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Sets the longitude
     * @param longitude the longitude to set
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
        getLocation().setLongitude(Double.parseDouble(longitude));
    }

    /**
     * @return a string of the location
     */
    public Location getLocation() {
        if (location == null) {
            this.location = new Location("");
        }
        return location;
    }

    /**
     * @return a string of the phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number
     * @param phone the number to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return a string of the restrictions
     */
    public String getRestrictions() {
        return restrictions;
    }

    /**
     * Sets the restrictions
     * @param restrictions the restrictions to set
     */
    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    /**
     * @return a string of notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the notes
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * @return a key
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Create a new shelter
     * @param name          the shelter's name
     * @param address       the shelter's address
     * @param capacity      the shelter's capacity
     * @param latitude      the shelter's latitude
     * @param longitude     the shelter's longitude
     * @param phone         the shelter's phone number
     * @param restrictions  restrictions pertaining to the shelter
     * @param notes         notes pertaining to the shelter
     */
    public Shelter(String name, String address, String capacity,
                   String latitude, String longitude, String phone,
                   String restrictions, String notes) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone = phone;
        this.restrictions = restrictions;
        this.notes = notes;
    }

    /**
     * Empty constructor to initialize shelter
     */
    public Shelter() {

    }

    /**
     * Returns Shelter as a string
     */
    public String toString() {
        return this.name + " @ " + this.address;
    }
    // TODO: functionality to add user to a shelter if space permits (reservation)
}
