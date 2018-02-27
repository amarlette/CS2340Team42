package team42.cs2340.gatech.buzzshelter.controllers;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import team42.cs2340.gatech.buzzshelter.R;
import team42.cs2340.gatech.buzzshelter.model.Model;
import team42.cs2340.gatech.buzzshelter.model.Shelter;

/**
 * Created by ckadi on 2/27/2018.
 */

public class ShelterDetailViewActivity extends AppCompatActivity {
    DatabaseReference shelterRef;

    ProgressDialog progressDialog;

    List<Shelter> list = new ArrayList<>();

    RecyclerView recyclerView;

    RecyclerView.Adapter adapter ;

    HashMap<String, String> details;
    public Shelter shelter;
    public TextView name;
    public TextView coordinates;
    public TextView phone;
    public TextView notes;
    public TextView restrictions;
    public TextView address;
    public TextView capacity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Model model = Model.getInstance();
        setContentView(R.layout.activity_detail_shelter_view);
        details = new HashMap<>();
        shelter = model.getCurrentShelter();
        if (shelter != null) {
            name = findViewById(R.id.ShowShelterNameTextView);
            name.setText(shelter.getName());
            coordinates = findViewById(R.id.ShowShelterCoordinates);
            String coor = "Longitude: " + shelter.getLongitude();
            coor += "\n";
            coor += "Latitude: " + shelter.getLatitude();
            coordinates.setText(coor);

            phone = findViewById(R.id.ShowShelterNumberTextView);
            phone.setText(shelter.getPhone());
            notes = findViewById(R.id.ShowShelterNotesTextView);
            notes.setText(shelter.getNotes());
            restrictions = findViewById(R.id.ShowShelterRestrictionsTextView);
            restrictions.setText(shelter.getRestrictions());
            address = findViewById(R.id.ShowShelterAddressTextView);
            address.setText(shelter.getAddress());

            capacity = findViewById(R.id.ShowShelterCapacityTextView);
            capacity.setText(shelter.getCapacity());
        }

    }
}
