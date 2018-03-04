package team42.cs2340.gatech.buzzshelter.controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import team42.cs2340.gatech.buzzshelter.R;
import team42.cs2340.gatech.buzzshelter.model.Model;

/**
 * Created by ckadi on 3/4/2018.
 */

public class ShelterListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_shelter);

        // TODO: improve item_shelter.xml (how the individual list item looks)
        
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag("myFragmentTag");
        if (fragment == null) {
            fragment = new ShelterListFragment();
            fm.beginTransaction()
                    .add(android.R.id.content, fragment).commit();
        }
    }
}
