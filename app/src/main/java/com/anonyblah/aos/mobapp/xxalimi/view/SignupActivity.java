package com.anonyblah.aos.mobapp.xxalimi.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import com.anonyblah.aos.mobapp.xxalimi.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Kloc on 2016-05-18.
 */
public class SignupActivity extends AppCompatActivity{
    private static final String TAG = SignupActivity.class.getSimpleName();

    @Bind(R.id.input_name) EditText nameText;
    @Bind(R.id.input_email) EditText emailText;
    @Bind(R.id.input_password) EditText passwordText;
    @Bind(R.id.btn_signup) EditText signupButton;
    @Bind(R.id.link_login) EditText loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);
        ButterKnife.bind(this);

        signupButton.setOnClickListener(v -> signup());
        loginLink.setOnClickListener(v -> finish());
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if(!validate()) {
            onSignupFailed();
            return;
        }

        signupButton.setEnabled(false);

        // Network block
    }

    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();
        signupButton.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if(name.isEmpty() || name.length() < 2) {
            nameText.setError("At least 2 characters");
            valid = false;
        } else {
            nameText.setError(null);
        }

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if(password.isEmpty() || password.length() < 8 || password.length() > 16) {
            passwordText.setError("Between 8 and 16 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }
}
