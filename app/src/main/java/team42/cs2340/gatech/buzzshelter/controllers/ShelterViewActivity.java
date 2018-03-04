package team42.cs2340.gatech.buzzshelter.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import team42.cs2340.gatech.buzzshelter.R;
import team42.cs2340.gatech.buzzshelter.model.Model;

/**
 * Created by ckadi on 3/3/2018.
 */

public class ShelterViewActivity extends AppCompatActivity {
    private TextView mStatusTextView;
    private TextView mDetailTextView;
    private Model model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // TODO: replace the activity_main with a dedicated screen for shelter detailed view
        // TODO: create the detailed shelter view screen

        model = Model.getInstance();
        mStatusTextView = findViewById(R.id.status);
        mDetailTextView = findViewById(R.id.detail);
        if (model.getCurrentUser() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (model.getCurrentUser() != null) {
            mStatusTextView.setText(model.getCurrentUser().getEmail());
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, model.getCurrentUser().getUid()));
            mDetailTextView.append("\n");
            mDetailTextView.append(getString(R.string.welcome_user, model.getCurrentUser().getName()));

            mDetailTextView.append("THIS IS THE SPECIFIC SHELTER SCREEN");
            mDetailTextView.append(model.getCurrentShelter().toString());
        }
    }
}
