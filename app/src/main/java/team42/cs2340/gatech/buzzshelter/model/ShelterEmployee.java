package team42.cs2340.gatech.buzzshelter.model;

public class ShelterEmployee extends User {
    private Shelter shelter;

    public ShelterEmployee(String uid, String name, String email) {
        super(uid, name, email);

    }
    public ShelterEmployee(String uid, String name, String email, Shelter shelter) {
        super(uid, name, email);
        this.shelter = shelter;

    }


    public void changeShelterOccupancy() {}
    public void changeShelterCapacity(){}
}
