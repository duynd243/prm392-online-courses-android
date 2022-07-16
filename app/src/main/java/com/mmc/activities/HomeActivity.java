package com.mmc.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mmc.R;
import com.mmc.fragments.FavoriteFragmemt;
import com.mmc.fragments.HomeFragment;
import com.mmc.fragments.ProfileFragment;
import com.mmc.models.Account;

public class HomeActivity extends AppCompatActivity {

    Account loggedInUser;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (getIntent() != null && getIntent().getExtras() != null) {
            loggedInUser = (Account) getIntent().getExtras().getSerializable("LOGGED_IN_USER");
            initComponents();
            initListeners();
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void initComponents() {
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
            // Home Fragment is default selected
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
        profileFragment.setArguments(bundle);
        return profileFragment;
    }

}