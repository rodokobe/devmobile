package com.projeto.academicplanner.model;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.projeto.academicplanner.adapter.Adapter_Classes;
import com.projeto.academicplanner.helper.ConfigFirebase;

import java.util.Collections;
import java.util.List;

public class Classes {

    private String idUser, idClass, subject, classDate, classTime, timeDuration, classroom, topicsAndContents,
            idUniversity, nameUniversity, idCourse, nameCourse, idDiscipline, nameDiscipline, idYear, nameYear, semester;

    private DatabaseReference firebaseRef = ConfigFirebase.getReferenciaFirebase();
    private DatabaseReference classesRef;

    public Classes() {
        classesRef = firebaseRef
                .child("classes");
        setIdClass(classesRef.push().getKey());
    }

    public void recovery(String idUserLogged, final List<Classes> classes, final Adapter_Classes adapter) {

        classesRef = firebaseRef
                .child("classes")
                .child(idUserLogged);

        classesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                classes.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    classes.add(ds.getValue(Classes.class));

                }

                //put the item added to the top
                Collections.reverse(classes);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    public void save() {

        classesRef = firebaseRef
                .child("classes")
                .child(getIdUser())
                .child(getIdClass());
        classesRef.setValue(this);
    }

    public void update(Classes objectToUpdate) {

        classesRef = firebaseRef
                .child("classes")
                .child(getIdUser())
                .child(getIdClass());
        classesRef.setValue(objectToUpdate);

    }

    public void delete() {

        classesRef = firebaseRef
                .child("classes")
                .child(getIdUser())
                .child(getIdClass());
        classesRef.removeValue();
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdUniversity() {
        return idUniversity;
    }

    public void setIdUniversity(String idUniversity) {
        this.idUniversity = idUniversity;
    }

    public String getNameUniversity() {
        return nameUniversity;
    }

    public void setNameUniversity(String nameUniversity) {
        this.nameUniversity = nameUniversity;
    }

    public String getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(String idCourse) {
        this.idCourse = idCourse;
    }

    public String getNameCourse() {
        return nameCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    public String getIdDiscipline() {
        return idDiscipline;
    }

    public void setIdDiscipline(String idDiscipline) {
        this.idDiscipline = idDiscipline;
    }

    public String getNameDiscipline() {
        return nameDiscipline;
    }

    public void setNameDiscipline(String nameDiscipline) {
        this.nameDiscipline = nameDiscipline;
    }

    public String getIdClass() {
        return idClass;
    }

    public void setIdClass(String idClass) {
        this.idClass = idClass;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getClassDate() { return classDate; }

    public void setClassDate(String classDate) { this.classDate = classDate; }

    public String getClassTime() { return classTime; }

    public void setClassTime(String classTime) { this.classTime = classTime; }

    public String getTimeDuration() { return timeDuration; }

    public void setTimeDuration(String timeDuration) { this.timeDuration = timeDuration; }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getTopicsAndContents() {
        return topicsAndContents;
    }

    public void setTopicsAndContents(String topicsAndContents) { this.topicsAndContents = topicsAndContents; }

    public String getIdYear() { return idYear; }

    public void setIdYear(String idYear) { this.idYear = idYear; }

    public String getNameYear() { return nameYear; }

    public void setNameYear(String nameYear) { this.nameYear = nameYear; }

    public String getSemester() { return semester; }

    public void setSemester(String semester) { this.semester = semester; }
}
