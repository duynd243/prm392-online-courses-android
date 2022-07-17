package com.mmc.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mmc.R;
import com.mmc.adapters.OrderCourseAdapter;
import com.mmc.models.Account;
import com.mmc.models.Course;
import com.mmc.models.Order;
import com.mmc.repositories.CourseRepository;
import com.mmc.services.CourseService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {

    Account loggedInUser;
    List<Order> allOrdersOfUser;
    List<Course> coursesFromOrders;
    RecyclerView rvCourses;
    ImageButton btnGoBack;
    TextView tvNoOrders;
    OrderCourseAdapter orderCourseAdapter;
    CourseService courseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String email = sharedPreferences.getString("EMAIL", "");
        String fullName = sharedPreferences.getString("FULL_NAME", "");
        String userId = sharedPreferences.getString("USER_ID", "");
        if (!email.isEmpty() && !userId.isEmpty() && getIntent() != null) {
            loggedInUser = new Account(Long.parseLong(userId), fullName, email, "");
            allOrdersOfUser = (List<Order>) getIntent().getExtras().getSerializable("ORDERS");
            courseService = CourseRepository.getCourseService();
            coursesFromOrders = new ArrayList<>();

            tvNoOrders = findViewById(R.id.tvNoOrders);
            rvCourses = findViewById(R.id.rvCourses);
            btnGoBack = findViewById(R.id.btnGoBack);
            btnGoBack.setOnClickListener(v -> onBackPressed());
            orderCourseAdapter = new OrderCourseAdapter(this, coursesFromOrders);
            rvCourses.setAdapter(orderCourseAdapter);
            rvCourses.setLayoutManager(new LinearLayoutManager(this));

            if (allOrdersOfUser != null && allOrdersOfUser.size() > 0) {
                getCoursesByOrders();
            } else {
                rvCourses.setVisibility(View.GONE);
                tvNoOrders.setVisibility(View.VISIBLE);
            }

        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void getCoursesByOrders() {
        for (Order order : allOrdersOfUser) {
            courseService
                    .getCourseById(order.getCourseId())
                    .enqueue(new Callback<Course>() {
                        @Override
                        public void onResponse(Call<Course> call, Response<Course> response) {
                            if (response.code() == 200 && response.body() != null) {
                                coursesFromOrders.add(response.body());
                                orderCourseAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(Call<Course> call, Throwable t) {

                        }
                    });
        }

    }
}