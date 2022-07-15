package com.mmc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.mmc.R;
import com.mmc.models.Account;
import com.mmc.models.AuthResponse;
import com.mmc.models.ResponseStatus;
import com.mmc.repositories.AccountRepository;
import com.mmc.services.AccountService;
import com.mmc.utils.ValidationUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    EditText etEmail, etFullName, etPassword, etRetypePassword;
    Button signUpButton;
    TextView tvSignIn;

    AccountService accountService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        accountService = AccountRepository.getAccountService();
        initComponents();
        initListeners();
    }

    private void initComponents() {
        etEmail = findViewById(R.id.etEmail);
        etFullName = findViewById(R.id.etFullName);
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
        String fullName = etFullName.getText().toString().trim();
        String password = etPassword.getText().toString();
        String retypePassword = etRetypePassword.getText().toString();

        if (email.isEmpty()) {
            etEmail.setError("Email is required");
        } else if (!ValidationUtils.isValidEmail(email)) {
            etEmail.setError("Invalid email address");
        }
        if (fullName.isEmpty()) {
            etFullName.setError("Full name is required");
        } else if (!ValidationUtils.isValidFullName(fullName)) {
            etFullName.setError("Full name must be less than " + ValidationUtils.MAX_LENGTH_FULL_NAME + " characters");
        }
        if (password.isEmpty()) {
            etPassword.setError("Password is required");
        } else if (!retypePassword.equals(password)) {
            etRetypePassword.setError("Password does not match");
        }
        return !email.isEmpty()
                && ValidationUtils.isValidEmail(email)
                && !fullName.isEmpty()
                && ValidationUtils.isValidFullName(fullName)
                && !password.isEmpty()
                && retypePassword.equals(password);
    }

    private void doSignUp() {
        String email = etEmail.getText().toString().trim();
        String fullName = etFullName.getText().toString().trim();
        String password = etPassword.getText().toString();

        Account signUpAccount = new Account(fullName, email, password);
        accountService
                .register(signUpAccount)
                .enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                        if (response.code() == 200) {
                            AuthResponse authResponse = response.body();
                            if (authResponse != null) {
                                ResponseStatus responseStatus = authResponse.getStatus();
                                if (responseStatus != null && responseStatus.isSuccess()) {
                                    Toast.makeText(SignUpActivity.this, "You account has been create successfully!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else if (responseStatus != null && responseStatus.getMessage() != null) {
                                    Toast.makeText(SignUpActivity.this, responseStatus.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {
                        Toast.makeText(SignUpActivity.this, "Something went wrong with our server. Please try again later.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}