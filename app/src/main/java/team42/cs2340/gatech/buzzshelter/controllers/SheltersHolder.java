package team42.cs2340.gatech.buzzshelter.controllers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import team42.cs2340.gatech.buzzshelter.R;
import team42.cs2340.gatech.buzzshelter.model.Model;
import team42.cs2340.gatech.buzzshelter.model.Shelter;

/**
 * Created by Tiffany on 3/13/18.
 */

public class SheltersHolder extends RecyclerView.ViewHolder {
    View mView;

    FirebaseRecyclerAdapter<Shelter, SheltersHolder> mFadapter;
    public SheltersHolder(View itemView, final FirebaseRecyclerAdapter<Shelter, SheltersHolder> fadapter) {
        super(itemView);

        mView = itemView;
        mFadapter =fadapter;

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