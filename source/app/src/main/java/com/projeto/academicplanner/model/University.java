package com.projeto.academicplanner.model;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.projeto.academicplanner.adapter.Adapter_Universities;
import com.projeto.academicplanner.helper.ConfigFirebase;

import java.util.Collections;
import java.util.List;

public class University {

    private String idUser;
    private String idUniversity;
    private String universityName;
    private String universityAcronym;
    private DatabaseReference firebaseRef = ConfigFirebase.getReferenciaFirebase();

    public University() {

        DatabaseReference universityRef = firebaseRef
                .child("universities");
        setIdUniversity(universityRef.push().getKey());

    }

    public void recovery(String idUserLoged, final List<University> universities, final Adapter_Universities adapter) {

        DatabaseReference universitiesRef = firebaseRef
                .child("universities")
                .child(idUserLoged);

        universitiesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                universities.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    universities.add(ds.getValue(University.class));

                }

                //put the item added to the top
                Collections.reverse(universities);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    public void save() {

        DatabaseReference universityRef = firebaseRef
                .child("universities")
                .child(getIdUser())
                .child(getIdUniversity());
        universityRef.setValue(this);
    }

    public void update(University objectToUpdate) {

        DatabaseReference universityRef = firebaseRef
                .child("universities")
                .child(getIdUser())
                .child(getIdUniversity());
        universityRef.setValue(objectToUpdate);

    }

    public void delete() {

        DatabaseReference universityRef = firebaseRef
                .child("universities")
                .child(getIdUser())
                .child(getIdUniversity());
        universityRef.removeValue();
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

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getUniversityAcronym() {
        return universityAcronym;
    }

    public void setUniversityAcronym(String universityAcronym) {
        this.universityAcronym = universityAcronym;
    }
}
