package team42.cs2340.gatech.buzzshelter.controllers;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import team42.cs2340.gatech.buzzshelter.R;
import team42.cs2340.gatech.buzzshelter.model.Shelter;

/**
 * Created by ckadi on 2/26/2018.
 */

public class ShelterViewActivity extends AppCompatActivity {


    DatabaseReference databaseReference;

    ProgressDialog progressDialog;

    List<Shelter> list = new ArrayList<>();

    RecyclerView recyclerView;

    RecyclerView.Adapter adapter;

    Query query;

    @BindView(R.id.search_shelter_button) Button _searchShelterButton;
    @BindView(R.id.filter_shelters_button) Button _filterSheltersButton;
    @BindView(R.id.filter_children_checkbox) CheckBox _filterChildrenCheckBox;
    @BindView(R.id.filter_newborns_checkbox) CheckBox _filterNewBornsCheckbox;
    @BindView(R.id.filter_young_adults_checkbox) CheckBox _filterYoungAdultsCheckbox;
    @BindView(R.id.search_view) SearchView _searchView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_view);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(ShelterViewActivity.this));

        progressDialog = new ProgressDialog(ShelterViewActivity.this);

        progressDialog.setMessage("Loading Data from Firebase Database");

        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference("shelters");
        _searchShelterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchShelters();
            }
        });

        _filterSheltersButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                filterShelters();
            }
        });


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Shelter shelterDetails = dataSnapshot.getValue(Shelter.class);
                    shelterDetails.setKey(dataSnapshot.getKey());
                    list.add(shelterDetails);
                }

                adapter = new RecyclerViewAdapter(ShelterViewActivity.this, list);

                recyclerView.setAdapter(adapter);

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();

            }


        });

    }


    public void searchShelters() {
        //TODO: handle null cases

        query = databaseReference.orderByKey().startAt((String)_searchView.getQuery(),(String)_searchView.getQuery()+"\\uf8ff");

    }
    public void filterShelters(){
        //TODO: handle null cases
        query = databaseReference.orderByKey().

    }



}