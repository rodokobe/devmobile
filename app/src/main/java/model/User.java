package model;

import com.google.firebase.database.DatabaseReference;

import helper.ConfigFirebase;

public class User {

    private String idUser;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String userPassword;

    public User() {

    }

    public void saveUserData() {

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

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
