package com.projeto.academicplanner.Interface;

import com.projeto.academicplanner.model.University;

import java.util.List;

public interface IFirebaseLoadDoneUniversity {

    void onFireBaseLoadUniversitySuccess(List<University> universitiesList);
    void onFireBaseLoadUniversityFailed(String message);
}
