package team42.cs2340.gatech.buzzshelter.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import team42.cs2340.gatech.buzzshelter.R;
import team42.cs2340.gatech.buzzshelter.model.BasicUser;
import team42.cs2340.gatech.buzzshelter.model.Model;

/**
 * Created by ckadi on 3/3/2018.
 */

public class ShelterViewActivity extends AppCompatActivity {
    private Model model;

    @BindView(R.id.numReservations) SeekBar _numReservations;
    @BindView(R.id.reservationLabel) TextView _reservationLabel;
    @BindView(R.id.make_reserve_button) Button _makeReservation;
    @BindView(R.id.cancel_reserve_button) Button _cancelReservation;
    @BindView(R.id.status) TextView mStatusTextView;
    @BindView(R.id.detail) TextView mDetailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter);
        ButterKnife.bind(this);
        // TODO: create the detailed shelter view screen

        model = Model.getInstance();
        _numReservations.setMax(model.getMaxReservations(model.getCurrentShelter()));
        _numReservations.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                _reservationLabel.setText("Number of Reservations (");
                _reservationLabel.append(Integer.toString(progress));
                _reservationLabel.append(")");
                if (progress == 0) {
                    _makeReservation.setEnabled(false);
                } else {
                    _makeReservation.setEnabled(!model.currentUserHasReservation());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        _makeReservation.setEnabled(!model.currentUserHasReservation()
                && model.getMaxReservations(model.getCurrentShelter()) != 0);
        _cancelReservation.setEnabled(model.currentUserHasReservation());

        _makeReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.makeReservation(_numReservations.getProgress());
                _makeReservation.setEnabled(false);
                _cancelReservation.setEnabled(true);

                // TODO: Simplify so that only one thing is updated
                mDetailTextView.setText(getString(R.string.welcome_user, model.getCurrentUser().getName()));
                mDetailTextView.append("\n\n");
                mDetailTextView.append(model.getCurrentShelter().toString());
                mDetailTextView.append("\nOccupancy: ");
                mDetailTextView.append(model.getCurrentShelter().getOccupancy());
                mDetailTextView.append("/");
                mDetailTextView.append(model.getCurrentShelter().getCapacity());
            }
        });

        _cancelReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.cancelReservation();
                _makeReservation.setEnabled(true);
                _cancelReservation.setEnabled(false);

                // TODO: Simplify so that only one thing is updated
                mDetailTextView.setText(getString(R.string.welcome_user, model.getCurrentUser().getName()));
                mDetailTextView.append("\n\n");
                mDetailTextView.append(model.getCurrentShelter().toString());
                mDetailTextView.append("\nOccupancy: ");
                mDetailTextView.append(model.getCurrentShelter().getOccupancy());
                mDetailTextView.append("/");
                mDetailTextView.append(model.getCurrentShelter().getCapacity());
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        if (model.getCurrentUser() != null) {
            mDetailTextView.setText(getString(R.string.welcome_user, model.getCurrentUser().getName()));
            mDetailTextView.append("\n\n");
            mDetailTextView.append(model.getCurrentShelter().toString());
            mDetailTextView.append("\nOccupancy: ");
            mDetailTextView.append(model.getCurrentShelter().getOccupancy());
            mDetailTextView.append("/");
            mDetailTextView.append(model.getCurrentShelter().getCapacity());
        }
    }
}
