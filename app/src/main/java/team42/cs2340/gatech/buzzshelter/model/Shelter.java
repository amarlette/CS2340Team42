package team42.cs2340.gatech.buzzshelter.model;

import android.location.Location;
import java.util.HashMap;
import java.util.Map;

/**
 * Information holder for a homeless shelter instance;
 * Shelter objects always loaded from and created by database;
 * Therefore, there is no full constructor implemented
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

    /** occupancy level for this shelter */
    private String occupancy;

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

    private boolean allowsMen;
    private boolean allowsChildren;
    private boolean allowsWomen;
    private boolean allowsNewborns;
    private boolean allowsYoungAdults;

    /**
     * @return the shelter's name
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
        getLocation(); // create location if not exists
        location.setLatitude(Double.parseDouble(latitude));
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
        getLocation(); // create location if not exists
        location.setLongitude(Double.parseDouble(longitude));
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
     * @return the occupancy
     */
    public String getOccupancy() {
        return occupancy;
    }

    /**
     * Set the occupancy
     * @param occupancy to set
     */
    public void setOccupancy(String occupancy) {
        this.occupancy = occupancy;
    }

    /**
     * Set if the shelter allows children
     * @param allowsChildren does the shelter allow children
     */
    public void setAllowsChildren(boolean allowsChildren) {
        this.allowsChildren = allowsChildren;
    }

    /**
     * Set if the shelter allows me
     * @param allowsMen does the shelter allow men
     */
    public void setAllowsMen(boolean allowsMen) {
        this.allowsMen = allowsMen;
    }

    /**
     * Set if the shelter allows newborns
     * @param allowsNewborns does the shelter allow newborns
     */
    public void setAllowsNewborns(boolean allowsNewborns) {
        this.allowsNewborns = allowsNewborns;
    }

    /**
     * Set if the shelter allows women
     * @param allowsWomen does the shelter allow women
     */
    public void setAllowsWomen(boolean allowsWomen) {
        this.allowsWomen = allowsWomen;
    }

    /**
     * Set if the shelter allows young adults
     * @param allowsYoungAdults does the shelter allow young adults
     */
    public void setAllowsYoungAdults(boolean allowsYoungAdults) {
        this.allowsYoungAdults = allowsYoungAdults;
    }

    /**
     * @return a boolean representing if the shelter allows men
     */
    public boolean getAllowsMen() {
        return allowsMen;
    }

    /**
     * @return a boolean representing if the shelter allows children
     */
    public boolean getAllowsChildren() {
        return allowsChildren;
    }

    /**
     * @return a boolean representing if the shelter allows women
     */
    public boolean getAllowsWomen() {
        return allowsWomen;
    }

    /**
     * @return a boolean representing if the shelter allows new borns
     */
    public boolean getAllowsNewborns() {
        return allowsNewborns;
    }

    /**
     * @return a boolean representing if the shelter allows young adults
     */
    public boolean getAllowsYoungAdults() {
        return allowsYoungAdults;
    }

    /**
     * Empty constructor to initialize a Shelter
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

    /**
     * @return the number of vacancies
     */
    public int getVacancies() {
        return Integer.parseInt(capacity) - Integer.parseInt(occupancy);
    }

    /**
     * @return a map of information about the shelter
     */
    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("address", address);
        result.put("occupancy", occupancy);
        result.put("capacity", capacity);
        result.put("latitude", latitude);
        result.put("longitude", longitude);
        result.put("phone", phone);
        result.put("restrictions", restrictions);
        result.put("notes", notes);
        result.put("allowsChildren", allowsChildren);
        result.put("allowsMen", allowsMen);
        result.put("allowsWomen", allowsWomen);
        result.put("allowsYoungAdults", allowsYoungAdults);
        result.put("allowsNewborns", allowsNewborns);

        return result;
    }

}
