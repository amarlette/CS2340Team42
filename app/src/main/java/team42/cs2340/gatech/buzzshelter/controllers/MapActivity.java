package team42.cs2340.gatech.buzzshelter.controllers;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ckadi on 2/28/2018.
 */
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;

import team42.cs2340.gatech.buzzshelter.R;
import team42.cs2340.gatech.buzzshelter.model.Model;
import team42.cs2340.gatech.buzzshelter.model.Shelter;

/**
 * This shows how to create a simple activity with a map and a marker on the map.
 */
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private Model model;
    private HashMap<Marker, Shelter> markers;
    private GoogleMap targetMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        model = Model.getInstance();
        markers = new HashMap<>();

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.targetMap = map;
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                Log.d("MAP", markers.get(marker).toString());
                targetMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 13));
                marker.showInfoWindow();
                return true;
            }
        });
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(final Marker marker) {
                Model.getInstance().setCurrentShelter(markers.get(marker));
                Intent intent = new Intent(MapActivity.this, ShelterViewActivity.class);
                startActivity(intent);
            }
        });
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Shelter shelter : model.getShelters()) {
            Location loc = shelter.getLocation();
            LatLng pos = new LatLng(loc.getLatitude(), loc.getLongitude());
            Marker marker = map.addMarker(new MarkerOptions().position(pos)
                    .title(shelter.getName())
                    .snippet(shelter.getPhone()));
            markers.put(marker, shelter);
            builder.include(pos);
        }
        LatLngBounds bounds = builder.build();
        int padding = 100; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        map.moveCamera(cu);
        map.animateCamera(cu);
    }
}
