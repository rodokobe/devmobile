package com.projeto.academicplanner.model;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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

    public void update(AdminPeople objectToUpdate) {

        DatabaseReference adminPeopleRef = firebaseRef
                .child("adminpeople")
                .child(getIdUser())
                .child(getIdAdminPeople());
        adminPeopleRef.setValue(objectToUpdate);

    }

    public void delete() {

        DatabaseReference adminPeopleRef = firebaseRef
                .child("adminpeople")
                .child(getIdUser())
                .child(getIdAdminPeople());
        adminPeopleRef.removeValue();
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

    public void setAdminPeopleFirstName(String adminPeopleFirstName) {
        this.adminPeopleFirstName = adminPeopleFirstName;
    }

    public String getAdminPeopleLastName() {
        return adminPeopleLastName;
    }

    public void setAdminPeopleLastName(String adminPeopleLastName) {
        this.adminPeopleLastName = adminPeopleLastName;
    }

    public String getAdminPeopleEmail() {
        return adminPeopleEmail;
    }

    public void setAdminPeopleEmail(String adminPeopleEmail) {
        this.adminPeopleEmail = adminPeopleEmail;
    }
}
