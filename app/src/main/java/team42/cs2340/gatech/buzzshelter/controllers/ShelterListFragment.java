package team42.cs2340.gatech.buzzshelter.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import team42.cs2340.gatech.buzzshelter.R;
import team42.cs2340.gatech.buzzshelter.model.Model;
import team42.cs2340.gatech.buzzshelter.model.Shelter;

/**
 * Fragment representing list of shelters
 */
public class ShelterListFragment extends Fragment {
    /**
     * Shelter List Adapter
     */
    private ShelterListAdapter adapter;

    public ShelterListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_shelter, container, false);
        View recyclerView = rootView.findViewById(R.id.shelter_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    /**
     * Set up an adapter and hook it to the provided view
     *
     * @param recyclerView  the view that needs this adapter
     */
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        adapter = new ShelterListAdapter(Model.getInstance().getShelters());
        Log.d("Adapter", adapter.toString());
        recyclerView.setAdapter(adapter);
    }

    /**
     * This inner class is our custom adapter.  It takes our basic model information and
     * converts it to the correct layout for this view.
     */
    public class ShelterListAdapter
            extends RecyclerView.Adapter<ShelterListAdapter.ViewHolder> {

        /** Collection of the items to be shown in this list. */
        private final List<Shelter> shelters;

        /**
         * set the items to be used by the adapter
         * @param items the list of items to be displayed in the recycler view
         */
        public ShelterListAdapter(List<Shelter> items) {
            shelters = items;
        }

        @Override
        public ShelterListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_shelter, parent, false);
            return new ShelterListAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ShelterListAdapter.ViewHolder holder, int position) {
            holder.mShelter = shelters.get(position);
            Log.d("Adapter", "shelter: " + holder.mShelter);
            holder.mIdView.setText("" + shelters.get(position).getPhone());
            holder.mContentView.setText(shelters.get(position).toString());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Model.getInstance().setCurrentShelter(holder.mShelter);
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ShelterViewActivity.class);
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return shelters.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            final View mView;
            final TextView mIdView;
            final TextView mContentView;
            Shelter mShelter;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = view.findViewById(R.id.shelter_phone);
                Log.d("Holder", mIdView.toString());
                mContentView = view.findViewById(R.id.shelter_address);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
