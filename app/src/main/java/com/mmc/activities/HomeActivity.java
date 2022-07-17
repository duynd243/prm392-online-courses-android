package com.mmc.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mmc.R;
import com.mmc.fragments.FavoriteFragmemt;
import com.mmc.fragments.HomeFragment;
import com.mmc.fragments.ProfileFragment;
import com.mmc.models.Account;
import com.mmc.models.GetOrdersResponse;
import com.mmc.models.Order;
import com.mmc.repositories.OrderRepository;
import com.mmc.services.OrderService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    Account loggedInUser;
    BottomNavigationView bottomNavigationView;

    List<Order> allOrdersOfUser;
    OrderService orderService;

    public Account getLoggedInUser() {
        return loggedInUser;
    }

    public List<Order> getAllOrdersOfUser() {
        return allOrdersOfUser;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrders();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String email = sharedPreferences.getString("EMAIL", "");
        String fullName = sharedPreferences.getString("FULL_NAME", "");
        String userId = sharedPreferences.getString("USER_ID", "");
        if (!email.isEmpty() && !userId.isEmpty()) {
            loggedInUser = new Account(Long.parseLong(userId), fullName, email, "");
            initComponents();
            initListeners();
            getOrders();
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void initComponents() {
        orderService = OrderRepository.getOrderService();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.home_activity_container, getHomeFragment())
                .commit();
    }

    @SuppressLint("NonConstantResourceId")
    private void initListeners() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.home_nav_bottom:
                    fragment = getHomeFragment();
                    break;
                case R.id.favorite_nav_bottom:
                    fragment = getFavoriteFragment();
                    break;
                case R.id.profile_nav_bottom:
                    fragment = getProfileFragment();
                    break;
            }
            if (fragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.home_activity_container, fragment)
                        .commit();
            }
            return true;
        });
    }

    private HomeFragment getHomeFragment() {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("LOGGED_IN_USER", loggedInUser);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    private FavoriteFragmemt getFavoriteFragment() {
        FavoriteFragmemt favoriteFragment = new FavoriteFragmemt();
        Bundle bundle = new Bundle();
        bundle.putSerializable("LOGGED_IN_USER", loggedInUser);
        favoriteFragment.setArguments(bundle);
        return favoriteFragment;
    }

    private ProfileFragment getProfileFragment() {
        ProfileFragment profileFragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("LOGGED_IN_USER", loggedInUser);
        bundle.putSerializable("ORDERS", (Serializable) allOrdersOfUser);
        profileFragment.setArguments(bundle);
        return profileFragment;
    }

    private void getOrders() {
        orderService
                .getOrdersOfUser(loggedInUser.getId())
                .enqueue(new Callback<GetOrdersResponse>() {
                    @Override
                    public void onResponse(Call<GetOrdersResponse> call, Response<GetOrdersResponse> response) {
                        if (response.code() == 200
                                && response.body() != null
                                && response.body().getData() != null) {
                            GetOrdersResponse x = response.body();
                            allOrdersOfUser = response.body().getData();
                        } else {
                            allOrdersOfUser = new ArrayList<>();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetOrdersResponse> call, Throwable t) {
                        allOrdersOfUser = new ArrayList<>();
                    }
                });
    }

}