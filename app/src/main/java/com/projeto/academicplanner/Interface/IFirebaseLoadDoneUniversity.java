package com.projeto.academicplanner.Interface;

import com.projeto.academicplanner.model.University;

import java.util.List;

public interface IFirebaseLoadDoneUniversity {

    void onFireBaseLoadSuccess(List<University> universitiesList);
    void onFireBaseLoadFailed(String message);
}
