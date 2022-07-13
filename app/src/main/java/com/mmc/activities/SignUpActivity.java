package com.mmc.activities;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.mmc.R;
import com.mmc.utils.ValidationUtils;

public class SignUpActivity extends AppCompatActivity {
    EditText etEmail, etPassword, etRetypePassword;
    Button signUpButton;
    TextView tvSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initComponents();
        initListeners();
    }

    private void initComponents() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etRetypePassword = findViewById(R.id.etRetypePassword);
        signUpButton = findViewById(R.id.signUpButton);
        tvSignIn = findViewById(R.id.tvSignIn);
    }

    private void initListeners() {
        signUpButton.setOnClickListener(v -> {
            if (!validateInputs()) {
                return;
            }
            doSignUp();
        });
        tvSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private boolean validateInputs() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();
        String retypePassword = etRetypePassword.getText().toString();

        if (email.isEmpty()) {
            etEmail.setError("Email is required");
        } else if (!ValidationUtils.isValidEmail(email)) {
            etEmail.setError("Invalid email");
        }
        if (password.isEmpty()) {
            etPassword.setError("Password is required");
        } else if (!retypePassword.equals(password)) {
            etRetypePassword.setError("Password does not match");
        }
        return !email.isEmpty()
                && ValidationUtils.isValidEmail(email)
                && !password.isEmpty()
                && retypePassword.equals(password);
    }

    private void doSignUp() {

    }
}