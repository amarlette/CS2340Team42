package team42.cs2340.gatech.buzzshelter;

import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import team42.cs2340.gatech.buzzshelter.controllers.MainActivity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Unit tests for the LoginActivity
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule
            = new ActivityTestRule<>(MainActivity.class);

    /**
     * Test login with incorrect password
     */
    @Test
    public void testIncorrectPassword() {
        // fail login : password incorrect
        onView(withId(R.id.input_email)).perform(typeText("user@basic.com"), closeSoftKeyboard());
        onView(withId(R.id.input_password)).perform(typeText("false_false"), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());
        SystemClock.sleep(5000); // wait for firebase authentication
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()));
    }

    /**
     * Test login with email which does not exist
     */
    @Test
    public void testEmailNotExists() {
        onView(withId(R.id.input_email)).perform(clearText());
        onView(withId(R.id.input_email))
                .perform(typeText("dne@dne.dne"), closeSoftKeyboard());
        onView(withId(R.id.input_password)).perform(clearText());
        onView(withId(R.id.input_password))
                .perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());
        SystemClock.sleep(2000);
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()));
    }

    /**
     * Test login with invalid email (incorrect format)
     */
    @Test
    public void testInvalidEmail() {
        onView(withId(R.id.input_email)).perform(clearText());
        onView(withId(R.id.input_email)).perform(typeText("dne"), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());
        onView(withId(R.id.input_email))
                .check(matches(hasErrorText("enter a valid email address")));
    }

    /**
     * Test login with password too short
     */
    @Test
    public void testShortPassword() {
        onView(withId(R.id.input_email)).perform(clearText());
        onView(withId(R.id.input_email))
                .perform(typeText("user@basic.com"), closeSoftKeyboard());
        onView(withId(R.id.input_password)).perform(clearText());
        onView(withId(R.id.input_password))
                .perform(typeText("pw"), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());
        onView(withId(R.id.input_password))
                .check(matches(hasErrorText("must be at least 6 characters")));
    }

    /**
     * Test login successful
     */
    @Test
    public void testLoginSuccess() {
        onView(withId(R.id.input_email)).perform(clearText());
        onView(withId(R.id.input_email))
                .perform(typeText("user@basic.com"), closeSoftKeyboard());
        onView(withId(R.id.input_password)).perform(clearText());
        onView(withId(R.id.input_password))
                .perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());

        SystemClock.sleep(5000); // wait for firebase authentication
        onView(withId(R.id.btn_login)).check(doesNotExist());
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        onView(withText("Sign Out"))
                .perform(click());
    }

}