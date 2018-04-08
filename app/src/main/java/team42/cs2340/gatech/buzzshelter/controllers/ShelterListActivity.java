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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import team42.cs2340.gatech.buzzshelter.R;
import team42.cs2340.gatech.buzzshelter.model.Model;
import team42.cs2340.gatech.buzzshelter.model.Shelter;

public class ShelterListActivity extends AppCompatActivity {
    private Model model;

    private DatabaseReference databaseReference;

    private ProgressDialog progressDialog;

    private List<Shelter> filtration = new ArrayList<>();

    private RecyclerView recyclerView;

    private Query query;

    private FirebaseRecyclerAdapter<Shelter,SheltersHolder> fadapter;

    @BindView(R.id.search_shelter_button) Button _searchShelterButton;
    @BindView(R.id.filter_shelters_button) Button _filterSheltersButton;
    @BindView(R.id.display_filtered_map) Button _filterSheltersToMapButton;
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
        setContentView(R.layout.list_shelter);
        ButterKnife.bind(this);
        model = Model.getInstance();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ShelterListActivity.this));
        progressDialog = new ProgressDialog(ShelterListActivity.this);
        progressDialog.setMessage("Loading Data from Firebase Database");
        progressDialog.show();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        query = databaseReference.child("shelters").orderByChild("name");

        //Search shelters on button press
        _searchShelterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchShelters();
            }
        });


        //Filter shelters on button press
        _filterSheltersButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                filterShelters();
            }
        });

        _filterSheltersToMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtration = new ArrayList<>();
                filterShelters();
                model.setFilteredShelters(filtration);
                startActivity(new Intent(ShelterListActivity.this, MapActivity.class));
            }
        });

        fadapter = createAdapter();
        fadapter.startListening();

        recyclerView.setAdapter(fadapter);
        progressDialog.cancel();

    }

    @Override protected void onStart() {
        super.onStart();
        fadapter.startListening();
    }


    /**
     * Creates a query to search for shelters matching the inputted text from the search view
     */
    private void searchShelters() {
        Log.d("******","****SEARCH SHELTERS****");
        Log.d("input",_searchView.getQuery().toString());

        query = databaseReference.child("shelters").orderByChild("name").
                startAt(_searchView.getQuery().toString()).
                endAt(_searchView.getQuery().toString()+"\uf8ff");
        fadapter.cleanup();

        //creates new adapter w/ updated adapter
        recyclerView.setAdapter(createAdapter());
    }

    /**
     * Creates a query to search for shelters based on checkboxes
     */
    private void filterShelters(){
        Log.d("input", "****FILTER SHELTERS**");
        if (_filterMale.isChecked()) {
            query = databaseReference.child("shelters").
                    orderByChild("allowsMen").equalTo(true);
        }
        else if (_filterFemale.isChecked()) {
            query = databaseReference.child("shelters").
                    orderByChild("allowsWomen").equalTo(true);
        }
        else if (_filterNewBornsCheckbox.isChecked()) {
            query = databaseReference.child("shelters").
                    orderByChild("allowsNewborns").equalTo(true);
        }
        else if (_filterYoungAdultsCheckbox.isChecked()) {
            query = databaseReference.child("shelters").
                    orderByChild("allowsYoungAdults").equalTo(true);
        }
        else if (_filterChildrenCheckBox.isChecked()) {
            query = databaseReference.child("shelters").
                    orderByChild("allowsChildren").equalTo(true);
        }
        else if (_filterAnyone.isChecked()) {
            query = databaseReference.child("shelters").
                    orderByChild("allowsAnyone").equalTo(true);
        } else {
            query = databaseReference.child("shelters").
                    orderByChild("name");
            filtration = model.getShelters(); // no filtering
        }

        fadapter.cleanup();

        //creates new adapter w/ updated query
        recyclerView.setAdapter(createAdapter());
    }


    /**
     * Creates a new recycler adapter for new search queries
     * @return a FirebaseUI Recycler Adapter with the updated query
     */
    private FirebaseRecyclerAdapter<Shelter, SheltersHolder> createAdapter() {
        fadapter = new FirebaseRecyclerAdapter<Shelter, SheltersHolder>(
                Shelter.class,
                R.layout.item_shelter,
                SheltersHolder.class,
                query
        ) {
            @Override
            public SheltersHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_shelter, parent, false);


                Log.d("******firebaseRecycler*", SheltersHolder.class.getName());
                return new SheltersHolder(view, fadapter);
            }

            @Override
            protected void populateViewHolder(SheltersHolder viewHolder,
                                              Shelter shelter, int position) {
                viewHolder.setDetails(shelter.getName(), shelter.getPhone());
                if (filtration.isEmpty()) {
                    for (int x = 0; x < fadapter.getItemCount(); x++) {
                        filtration.add(fadapter.getItem(x));
                        fadapter.getItem(x).setKey(getRef(x).getKey());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                super.onCancelled(error);
                progressDialog.cancel();
            }

        };

        return fadapter;
    }

    class SheltersHolder extends RecyclerView.ViewHolder {
        final View mView;

        SheltersHolder(View itemView,
                       final FirebaseRecyclerAdapter<Shelter, SheltersHolder> fadapter) {
            super(itemView);

            mView = itemView;

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ShelterViewActivity.class);
                    if (fadapter.getItem(getAdapterPosition()) != null) {
                        Shelter selected = fadapter.getItem(getAdapterPosition());
                        model.setCurrentShelter(selected);

                        context.startActivity(intent);
                    }
                }
            });


        }


        public void setDetails(CharSequence shelterName, CharSequence shelterPhone){
            TextView shelter_name = mView.findViewById(R.id.ShowShelterNameTextView);
            TextView shelter_number = mView.findViewById(R.id.ShowShelterNumberTextView);

            shelter_name.setText(shelterName);
            shelter_number.setText(shelterPhone);
        }


    }


}