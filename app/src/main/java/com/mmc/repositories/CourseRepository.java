package com.mmc.repositories;

import com.mmc.api.APIClient;
import com.mmc.services.CourseService;

public class CourseRepository {
    public static CourseService getCourseService() {
        return APIClient.getAPIClient().create(CourseService.class);
    }
}
