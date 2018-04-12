package team42.cs2340.gatech.buzzshelter;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import team42.cs2340.gatech.buzzshelter.model.BasicUser;
import team42.cs2340.gatech.buzzshelter.model.Model;
import team42.cs2340.gatech.buzzshelter.model.Shelter;
import team42.cs2340.gatech.buzzshelter.model.User;

/**
 * JUnit Test for decrementing the shelter occupancy
 */

public class IsSignedOutTest {

    private User user;
    private User user2;

    @Before
    public void initialize() {

        user = new BasicUser("id","name", "email@gmail.com");


        user2 = user;
        user2 = null;


    }

    @Test
    public void testNullCurrentUser() {
        Model.getInstance().setCurrentUser(user2);
        Assert.assertEquals(Model.getInstance().isSignedOut(), true);
    }


    @Test
    public void testHasCurrentUser() {
        Model.getInstance().setCurrentUser(user);
        Assert.assertEquals(Model.getInstance().isSignedOut(), false);
    }



}
