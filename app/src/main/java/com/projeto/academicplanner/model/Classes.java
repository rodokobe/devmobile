package com.projeto.academicplanner.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.projeto.academicplanner.adapter.Adapter_Classes_Calendar;
import com.projeto.academicplanner.helper.ConfigFirebase;

import java.util.Collections;
import java.util.List;

public class Classes implements Parcelable {

    private String idUser;
    private String idClass;
    private String subject;
    private String classDate;
    private String classTime;
    private String timeDuration;
    private String classroom;
    private String topicsAndContents;
    private String idUniversity;
    private String nameUniversity;
    private String idCourse;
    private String nameCourse;
    private String idDiscipline;
    private String nameDiscipline;
    private String idYear;
    private String nameYear;
    private String semester;
    private String isSpecial;
    private String isSpecialEvent;


    private DatabaseReference firebaseRef = ConfigFirebase.getReferenciaFirebase();
    private DatabaseReference classesRef, disciplineRef;

    public Classes() {
        classesRef = firebaseRef
                .child("classes");
        setIdClass(classesRef.push().getKey());

        disciplineRef = firebaseRef
                .child("disciplines")
                .child("classes");
        setIdDiscipline(disciplineRef.push().getKey());
    }

    protected Classes(Parcel in) {
        idUser = in.readString();
        idClass = in.readString();
        subject = in.readString();
        classDate = in.readString();
        classTime = in.readString();
        timeDuration = in.readString();
        classroom = in.readString();
        topicsAndContents = in.readString();
        idUniversity = in.readString();
        nameUniversity = in.readString();
        idCourse = in.readString();
        nameCourse = in.readString();
        idDiscipline = in.readString();
        nameDiscipline = in.readString();
        idYear = in.readString();
        nameYear = in.readString();
        semester = in.readString();
        isSpecial = in.readString();
        isSpecialEvent = in.readString();
    }

    public static final Creator<Classes> CREATOR = new Creator<Classes>() {
        @Override
        public Classes createFromParcel(Parcel in) {
            return new Classes(in);
        }

        @Override
        public Classes[] newArray(int size) {
            return new Classes[size];
        }
    };

    public void recovery(String idUserLogged, final List<Classes> classes, final Adapter_Classes_Calendar adapter) {

        classesRef = firebaseRef.getRef()
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

    public void recoveryClassesInDiscipline(String idUserLogged, String idDisciplineParameter, final List<Classes> classes, final Adapter_Classes_Calendar adapter) {

        classesRef = firebaseRef.getRef()
                .child("disciplines")
                .child(idUserLogged)
                .child(idDisciplineParameter)
                .child("classes");

        classesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                classes.clear();

                for (DataSnapshot dataSnapshotClasses : dataSnapshot.getChildren()) {


                }
            }
            @Override
            public void onCancelled (@NonNull DatabaseError databaseError){

            }
        });
    }

    public void recoverySimple(String idUserLogged, final List<Classes> classes, final Adapter_Classes_Calendar adapter) {

        classesRef = firebaseRef.getRef()
                .child("classes")
                .child(idUserLogged);

        classesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                classes.clear();

                for (DataSnapshot dataSnapshotClasses : dataSnapshot.getChildren()) {

                    Classes classe = dataSnapshotClasses.getValue(Classes.class);
                    classes.add(classe);

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

        disciplineRef = firebaseRef
                .child("disciplines")
                .child(getIdUser())
                .child(getIdDiscipline())
                .child("classes")
                .child(getIdClass());
        disciplineRef.setValue(this);

        disciplineRef = firebaseRef
                .child("classes")
                .child(getIdClass());
        disciplineRef.setValue(this);
    }

    public void update(Classes objectToUpdate) {

        classesRef = firebaseRef
                .child("disciplines")
                .child(getIdUser())
                .child(getIdDiscipline())
                .child("classes")
                .child(getIdClass());
        classesRef.setValue(objectToUpdate);

    }

    public void delete() {

        classesRef = firebaseRef
                .child("disciplines")
                .child(getIdUser())
                .child(getIdDiscipline())
                .child("classes")
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

    public String getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(String isSpecial) {
        this.isSpecial = isSpecial;
    }

    public String getIsSpecialEvent() {
        return isSpecialEvent;
    }

    public void setIsSpecialEvent(String isSpecialEvent) {
        this.isSpecialEvent = isSpecialEvent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idUser);
        dest.writeString(idClass);
        dest.writeString(subject);
        dest.writeString(classDate);
        dest.writeString(classTime);
        dest.writeString(timeDuration);
        dest.writeString(classroom);
        dest.writeString(topicsAndContents);
        dest.writeString(idUniversity);
        dest.writeString(nameUniversity);
        dest.writeString(idCourse);
        dest.writeString(nameCourse);
        dest.writeString(idDiscipline);
        dest.writeString(nameDiscipline);
        dest.writeString(idYear);
        dest.writeString(nameYear);
        dest.writeString(semester);
        dest.writeString(isSpecial);
        dest.writeString(isSpecialEvent);
    }
}
