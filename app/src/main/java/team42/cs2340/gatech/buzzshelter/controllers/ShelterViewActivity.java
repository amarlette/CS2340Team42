package team42.cs2340.gatech.buzzshelter.controllers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import team42.cs2340.gatech.buzzshelter.R;
import team42.cs2340.gatech.buzzshelter.model.Model;
import team42.cs2340.gatech.buzzshelter.model.Shelter;

/**
 * Created by ckadi on 2/26/2018.
 */

public class ShelterViewActivity extends AppCompatActivity {


    DatabaseReference databaseReference;

    ProgressDialog progressDialog;

    List<Shelter> list = new ArrayList<>();

    RecyclerView recyclerView;

    Query query;

    FirebaseRecyclerAdapter<Shelter,SheltersHolder> fadapter;

    @BindView(R.id.search_shelter_button) Button _searchShelterButton;
    @BindView(R.id.filter_shelters_button) Button _filterSheltersButton;
    @BindView(R.id.filter_male) CheckBox _filterMale;
    @BindView(R.id.filter_female) CheckBox _filterFemale;
    @BindView(R.id.filter_children_checkbox) CheckBox _filterChildrenCheckBox;
    @BindView(R.id.filter_newborns_checkbox) CheckBox _filterNewBornsCheckbox;
    @BindView(R.id.filter_young_adults_checkbox) CheckBox _filterYoungAdultsCheckbox;
    @BindView(R.id.filter_anyone) CheckBox _filterAnyone;
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

        query = databaseReference.child("shelters").orderByChild("name");


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
        progressDialog.cancel();


    }

    @Override protected void onStart() {
        super.onStart();
        fadapter.startListening();
    }

    public class SheltersHolder extends RecyclerView.ViewHolder {
        View mView;

        public SheltersHolder(View itemView) {
            super(itemView);

            mView = itemView;

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ShelterDetailViewActivity.class);

                    Model.getInstance().setCurrentShelter(fadapter.getItem(getAdapterPosition()));

                    context.startActivity(intent);
                }
            });


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
        Log.d("input",_searchView.getQuery().toString());
        query = databaseReference.child("shelters").orderByChild("name").startAt(_searchView.getQuery().toString()).endAt(_searchView.getQuery().toString()+"\uf8ff");
        fadapter.cleanup();


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

        recyclerView.setAdapter(fadapter);
    }
    public void filterShelters(){
        Log.d("input", "****FILTER SHELTERS**");
        if (_filterMale.isChecked()) {
            query = databaseReference.child("shelters").orderByChild("allowsMen").equalTo(true);
        }
        else if (_filterFemale.isChecked()) {
            query = databaseReference.child("shelters").orderByChild("allowsWomen").equalTo(true);
        }
        else if (_filterNewBornsCheckbox.isChecked()) {
            query = databaseReference.child("shelters").orderByChild("allowsNewborns").equalTo(true);
        }
        else if (_filterYoungAdultsCheckbox.isChecked()) {
            query = databaseReference.child("shelters").orderByChild("allowsYoungAdults").equalTo(true);
        }
        else if (_filterChildrenCheckBox.isChecked()) {
            query = databaseReference.child("shelters").orderByChild("allowsChildren").equalTo(true);
        }
        else if (_filterAnyone.isChecked()) {
            query = databaseReference.child("shelters").orderByChild("allowsAnyone").equalTo(true);
        }

        fadapter.cleanup();


        fadapter = new FirebaseRecyclerAdapter<Shelter, SheltersHolder>(
                Shelter.class,
                R.layout.recyclerview_items,
                SheltersHolder.class,
                query
        ) {
            public SheltersHolder onCreateViewHolder(ViewGroup parent, int viewType) {

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
        recyclerView.setAdapter(fadapter);

    }




}