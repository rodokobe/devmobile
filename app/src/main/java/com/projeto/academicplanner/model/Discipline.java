package com.projeto.academicplanner.model;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.projeto.academicplanner.adapter.Adapter_Disciplines;
import com.projeto.academicplanner.helper.ConfigFirebase;

import java.util.Collections;
import java.util.List;

public class Discipline extends Course {

    private String idUser;
    private String idUniversity;
    private String universityName;
    private String idCourse;
    private String courseName;
    private String idDiscipline;
    private String disciplineName;
    private String acronymDiscipline;
    private String disciplineYearId;
    private String disciplineYearName;
    private String disciplineSemester;



    private DatabaseReference firebaseRef = ConfigFirebase.getReferenciaFirebase();

    public Discipline() {

        DatabaseReference disciplineRef = firebaseRef
                .child("disciplines");
        setIdDiscipline(disciplineRef.push().getKey());

    }

    public void save() {

        DatabaseReference disciplineRef = firebaseRef
                .child("disciplines")
                .child(getIdUser())
                .child(getIdDiscipline());
        disciplineRef.setValue(this);

    }

    public void update(Discipline objectToUpdate) {

        DatabaseReference disciplineRef = firebaseRef
                .child("disciplines")
                .child(getIdUser())
                .child(getIdDiscipline());
        disciplineRef.setValue(objectToUpdate);

    }

    public void delete() {

        DatabaseReference disciplineRef = firebaseRef
                .child("disciplines")
                .child(getIdUser())
                .child(getIdDiscipline());
        disciplineRef.removeValue();
    }

    public void recovery(String idUserLoged, final List<Discipline> disciplines, final Adapter_Disciplines adapter) {

        DatabaseReference disciplineRef = firebaseRef
                .child("disciplines")
                .child(idUserLoged);

        disciplineRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                disciplines.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    disciplines.add(ds.getValue(Discipline.class));

                }

                //put the item added to the top
                Collections.reverse(disciplines);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public String getIdUser() { return idUser; }

    public void setIdUser(String idUser) { this.idUser = idUser; }

    public String getIdUniversity() {
        return idUniversity;
    }

    public void setIdUniversity(String idUniversity) {
        this.idUniversity = idUniversity;
    }

    public String getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(String idCourse) {
        this.idCourse = idCourse;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getIdDiscipline() { return idDiscipline; }

    public void setIdDiscipline(String idDiscipline) { this.idDiscipline = idDiscipline; }

    public String getDisciplineName() { return disciplineName; }

    public void setDisciplineName(String disciplineName) { this.disciplineName = disciplineName; }

    public String getAcronymDiscipline() { return acronymDiscipline; }

    public void setAcronymDiscipline(String acronymDiscipline) { this.acronymDiscipline = acronymDiscipline; }

    public String getDisciplineYearId() { return disciplineYearId; }

    public void setDisciplineYearId(String disciplineYearId) { this.disciplineYearId = disciplineYearId; }

    public String getDisciplineYearName() { return disciplineYearName; }

    public void setDisciplineYearName(String disciplineYearName) { this.disciplineYearName = disciplineYearName; }

    public String getDisciplineSemester() { return disciplineSemester; }

    public void setDisciplineSemester(String disciplineSemester) { this.disciplineSemester = disciplineSemester; }

}
