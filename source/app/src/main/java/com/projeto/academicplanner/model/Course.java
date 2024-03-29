package com.projeto.academicplanner.model;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projeto.academicplanner.adapter.Adapter_Courses;
import com.projeto.academicplanner.helper.ConfigFirebase;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Course extends University implements Serializable {

    private String idUser;
    private String idCourse;
    private String courseName;
    private String acronymCourse;
    private DatabaseReference firebaseRef = ConfigFirebase.getReferenciaFirebase();

    public Course() {

        DatabaseReference courseRef = firebaseRef
                .child("courses");
        setIdCourse(courseRef.push().getKey());

    }

    public void save() {

        DatabaseReference courseRef = firebaseRef
                .child("courses")
                .child(getIdUser())
                .child(getIdCourse());
        courseRef.setValue(this);

    }

    public void update(Course objectToUpdate) {

        DatabaseReference courseRef = firebaseRef
                .child("courses")
                .child(getIdUser())
                .child(getIdCourse());
        courseRef.setValue(objectToUpdate);

    }

    public void delete() {

        DatabaseReference courseRef = firebaseRef
                .child("courses")
                .child(getIdUser())
                .child(getIdCourse());
        courseRef.removeValue();
    }


    public void deleteCourseIntoAdminPeople(Course courseToDelete, AdminPeople adminPeopleParameter) {

        DatabaseReference adminPeopleRef = FirebaseDatabase.getInstance().getReference("adminpeople").child(adminPeopleParameter.getIdUser());

        adminPeopleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    AdminPeople adminPeople = snap.getValue(AdminPeople.class);
                    String adminPeopleIdToRemove = adminPeople.getIdAdminPeople();
                    final boolean[] update = {true};

                    DatabaseReference courseRef = adminPeopleRef.child(adminPeopleIdToRemove).child("courses");

                    courseRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot snap : dataSnapshot.getChildren()) {

                                Course course = snap.getValue(Course.class);
                                String courseIdToRemove = course.getIdCourse();

                                try {

                                    if ((courseToDelete.getIdCourse().equals(courseIdToRemove)) && (update[0]==true)) {
                                        courseRef.child(courseToDelete.getIdCourse()).removeValue();
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


    public void recovery(String idUserLoged, final List<Course> courses, final Adapter_Courses adapter) {

        DatabaseReference courseRef = firebaseRef
                .child("courses")
                .child(idUserLoged);

        courseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                courses.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    courses.add(ds.getValue(Course.class));

                }

                //put the item added to the top
                Collections.reverse(courses);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }


    public String getIdUser() { return idUser; }

    public void setIdUser(String idUser) { this.idUser = idUser; }

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

    public String getAcronymCourse() {
        return acronymCourse;
    }

    public void setAcronymCourse(String acronymCourse) {
        this.acronymCourse = acronymCourse;
    }

}
