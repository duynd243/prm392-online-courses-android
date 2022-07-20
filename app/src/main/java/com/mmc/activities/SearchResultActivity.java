package com.mmc.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mmc.R;
import com.mmc.adapters.CourseAdapter;
import com.mmc.models.Account;
import com.mmc.models.Course;
import com.mmc.models.CourseResponse;
import com.mmc.models.Order;
import com.mmc.repositories.CourseRepository;
import com.mmc.services.CourseService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {
    Account loggedInUser;
    List<Order> allOrdersOfUser;
    List<Course> coursesFromOrders;
    List<Course> searchedCourses;
    CourseService courseService;
    CourseAdapter courseAdapter;
    String searchValue = "";

    public Account getLoggedInUser() {
        return loggedInUser;
    }

    public List<Order> getAllOrdersOfUser() {
        return allOrdersOfUser;
    }

    RecyclerView rvCourses;
    ImageButton btnGoBack;
    TextView tvNotFound;
    LinearLayout llSearchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String email = sharedPreferences.getString("EMAIL", "");
        String fullName = sharedPreferences.getString("FULL_NAME", "");
        String userId = sharedPreferences.getString("USER_ID", "");

        if (!email.isEmpty() && !userId.isEmpty() && getIntent() != null) {
            loggedInUser = new Account(Long.parseLong(userId), fullName, email, "");
            allOrdersOfUser = (List<Order>) getIntent().getExtras().getSerializable("ORDERS");
            searchValue = getIntent().getExtras().getString("SEARCH_VALUE");
            courseService = CourseRepository.getCourseService();
            coursesFromOrders = new ArrayList<>();
            searchedCourses = new ArrayList<>();
            tvNotFound = findViewById(R.id.tvNotFound);
            rvCourses = findViewById(R.id.rvCourses);
            btnGoBack = findViewById(R.id.btnGoBack);
            llSearchResult = findViewById(R.id.llSearchResult);
            btnGoBack.setOnClickListener(v -> onBackPressed());
            courseAdapter = new CourseAdapter(this, searchedCourses);
            rvCourses.setAdapter(courseAdapter);
            rvCourses.setLayoutManager(new LinearLayoutManager(this));
            getSearchedCourses();

        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }


    private void getSearchedCourses() {
        courseService
                .getAllCourses(searchValue, "", 1, 100)
                .enqueue(new Callback<CourseResponse>() {
                    @Override
                    public void onResponse(Call<CourseResponse> call, Response<CourseResponse> response) {
                        if (response.code() == 200 && response.body() != null && response.body().getData() != null) {
                            searchedCourses = response.body().getData();
                            courseAdapter.setCourses(searchedCourses);
                            courseAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<CourseResponse> call, Throwable t) {

                    }
                });
    }

}