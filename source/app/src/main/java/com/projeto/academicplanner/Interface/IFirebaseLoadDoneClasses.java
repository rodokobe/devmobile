package com.projeto.academicplanner.Interface;

import com.projeto.academicplanner.model.Classes;

import java.util.List;

public interface IFirebaseLoadDoneClasses {

    void onFireBaseLoadClassesSuccess(List<Classes> classesList);
    void onFireBaseLoadClassesFailed(String message);
}
