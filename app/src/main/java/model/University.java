package model;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.List;

import adapter.Adapter_Universities;
import helper.ConfigFirebase;

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

    public void recoveryUniversities(String idUserLoged, final List<University> universities, final Adapter_Universities adapter) {

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

    public void saveUniversityData() {

        DatabaseReference universityRef = firebaseRef
                .child("universities")
                .child(getIdUser())
                .child(getIdUniversity());
        universityRef.setValue(this);
    }

    public void updateUniversityData(University objectToUpdate) {

        DatabaseReference universityRef = firebaseRef
                .child("universities")
                .child(getIdUser())
                .child(getIdUniversity());
        universityRef.setValue(objectToUpdate);

    }

    public void deleteUniversityData() {

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
