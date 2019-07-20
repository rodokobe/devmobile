package com.projeto.academicplanner.model;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.projeto.academicplanner.adapter.Adapter_Students;
import com.projeto.academicplanner.helper.ConfigFirebase;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Student implements Serializable {

    private String idUser;
    private String idStudent;
    private String studentFirstName;
    private String studentLastName;
    private String studentEmail;
    private String studentDelegate;
    private DatabaseReference firebaseRef = ConfigFirebase.getReferenciaFirebase();
    DatabaseReference studentRef;

    Discipline discipline = new Discipline();

    public Student() {

        studentRef = firebaseRef
                .child("students");
        setIdStudent(studentRef.push().getKey());

    }

    public void recovery(String idUserLoged, final List<Student> students, final Adapter_Students adapter) {

        DatabaseReference studentsRef = firebaseRef
                .child("students")
                .child(idUserLoged);

        studentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                students.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    students.add(ds.getValue(Student.class));

                }

                //put the item added to the top
                Collections.reverse(students);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    public void saveOnDiscipline(String studentOnDiscipline)
    {
        DatabaseReference disciplineRef = firebaseRef
                .child("disciplines")
                .child("students")
                .child(getIdStudent());
        disciplineRef.setValue(studentOnDiscipline);

    }

    public void removeFromDiscipline()
    {
        DatabaseReference disciplineRef = firebaseRef
                .child("disciplines")
                .child(getIdUser())
                .child(discipline.getIdDiscipline())
                .child("students")
                .child(getIdStudent());
        disciplineRef.removeValue();
    }

    public void save() {

        DatabaseReference studentsRef = firebaseRef
                .child("students")
                .child(getIdUser())
                .child(getIdStudent());
        studentsRef.setValue(this);
    }

    public void update(Student objectToUpdate) {

        DatabaseReference studentsRef = firebaseRef
                .child("students")
                .child(getIdUser())
                .child(getIdStudent());
        studentsRef.setValue(objectToUpdate);

    }

    public void delete() {

        DatabaseReference studentsRef = firebaseRef
                .child("students")
                .child(getIdUser())
                .child(getIdStudent());
        studentsRef.removeValue();
    }

    public String getIdUser() { return idUser; }

    public void setIdUser(String idUser) { this.idUser = idUser; }

    public String getIdStudent() { return idStudent; }

    public void setIdStudent(String idStudent) { this.idStudent = idStudent; }

    public String getStudentFirstName() { return studentFirstName; }

    public void setStudentFirstName(String studentFirstName) { this.studentFirstName = studentFirstName; }

    public String getStudentLastName() { return studentLastName; }

    public void setStudentLastName(String studentLastName) { this.studentLastName = studentLastName; }

    public String getStudentEmail() { return studentEmail; }

    public void setStudentEmail(String studentEmail) { this.studentEmail = studentEmail; }

    public String getStudentDelegate() { return studentDelegate; }

    public void setStudentDelegate(String studentDelegate) { this.studentDelegate = studentDelegate; }
}