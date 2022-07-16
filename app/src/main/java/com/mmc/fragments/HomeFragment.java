package com.mmc.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.chip.ChipGroup;
import com.mmc.R;
import com.mmc.adapters.HomeCourseAdapter;
import com.mmc.models.Account;
import com.mmc.models.Course;
import com.mmc.models.CourseResponse;
import com.mmc.repositories.CourseRepository;
import com.mmc.services.CourseService;
import com.mmc.utils.GreetingUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private enum CourseType {
        ALL,
        NEWEST,
        POPULAR,
        MOST_RATED,
    }

    TextView tvGreeting, tvUserFullName;
    Account loggedInUser;
    ChipGroup chipGroupCourse;
    CourseService courseService;
    RecyclerView rvCourses;
    HomeCourseAdapter courseAdapter;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            loggedInUser = (Account) getArguments().getSerializable("LOGGED_IN_USER");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        courseService = CourseRepository.getCourseService();
        chipGroupCourse = view.findViewById(R.id.chipGroupCourse);
        rvCourses = view.findViewById(R.id.rvCourses);
        courseAdapter = new HomeCourseAdapter(getContext(), new ArrayList<>());
        rvCourses.setAdapter(courseAdapter);
        rvCourses.setLayoutManager(new LinearLayoutManager(getContext()));

        updateCoursesAdapter(CourseType.ALL);

        chipGroupCourse.setOnCheckedStateChangeListener((group, checkedIds) -> {
            int checkedChipId = chipGroupCourse.getCheckedChipId();
            switch (checkedChipId) {
                case R.id.chipAll:
                    updateCoursesAdapter(CourseType.ALL);
                    break;
                case R.id.chipNewest:
                    updateCoursesAdapter(CourseType.NEWEST);
                    break;
                case R.id.chipPopular:
                    updateCoursesAdapter(CourseType.POPULAR);
                    break;
                case R.id.chipMostRated:
                    updateCoursesAdapter(CourseType.MOST_RATED);
                    break;
            }
        });

        tvGreeting = view.findViewById(R.id.tvGreeting);
        tvGreeting.setText(GreetingUtils.getGreeting() + ",");
        tvUserFullName = view.findViewById(R.id.tvUserFullName);
        tvUserFullName.setText(loggedInUser.getFullName());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void updateCoursesAdapter(CourseType courseType) {

        int page = 1;
        int size = 10;
        String sort = "";

        if (courseType == CourseType.NEWEST) {
            sort = "createDate desc";
        } else if (courseType == CourseType.POPULAR) {
            sort = "currentNumberMentee desc";
        } else if (courseType == CourseType.MOST_RATED) {
            sort = "totalRating desc";
        }

        courseService
                .getAllCourses("", sort, page, size)
                .enqueue(new Callback<CourseResponse>() {
                    @Override
                    public void onResponse(Call<CourseResponse> call, Response<CourseResponse> response) {
                        if (response.code() == 200) {
                            CourseResponse courseResponse = response.body();
                            if (courseResponse != null && courseResponse.getData() != null) {
                                List<Course> courses = courseResponse.getData();
                                courseAdapter.setCourses(courses);
                                courseAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CourseResponse> call, Throwable t) {
                    }
                });
    }
}