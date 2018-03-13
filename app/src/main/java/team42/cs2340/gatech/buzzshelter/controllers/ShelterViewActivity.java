package team42.cs2340.gatech.buzzshelter.controllers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    FirebaseRecyclerAdapter<Shelter,SheltersHolder> fadapter;

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
        ButterKnife.bind(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(ShelterViewActivity.this));

        progressDialog = new ProgressDialog(ShelterViewActivity.this);

        progressDialog.setMessage("Loading Data from Firebase Database");

        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        query = databaseReference.child("shelters").orderByValue();


        _searchShelterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchShelters();
            }
        });



        /*
        _filterSheltersButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                filterShelters();
            }
        });
        */




        fadapter = new FirebaseRecyclerAdapter<Shelter, SheltersHolder>(
                Shelter.class,
                R.layout.recyclerview_items,
                SheltersHolder.class,
                query
                ) {
            public SheltersHolder onCreateViewHolder(ViewGroup parent,int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recyclerview_items, parent, false);

                Log.d("******firebaseRecycler*", SheltersHolder.class.getName());
                return new SheltersHolder(view);
            }
            @Override
            protected void populateViewHolder(SheltersHolder viewHolder, Shelter model, int position) {
                viewHolder.setDetails(getApplicationContext(), model.getName(), model.getPhone());


            }

            @Override
            public void onCancelled(DatabaseError error) {
                super.onCancelled(error);
                progressDialog.cancel();
            }
        };

        fadapter.startListening();

        recyclerView.setAdapter(fadapter);

        /*
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Shelter shelterDetails = dataSnapshot.getValue(Shelter.class);
                    shelterDetails.setKey(dataSnapshot.getKey());
                    list.add(shelterDetails);
                }

                //adapter = new RecyclerViewAdapter(ShelterViewActivity.this, list);

                recyclerView.setAdapter(fadapter);

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();

            }


        });
        */

    }

    @Override protected void onStart() {
        super.onStart();
        fadapter.startListening();
    }

    public static class SheltersHolder extends RecyclerView.ViewHolder {
        View mView;

        public SheltersHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }


        public void setDetails(Context ctx, String shelterName, String shelterPhone){
            TextView shelter_name = mView.findViewById(R.id.ShowShelterNameTextView);
            TextView shelter_number = mView.findViewById(R.id.ShowShelterNumberTextView);

            shelter_name.setText(shelterName);
            shelter_number.setText(shelterPhone);
        }


        }
    public void searchShelters() {
        Log.d("******","****SEARCH SHELTERS****");
        query = databaseReference.orderByChild("name").startAt(_searchView.getQuery().toString()).endAt(_searchView.getQuery().toString()+"\uf8ff");

    }
    public void filterShelters(){
        //TODO: handle null cases

    }



}