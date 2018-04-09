package team42.cs2340.gatech.buzzshelter.controllers;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.BindView;
import team42.cs2340.gatech.buzzshelter.R;
import team42.cs2340.gatech.buzzshelter.model.AdminUser;
import team42.cs2340.gatech.buzzshelter.model.BasicUser;
import team42.cs2340.gatech.buzzshelter.model.Model;
import team42.cs2340.gatech.buzzshelter.model.ShelterEmployee;
import team42.cs2340.gatech.buzzshelter.model.User;
import team42.cs2340.gatech.buzzshelter.model.UserContainer;

/**
 * 'Create an Account' Screen
 */
public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    private FirebaseAuth mAuth;
    private Model model;

    @BindView(R.id.input_name) EditText _nameText;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.role_select) Spinner _userType;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        model = Model.getInstance();
        mAuth = FirebaseAuth.getInstance();

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.role_types, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _userType.setAdapter(adapter);
    }

    /**
     * Signs user up for a new account
     */
    private void signup() {
        Log.d(TAG, "Sign-up");

        if (!validate()) {
            onRegisterFail();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        final String name = _nameText.getText().toString();
        final String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();

        // Sign Up Logic

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful() && (mAuth.getCurrentUser() != null)) {
                            progressDialog.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            _passwordText.getText().clear();

                            UserProfileChangeRequest profileUpdates
                                    = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                onRegisterSuccess();
                                            }
                                        }
                                    });
                            DatabaseReference userRef = FirebaseDatabase.getInstance()
                                    .getReference().child("users");
                            Map<String, Object> userMap = new HashMap<>();

                            User currentUser;
                            String uid = mAuth.getCurrentUser().getUid();
                            if ("admin".equals(_userType.getSelectedItem())) {
                                currentUser = new AdminUser(uid, name, email);
                            } else if ("employee".equals(_userType.getSelectedItem())) {
                                currentUser = new ShelterEmployee(uid, name, email);
                            } else {
                                currentUser = new BasicUser(uid, name, email);
                            }

                            UserContainer userDetails = new UserContainer(currentUser);
                            userMap.put(user.getUid(), userDetails); // additional details
                            userRef.updateChildren(userMap); // add new user to database
                            model.setCurrentUser(currentUser);
                        } else {
                            progressDialog.dismiss();
                            // If sign up fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            if ((task.getException() != null)
                                    && task.getException().getClass()
                                    .equals(FirebaseAuthUserCollisionException.class)) {
                                Toast.makeText(RegisterActivity.this, R.string.user_exists,
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(RegisterActivity.this, R.string.user_create_fail,
                                        Toast.LENGTH_SHORT).show();
                            }
                            onRegisterFail();
                        }
                    }

                });
    }


    /**
     * Registration successful, end activity and proceed to MainActivity
     */
    private void onRegisterSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    /**
     * Allows user to attempt to sign-up again
     */
    private void onRegisterFail() {
        // Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    /**
     * Determines if credentials are valid
     * @return a boolean determining if the credentials are valid
     */
    private boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || (name.length() < 3)) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || (password.length() < 6)) {
            _passwordText.setError("Password must be at least 6 characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}