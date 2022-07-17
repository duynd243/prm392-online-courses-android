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
import com.mmc.repositories.AccountRepository;
import com.mmc.repositories.CourseRepository;
import com.mmc.services.AccountService;
import com.mmc.services.CourseService;
import com.mmc.utils.ValidationUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button signInButton;
    TextView tvSignUp;
    AccountService accountService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        accountService = AccountRepository.getAccountService();
        initComponents();
        initListeners();


        CourseService courseService = CourseRepository.getCourseService();


//        courseService
//                .getAllCourses("", "", 1, 10)
//                .enqueue(
//                        new Callback<CourseResponse>() {
//                            @Override
//                            public void onResponse(Call<CourseResponse> call, Response<CourseResponse> response) {
//                                List<Course> courses = response.body().getData();
//                                for(Course course : courses) {
//                                    Log.d("CoursId", String.valueOf(course.getId()));
//                                    Log.d("CoursName", course.getName());
//                                    Log.d("Mentor", course.getMentor().getFullName());
//                                    Log.d("Subject", course.getSubject().getName());
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<CourseResponse> call, Throwable t) {
//                                Log.d("LoginActivity", "onFailure: " + t.getMessage());
//                            }
//                        }
//                );

//        courseService
//                .getCourseById(19)
//                .enqueue(
//                        new Callback<Course>() {
//                            @Override
//                            public void onResponse(Call<Course> call, Response<Course> response) {
//                                Log.d("Response Code", String.valueOf(response.code()));
//                                Log.d("CoursId", String.valueOf(course.getId()));
//                                Log.d("CoursName", course.getName());
//                                Log.d("Mentor", course.getMentor().getFullName());
//                                Log.d("Subject", course.getSubject().getName());
//                            }
//
//                            @Override
//                            public void onFailure(Call<Course> call, Throwable t) {
//
//                            }
//                        }
//                );
//        ;
    }

    private void initComponents() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        signInButton = findViewById(R.id.signInButton);
        tvSignUp = findViewById(R.id.tvSignUp);
    }

    private void initListeners() {
        signInButton.setOnClickListener(v -> {
            if (!validateInputs()) {
                signInButton.setEnabled(true);
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

        if (email.isEmpty()) {
            etEmail.setError("Email is required");
        } else if (!ValidationUtils.isValidEmail(email)) {
            etEmail.setError("Invalid email");
        }
        if (password.isEmpty()) {
            etPassword.setError("Password is required");
        }
        return !email.isEmpty() && ValidationUtils.isValidEmail(email) && !password.isEmpty();
    }

    private void doLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();
        Account loginAccount = new Account("", email, password);

        signInButton.setClickable(false);
        signInButton.setEnabled(false);
        accountService
                .login(loginAccount)
                .enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                        boolean isSuccess = false;
                        if (response.code() == 200) {
                            AuthResponse authResponse = response.body();
                            if (authResponse != null) {
                                Account account = authResponse.getData();
                                if (account != null) {
                                    isSuccess = true;
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("LOGGED_IN_USER", account);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }
                        if (!isSuccess) {
                            Toast.makeText(LoginActivity.this, "Incorrect login information!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Something went wrong with our server. Please try again later.", Toast.LENGTH_SHORT).show();
                    }
                });
        signInButton.setEnabled(true);
        signInButton.setClickable(true);
    }
}