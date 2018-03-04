package team42.cs2340.gatech.buzzshelter.controllers;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;

import team42.cs2340.gatech.buzzshelter.R;
import team42.cs2340.gatech.buzzshelter.model.Model;
import team42.cs2340.gatech.buzzshelter.model.Shelter;

/**
 * Created by ckadi on 3/4/2018.
 */

public class ShelterListActivity extends AppCompatActivity {
    private Model model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        model = Model.getInstance();

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag("myFragmentTag");
        if (fragment == null) {
            FragmentTransaction ft = fm.beginTransaction();
            fragment = new ShelterListFragment();
//            ft.add(android.R.id.content, fragment,"myFragmentTag");
//            ft.commit();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();

        }
    }
}
