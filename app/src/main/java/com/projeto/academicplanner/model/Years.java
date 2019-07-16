package com.projeto.academicplanner.model;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.projeto.academicplanner.adapter.Adapter_Years;
import com.projeto.academicplanner.helper.ConfigFirebase;

import java.util.Collections;
import java.util.List;

public class Years {

    private String idUser;
    private String idYear;
    private String yearName;

    private DatabaseReference firebaseRef = ConfigFirebase.getReferenciaFirebase();

    public Years() {

        DatabaseReference courseRef = firebaseRef
                .child("years");
        setIdYear(courseRef.push().getKey());

    }

    public void recovery(String idUserLogged, final List<Years> years, final Adapter_Years adapter) {

        DatabaseReference yearsRef = firebaseRef
                .child("years")
                .child(idUserLogged);

        yearsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                years.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    years.add(ds.getValue(Years.class));

                }

                //put the item added to the top
                Collections.reverse(years);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    public void save() {

        DatabaseReference yearsRef = firebaseRef
                .child("years")
                .child(getIdUser())
                .child(getIdYear());
        yearsRef.setValue(this);

    }

    public void update(Years objectToUpdate) {

        DatabaseReference yearsRef = firebaseRef
                .child("years")
                .child(getIdUser())
                .child(getIdYear());
        yearsRef.setValue(objectToUpdate);

    }

    public void delete() {

        DatabaseReference yearsRef = firebaseRef
                .child("years")
                .child(getIdUser())
                .child(getIdYear());
        yearsRef.removeValue();
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdYear() {
        return idYear;
    }

    public void setIdYear(String idYear) {
        this.idYear = idYear;
    }

    public String getYearName() {
        return yearName;
    }

    public void setYearName(String yearName) {
        this.yearName = yearName;
    }
}
