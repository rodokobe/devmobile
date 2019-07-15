package com.projeto.academicplanner.Interface;

import com.projeto.academicplanner.model.Course;

import java.util.List;

public interface IFirebaseLoadDoneCourse {

    void onFireBaseLoadSuccess(List<Course> coursesList);
    void onFireBaseLoadFailed(String message);
}
