package team42.cs2340.gatech.buzzshelter;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import team42.cs2340.gatech.buzzshelter.model.Model;
import team42.cs2340.gatech.buzzshelter.model.Shelter;

/**
 * JUnit Test for decrementing the shelter occupancy
 */

public class DecrementShelterOccupancyTest {

    private Shelter temp;
    @Before
    public void initialize() {
        temp = new Shelter();
    }

    @Test(expected = IllegalStateException.class)
    public void testDecrementShelterOccupancyNegative() {
        temp.setOccupancy("0");
        Model.decrementShelterOccupancy(temp, 1);
    }

    @Test
    public void testDecrementShelterOccupancyCurrent() {
        temp.setOccupancy("5");
        temp.setKey("test123");
        Model.currentShelter = temp;
        Model.decrementShelterOccupancy(temp, 2);
        Assert.assertEquals(Model.currentShelter.getOccupancy(), temp.getOccupancy());
        Assert.assertEquals("3", temp.getOccupancy());
    }

    @Test
    public void testDecrementShelterOccupancyNotCurrent() {
        temp.setOccupancy("5");
        temp.setKey("test123");
        Model.currentShelter = new Shelter();
        Model.currentShelter.setKey("notSame");
        Model.decrementShelterOccupancy(temp, 2);
        Assert.assertNotEquals(Model.currentShelter.getOccupancy(), temp.getOccupancy());
        Assert.assertEquals("3", temp.getOccupancy());
    }
}
