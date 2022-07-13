package com.mmc.activities;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.mmc.R;
import com.mmc.utils.ValidationUtils;

public class LoginActivity extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button signInButton;
    TextView tvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponents();
        initListeners();
    }
    private void initComponents() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        signInButton = findViewById(R.id.signInButton);
        tvSignUp = findViewById(R.id.tvSignUp);
    }
    private void initListeners() {
        signInButton.setOnClickListener(v -> {
            if(!validateInputs()) {
                return;
            }
            doLogin();
        });
        tvSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private boolean validateInputs() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();

        if(email.isEmpty()) {
            etEmail.setError("Email is required");
        } else if(!ValidationUtils.isValidEmail(email)){
            etEmail.setError("Invalid email");
        }
        if(password.isEmpty()) {
            etPassword.setError("Password is required");
        }
        return !email.isEmpty() && ValidationUtils.isValidEmail(email) && !password.isEmpty();
    }

    private void doLogin() {

    }
}