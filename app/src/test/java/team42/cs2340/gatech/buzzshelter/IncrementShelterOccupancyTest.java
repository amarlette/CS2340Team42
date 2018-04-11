package team42.cs2340.gatech.buzzshelter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import team42.cs2340.gatech.buzzshelter.model.Model;
import team42.cs2340.gatech.buzzshelter.model.Shelter;
import team42.cs2340.gatech.buzzshelter.model.BasicUser;

/**
 * JUnit Test for incrementing the shelter occupancy
 */

public class IncrementShelterOccupancyTest {

    private Shelter tempShelter;
    private BasicUser tempUser;
    @Before
    public void initialize() {
        tempShelter = new Shelter();
        tempUser = new BasicUser(null, null,null);
    }

    @Test (expected = IllegalStateException.class)
    public void testIncrementShelterOccupancyCapacity() {
        tempShelter.setOccupancy("2");
        tempShelter.setCapacity("2");
        Model.setCurrentShelter(tempShelter);
        Model.incrementShelterOccupancy(1);
    }

    @Test
    public void testIncrementShelterOccupancyNoReservations() {
        tempShelter.setOccupancy("4");
        tempShelter.setCapacity("8");
        tempUser.setNumReservations(0);
        Model.currentShelter = new Shelter();
        Model.setCurrentShelter(tempShelter);
        Model.setCurrentUser(tempUser);
        Model.incrementShelterOccupancy(1);
        Assert.assertEquals(Model.currentShelter.getOccupancy(), tempShelter.getOccupancy());
        Assert.assertEquals("5", tempShelter.getOccupancy());
    }

    @Test
    public void testIncrementShelterOccupancyReservations() {
        tempShelter.setOccupancy("4");
        tempShelter.setCapacity("8");
        tempUser.setNumReservations(2);
        Model.currentShelter = new Shelter();
        Model.setCurrentShelter(tempShelter);
        Model.setCurrentUser(tempUser);
        Model.incrementShelterOccupancy(1);
        Assert.assertEquals(Model.currentShelter.getOccupancy(), tempShelter.getOccupancy());
        Assert.assertEquals("7", tempShelter.getOccupancy());
    }

}