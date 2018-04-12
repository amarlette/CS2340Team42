package team42.cs2340.gatech.buzzshelter;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import team42.cs2340.gatech.buzzshelter.controllers.ShelterListActivity;
import team42.cs2340.gatech.buzzshelter.model.Shelter;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    /**
     * Test to see if project is initialized correctly
     * @throws Exception if project is not initialized correctly
     */
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("team42.cs2340.gatech.buzzshelter", appContext.getPackageName());
    }

    @Test
    public void makeNewShelter() {
        Shelter one = new Shelter();
        one.setAddress("123 Burdel Street");
        one.setCapacity("200");
        one.setName("Stinger Shelter");
        one.setLatitude("33.77");
        one.setLongitude("84.39");
        one.setOccupancy("150");
        one.setPhone("8008008000");

        ArrayList<Shelter> shelterList = new ArrayList<Shelter>();
        shelterList.add(one);

        Assert.assertEquals("Wrong get shelter", one, shelterList.get(0));

    }

    public void testFilter(){

    }
}
