package com.projeto.academicplanner.model;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.projeto.academicplanner.adapter.Adapter_Classes;
import com.projeto.academicplanner.helper.ConfigFirebase;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Timer;

public class Classes {

    private String idUser;
    private String idUniversity;
    private String nameUniversity;
    private String idCourse;
    private String nameCourse;
    private String idDiscipline;
    private String nameDiscipline;
    private String idClass;
    private String subject;
    private Date classDate;
    private Timer classTime;
    private int timeDuration;
    private String classroom;
    private String topicsAndContents;

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

    public Date getClassDate() {
        return classDate;
    }

    public void setClassDate(Date classDate) {
        this.classDate = classDate;
    }

    public Timer getClassTime() {
        return classTime;
    }

    public void setClassTime(Timer classTime) {
        this.classTime = classTime;
    }

    public int getTimeDuration() {
        return timeDuration;
    }

    public void setTimeDuration(int timeDuration) {
        this.timeDuration = timeDuration;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getTopicsAndContents() {
        return topicsAndContents;
    }

    public void setTopicsAndContents(String topicsAndContents) {
        this.topicsAndContents = topicsAndContents;
    }
}
