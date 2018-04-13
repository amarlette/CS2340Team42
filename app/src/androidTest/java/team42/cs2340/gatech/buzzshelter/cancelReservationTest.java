package team42.cs2340.gatech.buzzshelter;


import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Assert;

import team42.cs2340.gatech.buzzshelter.controllers.MainActivity;
import team42.cs2340.gatech.buzzshelter.model.BasicUser;
import team42.cs2340.gatech.buzzshelter.model.Model;
import team42.cs2340.gatech.buzzshelter.model.Shelter;
import team42.cs2340.gatech.buzzshelter.model.User;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * JUnit Test for canceling reservation
 */

public class cancelReservationTest {

    private User user;
    private User user2;
    private Shelter currentShelter;
    private Shelter otherShelter;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void initialize() {
        user = new BasicUser("id", "name", "user@basic.com");
        user2 = user;
        user2 = null;
        onView(withId(R.id.input_email)).perform(clearText());
        onView(withId(R.id.input_email))
                .perform(typeText("user@basic.com"), closeSoftKeyboard());
        onView(withId(R.id.input_password)).perform(clearText());
        onView(withId(R.id.input_password)).perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());

        SystemClock.sleep(5000); // wait for firebase authentication
        onView(withId(R.id.btn_login)).check(doesNotExist());
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        currentShelter = Model.getInstance().getShelters().get(0);
        otherShelter = Model.getInstance().getShelters().get(1);

    }

    @Test
    public void testCancelReservationwUser() {
        Model.getInstance().setCurrentUser(user);
        Model.getInstance().setCurrentShelter(currentShelter);
        Model.getInstance().makeReservation(1);
        Assert.assertEquals(Model.getInstance().cancelReservation(), true);
        Model.getInstance().signoutUser();
    }

    @Test
    public void testCancelReservationwoReservation(){
        Model.getInstance().setCurrentUser(user);
        Model.getInstance().setCurrentShelter(currentShelter);
        Model.getInstance().makeReservation(0);
        Assert.assertEquals(Model.getInstance().cancelReservation(), false);
        Model.getInstance().signoutUser();
    }

}





