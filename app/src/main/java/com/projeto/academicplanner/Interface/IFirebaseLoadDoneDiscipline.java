package com.projeto.academicplanner.Interface;

import com.projeto.academicplanner.model.Discipline;

import java.util.List;

public interface IFirebaseLoadDoneDiscipline {

    void onFireBaseLoadDisciplineSuccess(List<Discipline> disciplinesList);
    void onFireBaseLoadDisciplineFailed(String message);
}
