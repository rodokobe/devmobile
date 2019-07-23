package com.projeto.academicplanner.model;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projeto.academicplanner.adapter.Adapter_AdminPeople;
import com.projeto.academicplanner.helper.ConfigFirebase;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class AdminPeople implements Serializable {

    private String idUser;
    private String idAdminPeople;
    private String adminPeopleFirstName;
    private String adminPeopleLastName;
    private String adminPeopleEmail;
    private DatabaseReference firebaseRef = ConfigFirebase.getReferenciaFirebase();

    public AdminPeople() {
        DatabaseReference adminPeopleRef = firebaseRef
                .child("adminpeople");
        DatabaseReference coursesRef = firebaseRef
                .child("courses");
        setIdAdminPeople(adminPeopleRef.push().getKey());
    }

    public void recovery(String idUserLoged, final List<AdminPeople> adminPeoples, final Adapter_AdminPeople adapter) {

        DatabaseReference adminPeopleRef = firebaseRef
                .child("adminpeople")
                .child(idUserLoged);

        adminPeopleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                adminPeoples.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    adminPeoples.add(ds.getValue(AdminPeople.class));

                }

                //put the item added to the top
                Collections.reverse(adminPeoples);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    public void save() {

        DatabaseReference adminPeopleRef = firebaseRef
                .child("adminpeople")
                .child(getIdUser())
                .child(getIdAdminPeople());
        adminPeopleRef.setValue(this);
    }

    public void saveAdminPeopleInCourse(Course courseToSave, AdminPeople adminPeopleToSave) {

        //save student on discipline
        DatabaseReference adminPeopleRef = firebaseRef.getRef()
                .child("courses")
                .child(adminPeopleToSave.getIdUser())
                .child(courseToSave.getIdCourse())
                .child("adminpeople")
                .child(adminPeopleToSave.getIdAdminPeople());
        adminPeopleRef.setValue(adminPeopleToSave);
    }

    public void saveCourseInAdminPeople(Course courseToSave, AdminPeople adminPeopleToSave) {

        //save discipline on Student
        DatabaseReference adminPeopleRef = firebaseRef.getRef()
                .child("adminpeople")
                .child(adminPeopleToSave.getIdUser())
                .child(adminPeopleToSave.getIdAdminPeople())
                .child("courses")
                .child(courseToSave.getIdCourse());
        adminPeopleRef.setValue(courseToSave);

    }


    public void update(AdminPeople objectToUpdate) {

        DatabaseReference adminPeopleRef = firebaseRef
                .child("adminpeople")
                .child(getIdUser())
                .child(getIdAdminPeople());
        adminPeopleRef.setValue(objectToUpdate);

    }

    public void updateAdminPeopleInCourse(AdminPeople adminPeopleToUpdate) {

        DatabaseReference coursesRef = FirebaseDatabase.getInstance().getReference("courses").child(adminPeopleToUpdate.getIdUser());

        coursesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Course course = snap.getValue(Course.class);
                    String idCourse = course.getIdCourse();

                    DatabaseReference adminpeopleRef = coursesRef.child(idCourse).child("adminpeople");

                    adminpeopleRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot snap : dataSnapshot.getChildren()) {

                                AdminPeople adminPeople = snap.getValue(AdminPeople.class);

                                String adminPeopleCourseToUpdate = adminPeople.getIdAdminPeople();

                                try {

                                    if (adminPeopleToUpdate.getIdAdminPeople().equals(adminPeopleCourseToUpdate)) {
                                        adminpeopleRef.child(adminPeopleCourseToUpdate).setValue(adminPeopleToUpdate);
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

        DatabaseReference adminPeopleRef = firebaseRef
                .child("adminpeople")
                .child(getIdUser())
                .child(getIdAdminPeople());
        adminPeopleRef.removeValue();
    }

    public void deleteAdminPeopleIntoOneCourse(AdminPeople adminPeopleToDelete, Course courseToDelete) {

        DatabaseReference courseRef = FirebaseDatabase.getInstance().getReference("courses").child(adminPeopleToDelete.getIdUser());

        courseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Course course = snap.getValue(Course.class);
                    String idCourseToRemove = course.getIdCourse();
                    final boolean[] update = {true};

                    DatabaseReference adminPeopleRef = courseRef.child(idCourseToRemove).child("adminpeople");

                    adminPeopleRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot snap : dataSnapshot.getChildren()) {

                                AdminPeople adminPeople = snap.getValue(AdminPeople.class);
                                String adminPeopleToRemove = adminPeople.getIdAdminPeople();

                                try {

                                    if ((idCourseToRemove.equals(courseToDelete.getIdCourse()) && (adminPeopleToRemove.equals(adminPeopleToDelete.getIdAdminPeople())) && update[0]==true)) {
                                        adminPeopleRef.child(adminPeopleToDelete.getIdAdminPeople()).removeValue();
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


    public void deleteAdminPeopleIntoAllCourses(AdminPeople adminPeopleToDelete) {

        DatabaseReference courseRef = FirebaseDatabase.getInstance().getReference("courses").child(adminPeopleToDelete.getIdUser());

        courseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Course course = snap.getValue(Course.class);
                    String idCourse = course.getIdCourse();
                    final boolean[] update = {true};

                    DatabaseReference adminPeopleRef = courseRef.child(idCourse).child("adminpeople");

                    adminPeopleRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot snap : dataSnapshot.getChildren()) {

                                AdminPeople adminPeople = snap.getValue(AdminPeople.class);

                                String adminPeopleToRemove = adminPeople.getIdAdminPeople();

                                try {

                                    if (adminPeopleToRemove.equals(adminPeople.getIdAdminPeople()) && update[0]==true) {
                                        adminPeopleRef.child(adminPeople.getIdAdminPeople()).removeValue();
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

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdAdminPeople() {
        return idAdminPeople;
    }

    public void setIdAdminPeople(String idAdminPeople) {
        this.idAdminPeople = idAdminPeople;
    }

    public String getAdminPeopleFirstName() {
        return adminPeopleFirstName;
    }

    public void setAdminPeopleFirstName(String adminPeopleFirstName) { this.adminPeopleFirstName = adminPeopleFirstName; }

    public String getAdminPeopleLastName() {
        return adminPeopleLastName;
    }

    public void setAdminPeopleLastName(String adminPeopleLastName) { this.adminPeopleLastName = adminPeopleLastName; }

    public String getAdminPeopleEmail() {
        return adminPeopleEmail;
    }

    public void setAdminPeopleEmail(String adminPeopleEmail) { this.adminPeopleEmail = adminPeopleEmail; }

}
