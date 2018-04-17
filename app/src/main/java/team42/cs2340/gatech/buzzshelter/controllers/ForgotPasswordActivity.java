package team42.cs2340.gatech.buzzshelter.controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import team42.cs2340.gatech.buzzshelter.R;
import team42.cs2340.gatech.buzzshelter.model.Model;

/**
 * Created by Tiffany on 4/17/18.
 */

public class ForgotPasswordActivity extends AppCompatActivity{
    private FirebaseAuth mAuth;
    private Model model;

    @BindView(R.id.reset_password) Button _resetPasswordButton;
    @BindView(R.id.email_password_reset) EditText _emailReset;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        model = Model.getInstance();
        mAuth = FirebaseAuth.getInstance();

        _resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = _emailReset.getText().toString();
                mAuth.sendPasswordResetEmail(email);
                Toast.makeText(ForgotPasswordActivity.this, "password reset email sent", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
