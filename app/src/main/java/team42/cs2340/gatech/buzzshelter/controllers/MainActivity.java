package team42.cs2340.gatech.buzzshelter.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import team42.cs2340.gatech.buzzshelter.R;
import team42.cs2340.gatech.buzzshelter.model.BasicUser;
import team42.cs2340.gatech.buzzshelter.model.Model;
import team42.cs2340.gatech.buzzshelter.model.User;

/**
 * Landing Screen on Login Success
 */
public class MainActivity extends AppCompatActivity {
    private TextView mStatusTextView;
    private TextView mDetailTextView;

    private Model model;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = Model.getInstance();
        mStatusTextView = findViewById(R.id.status);
        mDetailTextView = findViewById(R.id.detail);
        currentUser = model.getCurrentUser();
        if (model.isSignedOut()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        currentUser = model.getCurrentUser();
        if (!model.isSignedOut()) {
            String userType = currentUser.getClass().toString();

            mStatusTextView.setText(currentUser.getEmail());
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, currentUser.getUid()));
            mDetailTextView.append("\n");
            mDetailTextView.append(getString(R.string.welcome_user, currentUser.getName()));

            mDetailTextView.append("\nYou are a: ");
            mDetailTextView.append(userType);

            mDetailTextView.append("\n You currently have ");
            if (currentUser.getClass().equals(BasicUser.class)) {
                BasicUser user = (BasicUser) currentUser;
                mDetailTextView.append(Integer.toString(user.getNumReservations()));
                mDetailTextView.append(" reservations");
                if (user.getNumReservations() > 0) {
                    mDetailTextView.append(" at ");
                    mDetailTextView.append(model.getShelterDictionary().
                            get(user.getCurrentShelterId()).getName());
                }
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.view_map) {
            startActivity(new Intent(this, MapActivity.class));
        }

        if (id == R.id.sign_out) {
            signOut();
            return true;
        }

        if (id == R.id.view_shelters) {
            startActivity(new Intent(this, ShelterListActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        model.signoutUser();
//        Intent intent = new Intent(this, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
//                Intent.FLAG_ACTIVITY_CLEAR_TASK |
//                Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//        finish();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}