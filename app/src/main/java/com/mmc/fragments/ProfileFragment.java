package com.mmc.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.mmc.R;
import com.mmc.activities.LoginActivity;
import com.mmc.activities.OrdersActivity;
import com.mmc.models.Account;
import com.mmc.models.Order;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    Account loggedInUser;
    TextView tvUserFullName, tvTotalBoughtCourses;
    LinearLayout menuOrders, menuSignOut;
    List<Order> allOrdersOfUser;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            loggedInUser = (Account) getArguments().getSerializable("LOGGED_IN_USER");
            allOrdersOfUser = (List<Order>) getArguments().getSerializable("ORDERS");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvUserFullName = view.findViewById(R.id.tvUserFullName);
        tvTotalBoughtCourses = view.findViewById(R.id.tvTotalBoughtCourses);
        menuOrders = view.findViewById(R.id.menuOrders);
        menuSignOut = view.findViewById(R.id.menuSignOut);

        menuSignOut.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Sign Out");
            builder.setMessage("Are you sure you want to sign out?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                SharedPreferences sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences("user_info", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                startActivity(intent);
                Objects.requireNonNull(getActivity()).finish();
            });
            builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
            builder.show();
        });

        tvUserFullName.setText(loggedInUser.getFullName());
        tvTotalBoughtCourses.setText(String.valueOf(allOrdersOfUser.size()));

        menuOrders.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), OrdersActivity.class);
            intent.putExtra("ORDERS", (Serializable) allOrdersOfUser);
            startActivity(intent);
        });

        return view;
    }
}