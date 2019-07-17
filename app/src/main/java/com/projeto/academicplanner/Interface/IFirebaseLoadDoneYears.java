package com.projeto.academicplanner.Interface;

import com.projeto.academicplanner.model.Years;

import java.util.List;

public interface IFirebaseLoadDoneYears {

    void onFireBaseLoadYearsSuccess(List<Years> yearsList);
    void onFireBaseLoadYearsFailed(String message);

}