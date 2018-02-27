package team42.cs2340.gatech.buzzshelter.controllers;

/**
 * Created by ckadi on 2/26/2018.
 */

import android.content.Context;
import android.content.Intent;
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
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.shelterDetails = MainImageUploadInfoList.get(position);

        holder.ShelterNameTextView.setText(holder.shelterDetails.getName());

        holder.ShelterPhoneTextView.setText(holder.shelterDetails.getPhone());

        holder.ShelterBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ShelterDetailViewActivity.class);
                Model.getInstance().setCurrentShelter(holder.shelterDetails);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return MainImageUploadInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public View ShelterBlock;
        public TextView ShelterNameTextView;
        public TextView ShelterPhoneTextView;
        public Shelter shelterDetails;

        public ViewHolder(View itemView) {

            super(itemView);
            ShelterBlock = itemView;

            ShelterNameTextView = itemView.findViewById(R.id.ShowShelterNameTextView);

            ShelterPhoneTextView = itemView.findViewById(R.id.ShowShelterNumberTextView);
        }
    }
}