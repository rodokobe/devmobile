package model;

import com.google.firebase.database.DatabaseReference;

import helper.ConfigFirebase;

public class UserProfile {

    private String idUser;
    private String firstname;
    private String lastname;
    private String email;
    private String urlProfile;

    public UserProfile() {

    }

    public void save() {

        DatabaseReference userFirebaseRef = ConfigFirebase.getReferenciaFirebase();
        userFirebaseRef
                .child("users")
                .child(getIdUser())
                .setValue(this);

    }


    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrlProfile() {
        return urlProfile;
    }

    public void setUrlProfile(String urlProfile) {
        this.urlProfile = urlProfile;
    }
}
