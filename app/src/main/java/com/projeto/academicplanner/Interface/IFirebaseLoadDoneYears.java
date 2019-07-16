package com.projeto.academicplanner.Interface;

import com.projeto.academicplanner.model.Years;

import java.util.List;

public interface IFirebaseLoadDoneYears {

    void onFireBaseLoadSuccess(List<Years> yearsList);
    void onFireBaseLoadFailed(String message);

}