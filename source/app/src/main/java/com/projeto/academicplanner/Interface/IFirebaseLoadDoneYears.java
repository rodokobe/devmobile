package com.projeto.academicplanner.Interface;

import com.projeto.academicplanner.model.Years;

import java.util.List;

public interface IFirebaseLoadDoneYears {

    void onFireBaseLoadYearSuccess(List<Years> yearsList);
    void onFireBaseLoadYearFailed(String message);

}