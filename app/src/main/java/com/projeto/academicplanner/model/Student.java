package com.projeto.academicplanner.model;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projeto.academicplanner.adapter.Adapter_Students;
import com.projeto.academicplanner.helper.ConfigFirebase;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Student extends Discipline implements Serializable {

    private String idUser;
    private String idStudent;
    private String studentFirstName;
    private String studentLastName;
    private String studentEmail;
    private String studentDelegate;
    private String idUniversity;
    private String universityName;
    private String idCourse;
    private String courseName;
    private DatabaseReference firebaseRef = ConfigFirebase.getReferenciaFirebase();
    private DatabaseReference studentRef, disciplineRef;

    public Student() {

        DatabaseReference studentRef = firebaseRef
                .child("students");
        setIdStudent(studentRef.push().getKey());

    }

    public void save() {

        studentRef = firebaseRef.getRef()
                .child("students")
                .child(getIdUser())
                .child(getIdStudent());
        studentRef.setValue(this);

    }

    public void saveStudentInDiscipline(Discipline disciplineToSave, Student studentToSave) {

        //save student on discipline
        disciplineRef = firebaseRef.getRef()
                .child("disciplines")
                .child(studentToSave.getIdUser())
                .child(disciplineToSave.getIdDiscipline())
                .child("students")
                .child(studentToSave.getIdStudent());
        disciplineRef.setValue(studentToSave);

    }

    public void saveDisciplineInStudent(Discipline disciplineToSave, Student studentToSave) {

        //save discipline on Student
        studentRef = firebaseRef.getRef()
                .child("students")
                .child(studentToSave.getIdUser())
                .child(studentToSave.getIdStudent())
                .child("disciplines")
                .child(disciplineToSave.getIdDiscipline());
        studentRef.setValue(disciplineToSave);

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


    public void update(Student objectToUpdate) {

        DatabaseReference studentsRef = firebaseRef
                .child("students")
                .child(getIdUser())
                .child(objectToUpdate.getIdStudent());
        studentsRef.setValue(objectToUpdate);

    }


    public void updateStudentOnDiscipline(Student studentToUpdate) {

        DatabaseReference disciplineRef = FirebaseDatabase.getInstance().getReference("disciplines").child(studentToUpdate.getIdUser());

        disciplineRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Discipline discipline = snap.getValue(Discipline.class);
                    String idDiscipline = discipline.getIdDiscipline();

                    DatabaseReference studentsRef = disciplineRef.child(idDiscipline).child("students");

                    studentsRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot snap : dataSnapshot.getChildren()) {

                                Student student = snap.getValue(Student.class);

                                String studentDisciplineToUpdate = student.getIdStudent();
                                String studentDiscipline = student.getIdDiscipline();

                                try {

                                    if (studentToUpdate.getIdStudent().equals(studentDisciplineToUpdate)) {
                                        studentsRef.child(studentDisciplineToUpdate).setValue(studentToUpdate);
                                    } else {
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void delete() {

        DatabaseReference studentsRef = firebaseRef.child("students").child(getIdUser()).child(getIdStudent());
        studentsRef.removeValue();

    }


    public void deleteStudentIntoOneDiscipline(Student studentToDelete, Discipline disciplineToDelete) {

        DatabaseReference disciplineRef = FirebaseDatabase.getInstance().getReference("disciplines").child(studentToDelete.getIdUser());

        disciplineRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Discipline discipline = snap.getValue(Discipline.class);
                    String idDisciplineToRemove = discipline.getIdDiscipline();
                    final boolean[] update = {true};

                    DatabaseReference studentsRef = disciplineRef.child(idDisciplineToRemove).child("students");

                    studentsRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot snap : dataSnapshot.getChildren()) {

                                Student student = snap.getValue(Student.class);
                                String studentToRemove = student.getIdStudent();

                                try {

                                    if ((idDisciplineToRemove.equals(disciplineToDelete.getIdDiscipline()) && (studentToRemove.equals(studentToDelete.getIdStudent())) && update[0]==true)) {
                                        studentsRef.child(studentToDelete.getIdStudent()).removeValue();
                                        update[0] = false;
                                    } else {

                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void deleteStudentIntoAllDisciplines(Student studentToDelete) {

        DatabaseReference disciplineRef = FirebaseDatabase.getInstance().getReference("disciplines").child(studentToDelete.getIdUser());

        disciplineRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Discipline discipline = snap.getValue(Discipline.class);
                    String idDiscipline = discipline.getIdDiscipline();
                    final boolean[] update = {true};

                    DatabaseReference studentsRef = disciplineRef.child(idDiscipline).child("students");

                    studentsRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot snap : dataSnapshot.getChildren()) {

                                Student student = snap.getValue(Student.class);

                                String studentToRemove = student.getIdStudent();

                                try {

                                    if (studentToRemove.equals(studentToDelete.getIdStudent()) && update[0]==true) {
                                        studentsRef.child(studentToDelete.getIdStudent()).removeValue();
                                        update[0] = false;
                                    } else {

                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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

    public String getIdUniversity() { return idUniversity; }

    public void setIdUniversity(String idUniversity) { this.idUniversity = idUniversity; }

    public String getUniversityName() { return universityName; }

    public void setUniversityName(String universityName) { this.universityName = universityName; }

    public String getIdCourse() { return idCourse; }

    public void setIdCourse(String idCourse) { this.idCourse = idCourse; }

    public String getCourseName() { return courseName; }

    public void setCourseName(String courseName) { this.courseName = courseName; }

}