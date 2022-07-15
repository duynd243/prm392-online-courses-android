package com.mmc.services;

import com.mmc.models.Course;
import com.mmc.models.CourseResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CourseService {
    String COURSE = "courses";

    @GET(COURSE)
    Call<CourseResponse> getAllCourses(
            @Query("name") String searchedName,
            @Query("sort") String sort,
            @Query("page") int pageNumber,
            @Query("size") int pageSize
    );

    @GET(COURSE + "/{id}")
    Call<Course> getCourseById(@Path("id") Object id);

}
