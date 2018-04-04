package team42.cs2340.gatech.buzzshelter.model;

import android.location.Location;
import java.util.HashMap;
import java.util.Map;

/**
 * Information holder for a homeless shelter instance
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

    // TODO: add fields and getters/setters for allowances (women, newborns, etc.) in order to remove ClassMapper Warning
    private boolean allowsMen;
    private boolean allowsChildren;
    private boolean allowsWomen;
    private boolean allowsNewborns;
    private boolean allowsYoungAdults;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
        getLocation().setLatitude(Double.parseDouble(latitude));
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
        getLocation().setLongitude(Double.parseDouble(longitude));
    }

    public Location getLocation() {
        if (location == null) {
            this.location = new Location("");
        }
        return location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(String occupancy) {
        this.occupancy = occupancy;
    }


    public void setAllowsChildren(boolean allowsChildren) {
        this.allowsChildren = allowsChildren;
    }

    public void setAllowsMen(boolean allowsMen) {
        this.allowsMen = allowsMen;
    }

    public void setAllowsNewborns(boolean allowsNewborns) {
        this.allowsNewborns = allowsNewborns;
    }

    public void setAllowsWomen(boolean allowsWomen) {
        this.allowsWomen = allowsWomen;
    }

    public void setAllowsYoungAdults(boolean allowsYoungAdults) {
        this.allowsYoungAdults = allowsYoungAdults;
    }

    public boolean getAllowsMen() {
        return allowsMen;
    }

    public boolean getAllowsChildren() {
        return allowsChildren;
    }

    public boolean getAllowsWomen() {
        return allowsWomen;
    }

    public boolean getAllowsNewborns() {
        return allowsNewborns;
    }

    public boolean getAllowsYoungAdults() {
        return allowsYoungAdults;
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

    public Shelter() {

    }

    public String toString() {
        return this.name + " @ " + this.address;
    }
    // TODO: functionality to add user to a shelter if space permits (reservation)

    public int getVacancies() {
        return Integer.parseInt(capacity) - Integer.parseInt(occupancy);
    }

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
