package com.projeto.academicplanner.Interface;

import com.projeto.academicplanner.model.Course;

import java.util.List;

public interface IFirebaseLoadDoneCourse {

    void onFireBaseLoadCourseSuccess(List<Course> coursesList);
    void onFireBaseLoadCourseFailed(String message);
}
