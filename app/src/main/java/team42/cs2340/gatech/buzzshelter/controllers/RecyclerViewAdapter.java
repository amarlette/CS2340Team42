package team42.cs2340.gatech.buzzshelter.controllers;

/**
 * Created by ckadi on 2/26/2018.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import team42.cs2340.gatech.buzzshelter.R;
import team42.cs2340.gatech.buzzshelter.model.Shelter;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    List<Shelter> MainImageUploadInfoList;

    public RecyclerViewAdapter(Context context, List<Shelter> TempList) {

        this.MainImageUploadInfoList = TempList;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Shelter shelterDetails = MainImageUploadInfoList.get(position);

        holder.ShelterNameTextView.setText(shelterDetails.getName());

        holder.ShelterPhoneTextView.setText(shelterDetails.getPhone());

    }

    @Override
    public int getItemCount() {

        return MainImageUploadInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView ShelterNameTextView;
        public TextView ShelterPhoneTextView;

        public ViewHolder(View itemView) {

            super(itemView);

            ShelterNameTextView = (TextView) itemView.findViewById(R.id.ShowShelterNameTextView);

            ShelterPhoneTextView = (TextView) itemView.findViewById(R.id.ShowShelterNumberTextView);
        }
    }
}